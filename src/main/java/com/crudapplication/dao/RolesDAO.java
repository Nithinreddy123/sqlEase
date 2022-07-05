package com.crudapplication.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.crudapplication.entity.Role;
import com.crudapplication.entity.UserRole;

@Component
public interface RolesDAO {

	public Role getRoleByRoleId(int id);

	public boolean putUserIntoRole(int userId, int roleId);
	
	public UserRole getRoleByUserId(int userId);

}
