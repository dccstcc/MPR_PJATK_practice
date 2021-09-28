package repositories.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import domain.User;

public class UserBuilder implements IEntityBuilder<User> {

	@Override
	public User build(ResultSet rs) throws SQLException {
		User result = new User();
		result.setId(rs.getInt("id"));
		result.setLogin(rs.getString("login"));
		result.setPassword(rs.getString("password"));
		return result;
	}

}
