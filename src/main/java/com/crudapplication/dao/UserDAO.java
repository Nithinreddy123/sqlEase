package com.crudapplication.dao;


import java.util.List;

import org.springframework.stereotype.Component;

import com.crudapplication.entity.User;
import com.crudapplication.entity.UserInfo;

@Component
public interface UserDAO {

	
	
	public User createUser(User user);
	public List<User> getUserByUserName(String userName);
	public List<UserInfo> getUserByUserId(int userId);
	
}
