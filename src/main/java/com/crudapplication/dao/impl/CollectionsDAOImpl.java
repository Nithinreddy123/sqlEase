package com.crudapplication.dao.impl;

import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.crudapplication.dao.CollectionsDAO;
import com.crudapplication.entity.Collection;
import com.crudapplication.rowmapper.CollectionDescRowMapper;
import com.crudapplication.rowmapper.CollectionItemRowMapper;
import com.crudapplication.rowmapper.CollectionRowMapper;
import com.crudapplication.utility.CollectionsSqlStatements;

@Component
public class CollectionsDAOImpl implements CollectionsDAO {

	public static Logger logger = LoggerFactory.getLogger(CollectionsDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Collection createCollection(Collection collection) {
		// TODO Auto-generated method stub
		String query = CollectionsSqlStatements.prepareCreateCollectionStatement(collection);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rows = jdbcTemplate.update(c -> c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), keyHolder);
		if (rows > 0) {

			collection.setId(keyHolder.getKey().intValue());
		}

		return collection;
	}

	@Override
	public List<String> getCollectionsByRoleId(int roleId) {
		// TODO Auto-generated method stub
		String query = CollectionsSqlStatements.prepareGetCollectionsByRoleId(roleId);
		List<Map<String, Object>> collections = jdbcTemplate.query(query, new CollectionRowMapper());
		List<String> collectionList = new ArrayList<String>();

		for (Map<String, Object> collection : collections) {
			collectionList.add((String) collection.get("subject"));
		}
		return collectionList;
	}

	public Map<String, Object> getCollectionAttributes(String collectionId) {
		List<Map<String, Object>> rawAttributes = jdbcTemplate.query(
				CollectionsSqlStatements.prepareGetCollectionAttributesStatement(collectionId),
				new CollectionDescRowMapper());
		Map<String, Object> preparedAttributes = new HashMap<String, Object>();
		for (Map<String, Object> attr : rawAttributes) {

			for (Map.Entry<String, Object> attrMap : attr.entrySet()) {
				preparedAttributes.put(attrMap.getKey(), attrMap.getValue());
			}

		}

		return preparedAttributes;
	}

	@Override
	public Map<String, Object> putItemIntoCollection(String id, Map<String, Object> item) {
		// TODO Auto-generated method stub
		logger.debug("Perparing sql query");
		String query = CollectionsSqlStatements.preparePutItemIntoCollection(id, item);
		logger.debug(query);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		int rows = jdbcTemplate.update(c -> c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS), keyHolder);
		if (rows > 0) {

			item.put("id", keyHolder.getKey().intValue());
		}

		return item;
	}

	public List<Map<String, Object>> getItemsFromCollection(String collectionId, Map<String, String> params) {

		List<Map<String, Object>> collectionItems = jdbcTemplate.query(
				CollectionsSqlStatements.prepareGetItemsFromCollection(collectionId, params),
				new CollectionItemRowMapper());
		return collectionItems;

	};

	@Override
	public boolean copyCollectionContents(String source, String destination, List<Map<String, Object>> attributes) {

		jdbcTemplate
				.update(CollectionsSqlStatements.prepareCopyTableContentsStatement(source, destination, attributes));

		return true;
	}

	@Override
	public boolean alterCollectionName(String source, String destination) {
		jdbcTemplate.update(CollectionsSqlStatements.prepareAlterTableNameStatement(source, destination));
		return true;
	}

	@Override
	public boolean dropCollection(String collectionId) {
		jdbcTemplate.update(CollectionsSqlStatements.prepareDropCollection(collectionId));
		return false;
	}
}
