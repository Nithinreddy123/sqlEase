package com.crudapplication.utility;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crudapplication.constants.PermissionsConstants;
import com.crudapplication.constants.UserConstants;
import com.crudapplication.entity.Collection;

public class CollectionsSqlStatements {
	public static final Logger logger = LoggerFactory.getLogger(CollectionsSqlStatements.class);

	public static String prepareCreateCollectionStatement(Collection collection) {

		List<Map<String, Object>> fields = collection.getFields();

		String query = "CREATE TABLE " + collection.getCollection_id() + " (id int NOT NULL AUTO_INCREMENT,";

		for (Map<String, Object> field : fields) {

			if (!(field.get("name").equals(""))) {
				query += field.get("name") + " ";
			} else {

			}

			if (!(field.get("type").equals(""))) {
				query += field.get("type") + " ";
			} else {

			}
			if (field.get("type").equals("int")) {
				if (!(field.get("default").equals(""))) {
					query += "DEFAULT" + " " + field.get("default") + " ";
				} else {

				}

			} else {
				if (!(field.get("default").equals(""))) {
					query += "DEFAULT" + " '" + field.get("default") + "' ";
				}

			}

			query += ",";

//			query += field.get("name") + " " + field.get("type") + " " + "DEFAULT"
//					+ (field.get("type") == "int" ? " " + field.get("default") + ","
//							: " '" + field.get("default") + "',");
		}

//		query = query.substring(0, query.length() - 1);
		query += "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,PRIMARY KEY (`id`)";
		query += ")";
		return query;

	}

	public static String prepareGetCollectionsByRoleId(int roleId) {

		String query = "SELECT DISTINCT subject FROM " + PermissionsConstants.TABLE_NAME + " ";

		if (!(roleId == UserConstants.ADMIN_ROLE_ID)) {
			query += "WHERE" + " " + "role=" + roleId;
		}

		return query;

	}

	public static String prepareGetCollectionAttributesStatement(String collectionId) {
		String query = "DESC " + collectionId;
		return query;
	}

	public static final String preparePutItemIntoCollection(String id, Map<String, Object> items) {
		String query = "INSERT INTO" + " " + id + " " + "("

		;
		String values = "(";
		for (Map.Entry<String, Object> item : items.entrySet()) {

			query += item.getKey() + ",";
			try {
				int value = Integer.parseInt((String) item.getValue());
				logger.debug("Value is of type Integer");
				values += value + ",";
			} catch (Exception e) {
				logger.debug("Value is not of type Integer");
				values += "'" + item.getValue() + "'" + ",";
			}

		}

		values = values.substring(0, values.length() - 1);
		values += ")";
		query = query.substring(0, query.length() - 1);
		query += ")";
		query += " ";
		query += "values";

		query += " ";
		query += values;

		return query;
	}

	public static final String prepareGetItemsFromCollection(String collectionId, Map<String, String> params) {

		String query = "select * from " + collectionId + " ";

		if (params.entrySet().size() > 0)
			query += "where" + " ";
		int currentItr = 0;

		for (Map.Entry<String, String> item : params.entrySet()) {
			currentItr += 1;

			try {
				int value = Integer.parseInt(item.getValue());
				query += item.getKey() + "=" + item.getValue() + " ";

			} catch (Exception e) {
				query += item.getKey() + "=" + "'" + item.getValue() + "'" + " ";
			}

			if (!(currentItr == params.entrySet().size()))
				query += "and" + " ";

		}
		return query;

	}

	public static final String prepareCopyTableContentsStatement(String source, String destination,
			List<Map<String, Object>> attributes) {

		String columns = "";

		columns += "(";

		for (Map<String, Object> attribute : attributes) {

			columns += (String) attribute.get("name");
			columns += ",";
		}

		columns += "id";

		columns += ")";
		
		String columnsWithoutParanthesis=columns.replace('(', ' ');
		columnsWithoutParanthesis=columnsWithoutParanthesis.replace(')', ' ');
		
		String query = "INSERT INTO" + " " + destination + " " + columns + " " + "SELECT" + " " + columnsWithoutParanthesis + " " + "FROM"
				+ " " + source;
		;
		return query;

	}

	public static final String prepareAlterTableNameStatement(String source, String destination) {
		String query = "ALTER TABLE" + " " + source + " " + "RENAME TO" + " " + destination;
		return query;
	}

	public static final String prepareDropCollection(String collectionId) {

		String query = "DROP TABLE" + " " + collectionId;
		return query;

	}

}
