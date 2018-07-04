package com.books.dao.concrete.SQL;

import com.books.dao.abstracts.AuthorDAO;
import com.books.entities.Person;
import com.books.utils.AuthorTableColomnName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class AuthorSQLDAO implements AuthorDAO {
    private static final String AUTHOR_TABLE_NAME = "bookapp.authors";
    private static final Logger logger = LoggerFactory.getLogger(AuthorSQLDAO.class);

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
        jdbcTemplate.update(addAuthorQuery, new Object[]{item.getFirstName(), item.getLastName()});
    }

    @Override
    public void remove(Person item) {
        remove(item.getId());
    }

    @Override
    public Person remove(int id) {
        Person result = getAuthorById(id);
        String query = String.format("delete from %s where %s = ?", AUTHOR_TABLE_NAME, AuthorTableColomnName.ID);
        jdbcTemplate.update(query, new Object[]{id});
        return result;
    }

    @Override
    public List<Person> getList() {
        String getAllAuthorsQuery = String.format("select * from %s ", AUTHOR_TABLE_NAME);
        return jdbcTemplate.query(getAllAuthorsQuery, (rs, rn) ->
        {
            String firstName = rs.getString(AuthorTableColomnName.FIRST_NAME.toString());
            String lastName = rs.getString(AuthorTableColomnName.LAST_NAME.toString());
            Integer id = rs.getInt(AuthorTableColomnName.ID.toString());
            return new Person(id, firstName, lastName);
        });
    }

    @Override
    public void saveItem(Integer id, Person item) {
        String setAuthorQuery = String.format("update %s set %s = ?,%s=?,%s=? where %s =?",
                AUTHOR_TABLE_NAME,
                AuthorTableColomnName.ID,
                AuthorTableColomnName.FIRST_NAME,
                AuthorTableColomnName.LAST_NAME,
                AuthorTableColomnName.ID);
        jdbcTemplate.update(setAuthorQuery, new Object[]{item.getId(), item.getFirstName(), item.getLastName(), id});
    }

    @Override
    public Person getAuthorById(int id) {
        String getAuthorByIdQuery = String.format("select * from %s where %s = ?", AUTHOR_TABLE_NAME, AuthorTableColomnName.ID);

        return jdbcTemplate.queryForObject(getAuthorByIdQuery, new Object[]{id}, (rs, rn) ->
        {
            String firstName = rs.getString(AuthorTableColomnName.FIRST_NAME.toString());
            String lastName = rs.getString(AuthorTableColomnName.LAST_NAME.toString());
            //Integer id = rs.getInt(AuthorTableColomnName.ID.toString());
            return new Person(id, firstName, lastName);
        });

    }
}
