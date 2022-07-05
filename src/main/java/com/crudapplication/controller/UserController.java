package com.crudapplication.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.crudapplication.constants.CollectionConstants;
import com.crudapplication.constants.UserConstants;
import com.crudapplication.entity.Collection;
import com.crudapplication.entity.User;

@RestController
public interface UserController {
	
	
	
	@RequestMapping(method=RequestMethod.POST,value=UserConstants.ROUTE_USER,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> createUser(@RequestBody User user,HttpServletRequest request);
	
	@RequestMapping(method=RequestMethod.GET,value=UserConstants.ROUTE_USER,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String,Object>> loadUserInfo(HttpServletRequest request);
	
	
	
	

}
