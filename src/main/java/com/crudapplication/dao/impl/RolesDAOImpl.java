package com.crudapplication.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.crudapplication.dao.RolesDAO;
import com.crudapplication.entity.Role;
import com.crudapplication.entity.UserRole;
import com.crudapplication.exception.UserException;
import com.crudapplication.rowmapper.RolesRowMapper;
import com.crudapplication.rowmapper.UserRoleRowMapper;
import com.crudapplication.utility.RolesSqlStatements;

@Component
public class RolesDAOImpl implements RolesDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public Role getRoleByRoleId(int id) {

		List<Role> role = jdbcTemplate.query(RolesSqlStatements.getRoleByRoleIdSqlStatement(id), new RolesRowMapper());
		if (role.size() == 0) {
			throw new UserException("Role id is invalid, please pass a valid role id", HttpStatus.BAD_REQUEST);
		}
		return role.get(0);

	}

	public boolean putUserIntoRole(int userId, int roleId) {

		jdbcTemplate.update(RolesSqlStatements.getPutUserIntoRoleStatement(userId, roleId));
		return true;
	}
	
	@Override
	public UserRole getRoleByUserId(int userId) {
		
		String query=RolesSqlStatements.prepareGetRoleFromUserIdSqlStatement(userId);
		List<UserRole> roles=jdbcTemplate.query(query, new UserRoleRowMapper());
		// TODO Auto-generated method stub
		return roles.get(0);
	}

}
