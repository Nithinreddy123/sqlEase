package com.crudapplication.dao.impl;

import java.io.Console;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.crudapplication.dao.UserDAO;
import com.crudapplication.entity.User;
import com.crudapplication.entity.UserInfo;
import com.crudapplication.exception.UserException;
import com.crudapplication.rowmapper.UserInfoRowMapper;
import com.crudapplication.rowmapper.UserRowMapper;
import com.crudapplication.utility.UserSqlStatements;

@Component

public class UserDAOImpl implements UserDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override

	public User createUser(User user) {
		// TODO Auto-generated method stub
		try {
			if (user.getUser_name() == null || user.getFirst_name() == null || user.getUser_pass() == null
					|| user.getLast_name() == null) {
				throw new UserException("Please fill all the mandatory fields", HttpStatus.BAD_REQUEST);
			}
			String insertQuery = UserSqlStatements.getUserCreateStatement(user);
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(c -> c.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS), keyHolder);
//			System.out.println(keyHolder.getKey().longValue());
			List<User> createdUser = getUserByUserName(user.getUser_name());

			return createdUser.get(0);
		} catch (Exception e) {
			throw new UserException(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@Override
	public List<User> getUserByUserName(String userName) {

		List<User> createdUser = jdbcTemplate.query(UserSqlStatements.getUserByUserNameStatement(userName),
				new UserRowMapper());
		return createdUser;
	}

	@Override
	public List<UserInfo> getUserByUserId(int userId) {
		// TODO Auto-generated method stub

		List<UserInfo> user = jdbcTemplate.query(UserSqlStatements.prepareGetUserByUserId(userId),
				new UserInfoRowMapper());

		return user;
	}
}
