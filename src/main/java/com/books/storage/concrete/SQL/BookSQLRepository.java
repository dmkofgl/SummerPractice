package com.books.storage.concrete.SQL;

import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.services.PublisherService;
import com.books.storage.abstracts.Repository;
import com.books.utils.BookAuthorTableColumnName;
import com.books.utils.BookTableColumnName;
import com.books.utils.Constants;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookSQLRepository implements Repository<Book> {
    private static final String BOOK_TABLE_NAME = "bookapp.books";
    private static final String BOOK_AUTHORS_TABLE_NAME = "bookapp.book_author";


    private static final Logger logger = LoggerFactory.getLogger(BookSQLRepository.class);
    public static final BookSQLRepository INSTANCE = new BookSQLRepository();
    private JdbcConnectionPool connectionPool;
    // TODO Change Repository<Person>
    private Repository<Person> authorRepository;
    // TODO Change PublisherSQLRepository
    private PublisherSQLRepository publisherRepository;

    public static BookSQLRepository getInstance() {
        return INSTANCE;
    }

    private BookSQLRepository() {
        connectionPool = JdbcConnectionPool.create(Constants.DATABASE_URL,
                Constants.DATABASE_USER_NAME, Constants.DATABASE_USER_PASSWORD);
        authorRepository = AuthorSQLRepository.getInstance();
        publisherRepository = PublisherSQLRepository.getInstance();
    }

    @Override
    public void add(Book item) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String queryBook = String.format("insert into %s (%s,%s,%s,%s ) values(%s,'%s', %s,'%s')",
                BOOK_TABLE_NAME,
                BookTableColumnName.ID.toString(),
                BookTableColumnName.NAME.toString(),
                BookTableColumnName.PUBLISHER_ID.toString(),
                BookTableColumnName.BOOKDATE.toString(),
                item.getId(),
                item.getName(),
                item.getPublisher().getId(),
                dateFormat.format(item.getPublishDate()));

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
            Statement statement = conn.createStatement();
            statement.executeUpdate(queryBook);
            statement.executeUpdate(queryBookAuthor);
        } catch (SQLException e) {
            logger.info("db add query drop down:" + e.getMessage());
        }

    }

    @Override
    public void remove(Book item) {
        remove(item.getId());
    }

    public Book remove(int id) {
        Book result = null;
        String querySelect = String.format("select * from %s where id = %s", BOOK_TABLE_NAME, id);
        String queryDeleteBook = String.format("delete from %s where id = %s; ", BOOK_TABLE_NAME, id);
        String deleteBookAuthorQuery = String.format("delete from %s where %s = %s;", BOOK_AUTHORS_TABLE_NAME, BookAuthorTableColumnName.BOOK_ID, id);
        String queryBookAuthors = String.format("select * from %s where %s = %s ", BOOK_AUTHORS_TABLE_NAME, BookAuthorTableColumnName.BOOK_ID, id);
        try (Connection conn = connectionPool.getConnection()) {
            Statement createStatement = conn.createStatement();
            Statement deleteStatement = conn.createStatement();
            Statement findAuthorsStatement = conn.createStatement();
            ResultSet book = createStatement.executeQuery(querySelect);
            ResultSet bookAuthors = findAuthorsStatement.executeQuery(queryBookAuthors);
            book.next();
            result = compileBookFromSet(book, bookAuthors);
            deleteStatement.executeUpdate(deleteBookAuthorQuery);
            deleteStatement.executeUpdate(queryDeleteBook);

        } catch (SQLException e) {
            logger.info("db remove query drop down:" + e.getMessage());
        }
        return result;
    }

    private Book compileBookFromSet(ResultSet bookSet, ResultSet bookAuthorsSet) throws SQLException {
        Integer id = bookSet.getInt(BookTableColumnName.ID.toString());
        String name = bookSet.getString(BookTableColumnName.NAME.toString());
        Date date = bookSet.getDate(BookTableColumnName.BOOKDATE.toString());
        List<Person> authors = new ArrayList<>();
        Publisher publisher = publisherRepository.getPublisherById(
                bookSet.getInt(BookTableColumnName.PUBLISHER_ID.toString()));
        while (bookAuthorsSet.next()) {
            if (bookAuthorsSet.getInt(BookAuthorTableColumnName.BOOK_ID.toString()) == id) {
                Integer authorId = bookAuthorsSet.getInt(BookAuthorTableColumnName.AUTHOR_ID.toString());
                //TODO cast
                authors.add(((AuthorSQLRepository) authorRepository).getAuthorById(authorId));
            }
        }
        Book book = new Book(id, name, date, publisher, authors);
        return book;
    }

    @Override
    public List<Book> getCollection() {
        String queryBook = String.format("select * from %s ", BOOK_TABLE_NAME);
        String queryBookAuthors = String.format("select * from %s ", BOOK_AUTHORS_TABLE_NAME);

        ResultSet resultSetBooks;
        ResultSet resultSetBookAuthors;

        List<Book> result = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            Statement statement = conn.createStatement();
            Statement statement2 = conn.createStatement();
            resultSetBooks = statement.executeQuery(queryBook);
            resultSetBookAuthors = statement2.executeQuery(queryBookAuthors);

            while (resultSetBooks.next()) {
                Book book = compileBookFromSet(resultSetBooks, resultSetBookAuthors);
                result.add(book);
            }
        } catch (SQLException e) {
            logger.info("db get_all query drop down:" + e.getMessage());
        } finally {
        }
        return result;
    }

    @Override
    public void setItem(int id, Book item) {
        remove(id);
        add(item);
    }
}
