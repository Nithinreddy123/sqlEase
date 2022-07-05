package com.crudapplication.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.crudapplication.constants.UserConstants;
import com.crudapplication.controller.UserController;
import com.crudapplication.dao.RolesDAO;
import com.crudapplication.dao.UserDAO;
import com.crudapplication.entity.Collection;
import com.crudapplication.entity.Role;
import com.crudapplication.entity.User;
import com.crudapplication.entity.UserInfo;
import com.crudapplication.exception.UserException;
import com.crudapplication.utility.EncryptionDecryptionAES;
import com.crudapplication.utility.ResponseGenerator;

@Component
@Transactional
public class UserControllerImpl implements UserController {

	public static Logger logger = LoggerFactory.getLogger(UserControllerImpl.class);
	@Autowired
	UserDAO userDao;
	@Autowired
	RolesDAO rolesDao;

	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@Override
	@Transactional(rollbackFor = { Exception.class, UserException.class })
	public ResponseEntity<Map<String, Object>> createUser(User user, HttpServletRequest request) {

		int role_id = (int) request.getAttribute("role_id");

		if (role_id != UserConstants.ADMIN_ROLE_ID) {

			throw new UserException("INSUFFICIENT PRIVILIGES", HttpStatus.FORBIDDEN);

		}

		logger.debug("Creating user");

		user.setUser_pass(bCryptPasswordEncoder.encode(user.getUser_pass()));
		User createdUser = userDao.createUser(user);
		Role role = rolesDao.getRoleByRoleId(user.getUser_role());
		int roleId = role.getId();
		int userId = createdUser.getId();
		createdUser.setUser_role(user.getUser_role());
		boolean status = rolesDao.putUserIntoRole(userId, roleId);
		if (!status) {
			throw new UserException("Failed to put user into role", HttpStatus.BAD_REQUEST);
		}

		List<User> userList = new ArrayList<User>();
		userList.add(createdUser);

		return ResponseGenerator.getGenericResponse(userList);
	}

	@Override
	public ResponseEntity<Map<String, Object>> loadUserInfo(HttpServletRequest request) {
		// TODO Auto-generated method stub

		int userId = (int) request.getAttribute("user_id");
		List<UserInfo> user = userDao.getUserByUserId(userId);
		return ResponseGenerator.getGenericResponse(user);
	}

}
