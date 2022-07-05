package com.crudapplication.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.crudapplication.dao.PermissionsDAO;
import com.crudapplication.entity.Collection;
import com.crudapplication.rowmapper.PermissionsRowMapper;
import com.crudapplication.utility.PermissionsStatement;

@Component
public class PermissionsDAOImpl implements PermissionsDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public boolean createPermission(Collection collection) {
		// TODO Auto-generated method stub
		List<String> permissions = collection.getPermissions();

		for (String permission : permissions) {
			String statement = PermissionsStatement.prepareCreatePermissionStatement(collection, permission);
			jdbcTemplate.update(statement);
		}

		return true;
	}

	@Override
	public List<String> getPermissionByCollectionId(String collectionId) {
		// TODO Auto-generated method stub
		String query = PermissionsStatement.prepareGetPermissionStatement(collectionId);
		List<Map<String, Object>> rawPermissions = jdbcTemplate.query(query, new PermissionsRowMapper());
		List<String> permissions = new ArrayList<String>();
		for (Map<String, Object> permission : rawPermissions) {
			permissions.add((String) permission.get("action"));
		}
		return permissions;
	}

	@Override
	public boolean updateCollectionId(String oldCollectionId, String newCollectionId) {

		jdbcTemplate.update(PermissionsStatement.prepareUpdateCollectionIdStatement(oldCollectionId, newCollectionId));
		
		return true;
	}
}
