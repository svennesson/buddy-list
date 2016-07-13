package se.svennesson.svennelist.dao.mapping;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.svennesson.svennelist.model.Role;
import se.svennesson.svennelist.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements ResultSetMapper<User> {

    @Override
    public User map(int i, ResultSet r, StatementContext statementContext) throws SQLException {
        return new User(r.getLong("id"), r.getString("name"), r.getString("email"), r.getString("password"), r.getInt("age"), Role.valueOf(r.getString("role")));
    }
}
