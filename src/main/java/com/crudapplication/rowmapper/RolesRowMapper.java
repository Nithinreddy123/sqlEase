package com.crudapplication.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.crudapplication.entity.Role;

public class RolesRowMapper implements RowMapper<Role> {
	
	
	@Override
	public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
		Role role=new Role();
		role.setId(rs.getInt("id"));
		role.setRole_description(rs.getString("role_description"));
		role.setRole_id(rowNum);
		return role;
	}

}
