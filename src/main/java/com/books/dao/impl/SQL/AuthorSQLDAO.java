package com.books.dao.impl.SQL;

import com.books.dao.abstracts.AuthorDAO;
import com.books.entities.Person;
import com.books.utils.AuthorTableColomnName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AuthorSQLDAO implements AuthorDAO {
    private static final String AUTHOR_TABLE_NAME = "bookapp.authors";

    private JdbcTemplate jdbcTemplate;

    private AuthorSQLDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(Person item) {
        String addAuthorQuery = String.format("insert into %s (%s,%s ) values( ?, ?)",
                AUTHOR_TABLE_NAME,
                AuthorTableColomnName.FIRST_NAME.toString(),
                AuthorTableColomnName.LAST_NAME.toString());
        jdbcTemplate.update(addAuthorQuery, item.getFirstName(), item.getLastName());
    }

    @Override
    public void remove(Person item) {
        remove(item.getId());
    }

    @Override
    public Person remove(int id) {
        Person result = getAuthorById(id);
        String query = String.format("delete from %s where %s = ?", AUTHOR_TABLE_NAME, AuthorTableColomnName.ID);
        jdbcTemplate.update(query, id);
        return result;
    }

    @Override
    public List<Person> getList() {
        String getAllAuthorsQuery = String.format("select * from %s ", AUTHOR_TABLE_NAME);
        return jdbcTemplate.query(getAllAuthorsQuery, new AuthorMapper());
    }

    @Override
    public void saveItem(Integer id, Person item) {
        String setAuthorQuery = String.format("update %s set %s = ?,%s=?,%s=? where %s =?",
                AUTHOR_TABLE_NAME,
                AuthorTableColomnName.ID,
                AuthorTableColomnName.FIRST_NAME,
                AuthorTableColomnName.LAST_NAME,
                AuthorTableColomnName.ID);
        jdbcTemplate.update(setAuthorQuery, item.getId(), item.getFirstName(), item.getLastName(), id);
    }

    @Override
    public Person getAuthorById(int id) {
        String getAuthorByIdQuery = String.format("select * from %s where %s = ?", AUTHOR_TABLE_NAME, AuthorTableColomnName.ID);

        return jdbcTemplate.queryForObject(getAuthorByIdQuery, new Object[]{id}, new AuthorMapper());
    }

    private class AuthorMapper implements RowMapper<Person> {

        @Override
        public Person mapRow(ResultSet resultSet, int i) throws SQLException {
            String firstName = resultSet.getString(AuthorTableColomnName.FIRST_NAME.toString());
            String lastName = resultSet.getString(AuthorTableColomnName.LAST_NAME.toString());
            Integer id = resultSet.getInt(AuthorTableColomnName.ID.toString());
            return new Person(id, firstName, lastName);
        }
    }
}
