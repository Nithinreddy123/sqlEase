package com.crudapplication.utility;

import com.crudapplication.constants.RolesConstants;

public class RolesSqlStatements {

	public static String getRoleByRoleIdSqlStatement(int id) {

		String query = "select * from " + RolesConstants.TABLE_NAME + " where role_id=" + id;
		return query;
	}

	public static String getPutUserIntoRoleStatement(int userId, int roleId) {

		String query = "insert into " + RolesConstants.USER_ROLE_TABLE_NAME + " (user_id,role_id) values(" + userId
				+ "," + roleId + ")";
		return query;
	}

	public static String prepareGetRoleFromUserIdSqlStatement(int user_id) {

		String query = "select * from " + RolesConstants.USER_ROLE_TABLE_NAME + " where user_id=" + user_id;
		return query;
	}
}
