package com.crudapplication.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.crudapplication.entity.UserRole;

public class UserRoleRowMapper implements RowMapper<UserRole> {

	@Override
	public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		UserRole userRole = new UserRole();
		userRole.setId(rs.getInt("id"));
		userRole.setRole_id(rs.getInt("role_id"));
		userRole.setUser_id(rs.getInt("user_id"));
		return userRole;
	}

}
