package com.crudapplication.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

public class PermissionsRowMapper implements RowMapper<Map<String,Object>> {
	
	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("action", rs.getString("action"));
		return result;
	}

}
