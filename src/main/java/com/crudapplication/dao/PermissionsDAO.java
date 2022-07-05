package com.crudapplication.dao;

import java.util.List;
import java.util.Map;

import com.crudapplication.entity.Collection;

public interface PermissionsDAO {

	public boolean createPermission(Collection collection);
	public List<String> getPermissionByCollectionId(String collectionId);
}
