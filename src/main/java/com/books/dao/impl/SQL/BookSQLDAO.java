package com.books.dao.impl.SQL;

import com.books.dao.abstracts.AuthorDAO;
import com.books.dao.abstracts.BookDAO;
import com.books.dao.abstracts.PublisherDAO;
import com.books.entities.Book;
import com.books.entities.Person;
import com.books.entities.Publisher;
import com.books.exceptions.UncorrectedQueryException;
import com.books.utils.BookAuthorTableColumnName;
import com.books.utils.BookTableColumnName;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BookSQLDAO implements BookDAO {
    private static final String BOOK_TABLE_NAME = "bookapp.books";
    private static final String BOOK_AUTHORS_TABLE_NAME = "bookapp.book_author";

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
        String queryBookAuthor = String.format("insert into %s (%s, %s ) values(?, ?);",
                BOOK_AUTHORS_TABLE_NAME,
                BookAuthorTableColumnName.AUTHOR_ID.toString(),
                BookAuthorTableColumnName.BOOK_ID.toString());

        Collection<Person> authors = item.getAuthors();
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(queryBook, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, item.getName());
            Optional<Publisher> publisher = Optional.ofNullable(item.getPublisher());
            if (publisher.isPresent()) {
                statement.setInt(2, item.getPublisher().getId());
            } else {
                statement.setNull(2, Types.INTEGER);
            }
            statement.setDate(3, new Date(item.getPublishDate().getTime()));
            return statement;
        }, holder);
        Long id = holder.getKey().longValue();
        List batch = authors.stream().map(author -> new Object[]{author.getId(), id}).collect(Collectors.toList());
        jdbcTemplate.batchUpdate(queryBookAuthor, batch);
    }

    private void addWithId(Book item) {
        String queryBook = String.format("insert into %s (%s,%s,%s,%s ) values(?,?, ?,?)",
                BOOK_TABLE_NAME,
                BookTableColumnName.ID,
                BookTableColumnName.NAME.toString(),
                BookTableColumnName.PUBLISHER_ID.toString(),
                BookTableColumnName.BOOKDATE.toString());
        String queryBookAuthor = String.format("insert into %s (%s, %s ) values(?, ?);",
                BOOK_AUTHORS_TABLE_NAME,
                BookAuthorTableColumnName.AUTHOR_ID.toString(),
                BookAuthorTableColumnName.BOOK_ID.toString());

        Collection<Person> authors = item.getAuthors();

        jdbcTemplate.update(queryBook, item.getId(),
                item.getName(),
                item.getPublisher().getId(),
                item.getPublishDate());
        List batch = authors.stream().map(author -> new Object[]{author.getId(), item.getId()}).collect(Collectors.toList());
        jdbcTemplate.batchUpdate(queryBookAuthor, batch);

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
        jdbcTemplate.update(deleteBookAuthorQuery, id);
        jdbcTemplate.update(queryDeleteBook, id);
        return result;

    }

    @Override
    public List<Book> getList() {
        String queryBook = String.format("select * from %s ", BOOK_TABLE_NAME);
        String queryBookAuthors = String.format("select * from %s where %s = ?",
                BOOK_AUTHORS_TABLE_NAME,
                BookAuthorTableColumnName.BOOK_ID);
        List<Book> result = jdbcTemplate.query(queryBook, new BookMapper());
        result.forEach(book -> book.setAuthors(
                jdbcTemplate.query(queryBookAuthors, new Object[]{book.getId()}, (rs, rn) -> {
                    Integer authorId = rs.getInt(BookAuthorTableColumnName.AUTHOR_ID.toString());
                    return authorRepository.getAuthorById(authorId);
                })));
        return result;
    }

    @Override
    public Book getBookById(int id) {
        String queryBook = String.format("select * from %s where %s = ?",
                BOOK_TABLE_NAME, BookTableColumnName.ID.toString());
        String queryBookAuthors = String.format("select * from %s where %s = ?",
                BOOK_AUTHORS_TABLE_NAME,
                BookAuthorTableColumnName.BOOK_ID);
        Book result = null;

        try {
            result = jdbcTemplate.queryForObject(queryBook, new Object[]{id}, new BookMapper());
            result.setAuthors(
                    jdbcTemplate.query(queryBookAuthors, new Object[]{result.getId()}, (rs, rn) -> {
                        Integer authorId = rs.getInt(BookAuthorTableColumnName.AUTHOR_ID.toString());
                        return authorRepository.getAuthorById(authorId);
                    }));
        } catch (EmptyResultDataAccessException emptyResultExcept) {
            throw new UncorrectedQueryException("Value does't found:BOOK:id =  " + id);
        }
        return result;
    }

    @Override
    public void saveItem(Integer id, Book item)  {
        Book book = null;
        try {
            book = getBookById(id);
            remove(id);
        } catch (UncorrectedQueryException e) {
            if (book != null) {
                addWithId(book);
                throw e;
            }
            return;
        }
        addWithId(item);

    }

    private class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            Integer id = resultSet.getInt(BookTableColumnName.ID.toString());
            String name = resultSet.getString(BookTableColumnName.NAME.toString());
            java.util.Date date = resultSet.getDate(BookTableColumnName.BOOKDATE.toString());
            Integer publisherId = resultSet.getObject(BookTableColumnName.PUBLISHER_ID.toString(), Integer.class);
            Publisher publisher = null;
            try {
                publisher = publisherRepository.getPublisherById(publisherId).orElse(null);
            } catch (UncorrectedQueryException e) {
            }
            Book book = new Book(id, name, date, publisher);
            return book;
        }
    }


}
