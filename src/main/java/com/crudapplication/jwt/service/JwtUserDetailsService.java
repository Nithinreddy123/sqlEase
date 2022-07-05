package com.crudapplication.jwt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.crudapplication.dao.UserDAO;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserDAO userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		List<com.crudapplication.entity.User> user = userDao.getUserByUserName(username);

		if ((user.get(0).getUser_name()).equals(username)) {
			return new User((user.get(0).getUser_name()), (user.get(0).getUser_pass()), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}