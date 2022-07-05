package com.crudapplication.utility;

import java.util.List;

import com.crudapplication.constants.PermissionsConstants;
import com.crudapplication.entity.Collection;

public class PermissionsStatement {

	public static String prepareCreatePermissionStatement(Collection collection, String permission) {

		String query = "insert into " + PermissionsConstants.TABLE_NAME
				+ " (action,subject,properties,conditions,role) values ('" + permission + "',"

				+ "'" + collection.getCollection_id() + "'," + "'[]'," + "'[]'," + "'" + collection.getRole() + "')";

		return query;
	}

	public static String prepareGetPermissionStatement(String collectionId) {

		String query = "select * from" + " " + PermissionsConstants.TABLE_NAME + " " + "where subject='" + collectionId
				+ "'";
		return query;
	}

	public static String prepareUpdateCollectionIdStatement(String oldId, String newId) {

		String query = "update" + " " + PermissionsConstants.TABLE_NAME + " " + "set subject='" + newId + "'" + " "
				+ "where subject='" + oldId + "'";
		return query;
	}
}
