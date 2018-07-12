package com.books.dao.concrete.SQL;

import com.books.dao.abstracts.AuthorDAO;
import com.books.dao.abstracts.BookDAO;
import com.books.dao.abstracts.PublisherDAO;
import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.utils.BookAuthorTableColumnName;
import com.books.utils.BookTableColumnName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class BookSQLDAO implements BookDAO {
    private static final String BOOK_TABLE_NAME = "bookapp.books";
    private static final String BOOK_AUTHORS_TABLE_NAME = "bookapp.book_author";

    private static final Logger logger = LoggerFactory.getLogger(BookSQLDAO.class);


    private JdbcTemplate jdbcTemplate;
    private AuthorDAO authorRepository;
    private PublisherDAO publisherRepository;


    private BookSQLDAO(JdbcTemplate jdbcTemplate, AuthorDAO authorDAO, PublisherDAO publisherDAO) {
        this.jdbcTemplate = jdbcTemplate;
        authorRepository = authorDAO;
        publisherRepository = publisherDAO;
    }

    @Override
    public void add(Book item) {
        String queryBook = String.format("insert into %s (%s, %s, %s ) values(?, ?, ?)",
                BOOK_TABLE_NAME,
                BookTableColumnName.NAME.toString(),
                BookTableColumnName.PUBLISHER_ID.toString(),
                BookTableColumnName.BOOKDATE.toString());

        Collection<Person> authors = item.getAuthors();
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(queryBook, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, item.getName());
                statement.setInt(2, item.getPublisher().getId());
                statement.setDate(3, new java.sql.Date(item.getPublishDate().getTime()));
                return statement;
            }
        }, holder);
        Long id = holder.getKey().longValue();
        String queryBookAuthor = "";
        for (Person author : authors) {
            queryBookAuthor += String.format("insert into %s (%s, %s ) values(%s, %s);",
                    BOOK_AUTHORS_TABLE_NAME,
                    BookAuthorTableColumnName.AUTHOR_ID.toString(),
                    BookAuthorTableColumnName.BOOK_ID.toString(),
                    author.getId(), id);
        }
        jdbcTemplate.update(queryBookAuthor);
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
        jdbcTemplate.update(queryBook, new Object[]{
                item.getId(),
                item.getName(),
                item.getPublisher().getId(),
                item.getPublishDate()});
        jdbcTemplate.update(queryBookAuthor);
    }

    @Override
    public void remove(Book item) {
        if (item.getId() != null)
            remove(item.getId());
    }

    @Override
    public Book remove(int id) {
        Book result = getBookById(id);
        String queryDeleteBook = String.format("delete from %s where %s = ?; ", BOOK_TABLE_NAME, BookTableColumnName.ID);
        String deleteBookAuthorQuery = String.format("delete from %s where %s = ?", BOOK_AUTHORS_TABLE_NAME, BookAuthorTableColumnName.BOOK_ID);
        jdbcTemplate.update(deleteBookAuthorQuery, new Object[]{id});
        jdbcTemplate.update(queryDeleteBook, new Object[]{id});
        return result;

    }


    @Override
    public List<Book> getList() {
        String queryBook = String.format("select * from %s ", BOOK_TABLE_NAME);
        return jdbcTemplate.query(queryBook, new BookMapper());
    }

    @Override
    public Book getBookById(int id) {
        String queryBook = String.format("select * from %s where %s = ?",
                BOOK_TABLE_NAME, BookTableColumnName.ID.toString());
        return jdbcTemplate.queryForObject(queryBook, new Object[]{id}, new BookMapper());
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

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer id = resultSet.getInt(BookTableColumnName.ID.toString());
            String name = resultSet.getString(BookTableColumnName.NAME.toString());
            Date date = resultSet.getDate(BookTableColumnName.BOOKDATE.toString());

            String queryBookAuthors = String.format("select * from %s where %s = ?",
                    BOOK_AUTHORS_TABLE_NAME,
                    BookAuthorTableColumnName.BOOK_ID.toString());
            Integer publisherId = resultSet.getObject(BookTableColumnName.PUBLISHER_ID.toString(), Integer.class);
            List<Person> authors = jdbcTemplate.query(queryBookAuthors, new Object[]{id}, (rs, rn) -> {
                Integer authorId = rs.getInt(BookAuthorTableColumnName.AUTHOR_ID.toString());
                return authorRepository.getAuthorById(authorId);
            });
            Publisher publisher = null;
            if (publisherId != null) {
                publisher = publisherRepository.getPublisherById(publisherId.intValue());
            }
            Book book = new Book(id, name, date, publisher, authors);
            return book;
        }
    }
}
