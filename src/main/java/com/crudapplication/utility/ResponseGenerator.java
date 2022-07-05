package com.crudapplication.utility;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseGenerator {

	public static ResponseEntity<Map<String,Object>> getGenericResponse(Object response){
		
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("status", true);
		resultMap.put("data", response);
		return  new ResponseEntity<Map<String,Object>>(resultMap,HttpStatus.CREATED);
	}
}
