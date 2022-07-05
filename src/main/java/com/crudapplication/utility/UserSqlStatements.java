package com.crudapplication.utility;

import com.crudapplication.constants.UserConstants;
import com.crudapplication.entity.User;

public class UserSqlStatements {

	public static String getUserCreateStatement(User user) {

		String query = "insert into " + UserConstants.TABLE_NAME + " (user_name,first_name,last_name,user_pass)"
				+ " values('" + user.getUser_name() + "','" + user.getFirst_name() + "','" + user.getLast_name() + "','"
				+ user.getUser_pass() + "')";
		return query;
	}

	public static String getUserByUserNameStatement(String userName) {
		String query = "select * from " + UserConstants.TABLE_NAME + " where user_name='" + userName + "'";
		return query;
	}

	public static String prepareGetUserByUserId(int userId) {
		String query = "SELECT a.id,a.user_name,a.first_name,a.last_name,b.role_id FROM users a INNER JOIN user_role b on a.id=b.user_id where a.id="
				+ userId;
		return query;
	}
}
