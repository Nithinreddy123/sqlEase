package com.crudapplication.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class CollectionRowMapper implements RowMapper<Map<String,Object>>{
	
	
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> collection=new HashMap<String, Object>();
//		collection.put("id", rs.getInt("id"));
//		collection.put("action", rs.getString("action"));
		collection.put("subject", rs.getString("subject"));
//		collection.put("role", rs.getInt("role"));
		return collection;
	}

}
