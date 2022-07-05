package com.crudapplication.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.crudapplication.entity.UserInfo;

public class UserInfoRowMapper implements RowMapper<UserInfo> {

	@Override
	public UserInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		UserInfo userInfo = new UserInfo();
		userInfo.setFirst_name(rs.getString("first_name"));
		userInfo.setLast_name(rs.getString("last_name"));
		userInfo.setUser_name(rs.getString("user_name"));
		userInfo.setUser_role(rs.getInt("role_id"));
		userInfo.setId(rs.getInt("id"));
		return userInfo;
	}
}
