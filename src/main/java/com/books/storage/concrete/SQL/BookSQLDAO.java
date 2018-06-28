package com.books.storage.concrete.SQL;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.storage.abstracts.AuthorDAO;
import com.books.storage.abstracts.BookDAO;
import com.books.storage.abstracts.PublisherDAO;
import com.books.utils.BookAuthorTableColumnName;
import com.books.utils.BookTableColumnName;
import com.books.utils.Constants;
import com.books.utils.DatabaseConnector;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookSQLDAO implements BookDAO {
    private static final String BOOK_TABLE_NAME = "bookapp.books";
    private static final String BOOK_AUTHORS_TABLE_NAME = "bookapp.book_author";

    private static final Logger logger = LoggerFactory.getLogger(BookSQLDAO.class);
    public static final BookSQLDAO INSTANCE = new BookSQLDAO();

    private JdbcConnectionPool connectionPool;
    private AuthorDAO authorRepository;
    private PublisherDAO publisherRepository;

    public static BookSQLDAO getInstance() {
        return INSTANCE;
    }

    private BookSQLDAO() {
        connectionPool = DatabaseConnector.getInstance().getConnectionPool();
        authorRepository = AuthorSQLDAO.getInstance();
        publisherRepository = PublisherSQLDAO.getInstance();
    }

    @Override
    public void add(Book item) {
        String queryBook = String.format("insert into %s (%s,%s,%s ) values(?, ?,?)",
                BOOK_TABLE_NAME,
                BookTableColumnName.NAME.toString(),
                BookTableColumnName.PUBLISHER_ID.toString(),
                BookTableColumnName.BOOKDATE.toString());

        Collection<Person> authors = item.getAuthors();
        String queryBookAuthor = "";
        for (Person author : authors) {
            queryBookAuthor += String.format("insert into %s (%s,%s ) values(%s, %s);",
                    BOOK_AUTHORS_TABLE_NAME,
                    BookAuthorTableColumnName.AUTHOR_ID.toString(),
                    BookAuthorTableColumnName.BOOK_ID.toString(),
                    author.getId(), item.getId());
        }
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement addBookStatement = conn.prepareStatement(queryBook);
            addBookStatement.setString(1, item.getName());
            try {
                Integer publisherId = item.getPublisher().getId();
                addBookStatement.setInt(2, publisherId);
            } catch (NullPointerException e) {
                addBookStatement.setNull(2, Types.INTEGER);
            }
            addBookStatement.setDate(3, new java.sql.Date(item.getPublishDate().getTime()));
            addBookStatement.execute();

            Statement addBookAuthorsStatement = conn.createStatement();
            addBookAuthorsStatement.execute(queryBookAuthor);
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    private void addWithId(Book item) {
        String queryBook = String.format("insert into %s (%s,%s,%s,%s ) values(?,?, ?,?)",
                BOOK_TABLE_NAME,
                BookTableColumnName.ID,
                BookTableColumnName.NAME.toString(),
                BookTableColumnName.PUBLISHER_ID.toString(),
                BookTableColumnName.BOOKDATE.toString());

        Collection<Person> authors = item.getAuthors();
        String queryBookAuthor = "";
        for (Person author : authors) {
            queryBookAuthor += String.format("insert into %s (%s,%s ) values(%s, %s);",
                    BOOK_AUTHORS_TABLE_NAME,
                    BookAuthorTableColumnName.AUTHOR_ID.toString(),
                    BookAuthorTableColumnName.BOOK_ID.toString(),
                    author.getId(), item.getId());
        }
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement addBookStatement = conn.prepareStatement(queryBook);
            addBookStatement.setInt(1, item.getId());
            addBookStatement.setString(2, item.getName());
            try {
                Integer publisherId = item.getPublisher().getId();
                addBookStatement.setInt(3, publisherId);
            } catch (NullPointerException e) {
                addBookStatement.setNull(3, Types.INTEGER);
            }
            addBookStatement.setDate(4, new java.sql.Date(item.getPublishDate().getTime()));
            addBookStatement.execute();

            Statement addBookAuthorsStatement = conn.createStatement();
            addBookAuthorsStatement.execute(queryBookAuthor);
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }
    }

    @Override
    public void remove(Book item) {
        if (item.getId() != null)
            remove(item.getId());
    }

    @Override
    public Book remove(int id) {
        Book result = null;
        String queryDeleteBook = String.format("delete from %s where %s = ?; ", BOOK_TABLE_NAME, BookTableColumnName.ID);
        String deleteBookAuthorQuery = String.format("delete from %s where %s = ?", BOOK_AUTHORS_TABLE_NAME, BookAuthorTableColumnName.BOOK_ID);
        try (Connection conn = connectionPool.getConnection()) {
            result = getBookById(id);

            PreparedStatement deleteAuthorsStatement = conn.prepareStatement(deleteBookAuthorQuery);
            deleteAuthorsStatement.setInt(1, id);
            deleteAuthorsStatement.execute();

            PreparedStatement deleteBookStatement = conn.prepareStatement(queryDeleteBook);
            deleteBookStatement.setInt(1, id);
            deleteBookStatement.execute();
        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
        return result;
    }

    private Book compileBookFromSet(ResultSet bookSet) throws SQLException {
        Integer id = bookSet.getInt(BookTableColumnName.ID.toString());
        String name = bookSet.getString(BookTableColumnName.NAME.toString());
        Date date = bookSet.getDate(BookTableColumnName.BOOKDATE.toString());
        List<Person> authors = new ArrayList<>();
        String queryBookAuthors = String.format("select * from %s where %s = ?",
                BOOK_AUTHORS_TABLE_NAME,
                BookAuthorTableColumnName.BOOK_ID.toString());
//In DB value is null, but there is 0
        Integer publusherId = bookSet.getObject(BookTableColumnName.PUBLISHER_ID.toString(), Integer.class);
        Publisher publisher = null;
        if (publusherId != null) {
            publisher = publisherRepository.getPublisherById(publusherId.intValue());
        }
        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(queryBookAuthors);
            statement.setInt(1, bookSet.getInt(BookTableColumnName.ID.toString()));
            ResultSet resultSetBookAuthor = statement.executeQuery();
            while (resultSetBookAuthor.next()) {
                Integer authorId = resultSetBookAuthor.getInt(BookAuthorTableColumnName.AUTHOR_ID.toString());

                authors.add(authorRepository.getAuthorById(authorId));
            }
        }
        Book book = new Book(id, name, date, publisher, authors);
        return book;
    }

    @Override
    public List<Book> getList() {
        String queryBook = String.format("select * from %s ", BOOK_TABLE_NAME);
        ResultSet resultSetBooks;
        List<Book> result = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            resultSetBooks = statement.executeQuery(queryBook);
            while (resultSetBooks.next()) {
                Book book = compileBookFromSet(resultSetBooks);
                result.add(book);
            }
        } catch (SQLException e) {
            logger.info("db get_all query drop down:" + e.getMessage());
        } finally {
        }
        return result;
    }

    @Override
    public Book getBookById(int id) {
        String queryBook = String.format("select * from %s where %s = ?",
                BOOK_TABLE_NAME, BookTableColumnName.ID.toString());
        ResultSet resultSetBooks;
        Book result = null;

        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement statement = conn.prepareStatement(queryBook);
            statement.setInt(1, id);
            resultSetBooks = statement.executeQuery();
            //take only one
            resultSetBooks.next();
            result = compileBookFromSet(resultSetBooks);
        } catch (SQLException e) {
            logger.info("db get_all query drop down:" + e.getMessage());
        }
        return result;
    }

    @Override
    public void saveItem(Integer id, Book item) {
        if (item.getId() == null) {
            add(item);
        } else {
            remove(item);
            addWithId(item);
        }
    }
}
