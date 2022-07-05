package com.crudapplication.controller.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.crudapplication.constants.CollectionConstants;
import com.crudapplication.constants.UserConstants;
import com.crudapplication.controller.CollectionsController;
import com.crudapplication.dao.CollectionsDAO;
import com.crudapplication.dao.PermissionsDAO;
import com.crudapplication.entity.Collection;
import com.crudapplication.entity.CollectionResponse;
import com.crudapplication.entity.User;
import com.crudapplication.exception.UserException;
import com.crudapplication.utility.CollectionsSqlStatements;
import com.crudapplication.utility.ResponseGenerator;
import com.crudapplication.utility.UUIDGenerator;

@Component
@Transactional

public class CollectionsControllerImpl implements CollectionsController {

	public static final Logger logger = LoggerFactory.getLogger(CollectionsController.class);
	@Autowired
	CollectionsDAO collectionsDao;
	@Autowired
	PermissionsDAO permissionsDao;

	@Override
	public ResponseEntity<Map<String, Object>> createCollection(Collection collection, HttpServletRequest request) {

		int role_id = (int) request.getAttribute("role_id");

		if (role_id != UserConstants.ADMIN_ROLE_ID) {

			throw new UserException("INSUFFICIENT PRIVILIGES", HttpStatus.FORBIDDEN);

		}
		logger.debug("Creating collection: " + collection.getCollection_id() + " with Admin Access");
		Collection createdCollection = collectionsDao.createCollection(collection);
		boolean status = permissionsDao.createPermission(collection);
		List<Collection> collectionList = new ArrayList<Collection>();
		collectionList.add(createdCollection);
		return (ResponseGenerator.getGenericResponse(collectionList));

	}

	@Override
	public ResponseEntity<Map<String, Object>> getCollections(HttpServletRequest request) {

		int roleId = (int) request.getAttribute("role_id");
		logger.debug("Retrieving collections with Role: " + roleId);
		List<String> collections = collectionsDao.getCollectionsByRoleId(roleId);
		logger.debug("Found collections: " + collections + " " + "for Role: " + roleId);

		List<CollectionResponse> collectionList = new ArrayList<CollectionResponse>();
		for (String collection : collections) {

			CollectionResponse collectionWithPermission = new CollectionResponse();

			String collectionId = collection;

			List<String> permissions = permissionsDao.getPermissionByCollectionId(collectionId);
			Map<String, Object> attributes = getCollectionAttributes(collectionId);
			collectionWithPermission.setPermissions(permissions);
			collectionWithPermission.setAttributes(attributes);
			collectionWithPermission.setApiId(collectionId);
			collectionList.add(collectionWithPermission);

		}

		logger.debug("Collection List: " + collectionList);

		// TODO Auto-generated method stub
		return (ResponseGenerator.getGenericResponse(collectionList));
	}

	@Override
	public ResponseEntity<Map<String, Object>> putItemIntoCollection(String id, Map<String, Object> item,
			HttpServletRequest request) {
		// TODO Auto-generated method stub

		int roleId = (int) request.getAttribute("role_id");

		List<String> collections = collectionsDao.getCollectionsByRoleId(roleId);
		logger.debug("Found collections: " + collections + " " + "for Role: " + roleId);
		List<String> permissions = permissionsDao.getPermissionByCollectionId(id);
		logger.debug("Found permissions: " + permissions + " " + "on Collection: " + id);
		if (!((collections.contains(id)) && (permissions.contains("update")))
				&& !(roleId == UserConstants.ADMIN_ROLE_ID)) {

			throw new UserException("INSUFFICIENT PRIVILIGES", HttpStatus.FORBIDDEN);
		}
		Map<String, Object> createdItem = collectionsDao.putItemIntoCollection(id, item);

		List<Map<String, Object>> itemList = new ArrayList<Map<String, Object>>();
		itemList.add(createdItem);

		logger.debug("Inserted item: " + itemList + " " + "into Collection: " + id);

		return (ResponseGenerator.getGenericResponse(itemList));
	}

	@Override
	public ResponseEntity<Map<String, Object>> getItemsFromCollection(String id, Map<String, String> params,
			HttpServletRequest request) {

		int roleId = (int) request.getAttribute("role_id");

		List<String> collections = collectionsDao.getCollectionsByRoleId(roleId);
		logger.debug("Found collections: " + collections + " " + "for Role: " + roleId);
		List<String> permissions = permissionsDao.getPermissionByCollectionId(id);

		if (!((collections.contains(id)) && (permissions.contains("read")))
				&& !(roleId == UserConstants.ADMIN_ROLE_ID)) {

			throw new UserException("INSUFFICIENT PRIVILIGES", HttpStatus.FORBIDDEN);
		}

		List<Map<String, Object>> items = collectionsDao.getItemsFromCollection(id, params);

		return (ResponseGenerator.getGenericResponse(items));
	}

	@Override
	@Transactional(rollbackFor = { Exception.class, UserException.class })
	public ResponseEntity<Map<String, Object>> updateCollectionMetaData(String id,
			Map<String, Object> updatedCollection, HttpServletRequest request) {

		String newCollectionId = (String) updatedCollection.get(CollectionConstants.COLLECTION_ID);
//		attributes: { columnName: {type: dataType}     }
		@SuppressWarnings("unchecked")
		Map<String, Object> attributes = (Map<String, Object>) updatedCollection.get(CollectionConstants.ATTRIBUTES);

//		listOfAttributes: [{name: columnName, type: dataType, default: defaultValue} ...]

		// new attributed which are to be updated
		List<Map<String, Object>> listOfAttributes = new ArrayList<Map<String, Object>>();

//		attributes: { columnName: {type: dataType}     }

		for (Map.Entry<String, Object> attribute : attributes.entrySet()) {
			if ((((String) attribute.getKey()).equals(CollectionConstants.CREATED_AT))
					|| (((String) attribute.getKey()).equals(CollectionConstants.UPDATED_AT))

					||

					(((String) attribute.getKey()).equals(CollectionConstants.ID))

			) {

				continue;
			}
			Map<String, Object> preparedAttribute = new HashMap<String, Object>();
			preparedAttribute.put(CollectionConstants.NAME, attribute.getKey());
			@SuppressWarnings("unchecked")
			Map<String, Object> attributeValue = (Map<String, Object>) attribute.getValue();
			preparedAttribute.put(CollectionConstants.TYPE, (String) attributeValue.get(CollectionConstants.TYPE));
			preparedAttribute.put(CollectionConstants.DEFAULT, CollectionConstants.EMPTY_STRING);
			listOfAttributes.add(preparedAttribute);

		}

		// create new collection object to create a new collection
		Collection collection = new Collection();
		String duplicateTableName = CollectionConstants.DUPLICATE_COLLECTION_NAME;
		// a collection with name duplicate gets created and renamed to original after
		// dropping old collection
		collection.setCollection_id(duplicateTableName);
		collection.setFields(listOfAttributes);
		List<Collection> response = new ArrayList<Collection>();

		// old attributes of the collection which we need to copy old data intto newly
		// created collection as we dont want to loose data;

		List<Map<String, Object>> listOfOldAttributes = new ArrayList<Map<String, Object>>();

		for (Map.Entry<String, Object> attribute : getCollectionAttributes(id).entrySet()) {

			Map<String, Object> preparedAttribute = new HashMap<String, Object>();
			preparedAttribute.put(CollectionConstants.NAME, attribute.getKey());
			@SuppressWarnings("unchecked")
			Map<String, Object> attributeValue = (Map<String, Object>) attribute.getValue();
			preparedAttribute.put(CollectionConstants.TYPE, (String) attributeValue.get(CollectionConstants.TYPE));
			preparedAttribute.put(CollectionConstants.DEFAULT, CollectionConstants.EMPTY_STRING);
			listOfOldAttributes.add(preparedAttribute);

		}

		// from new attributes we only extract attributes which we need to copy
		// to new table
		// Strategy: from the provided new set of attributes we figure out which
		// attributes are stable and are not changed. Rest will be dumped;

		List<Map<String, Object>> listOfAttributesToCopy = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < listOfAttributes.size(); i++) {
			for (int j = 0; j < listOfOldAttributes.size(); j++) {

				if (((String) listOfAttributes.get(i).get(CollectionConstants.NAME))
						.equals((String) listOfOldAttributes.get(j).get(CollectionConstants.NAME))) {

					listOfAttributesToCopy.add(listOfAttributes.get(i));

				}

			}
		}

		// create a new collection with updated configuration with id as duplicate
		Collection createdCollection = collectionsDao.createCollection(collection);

		// copy data from old collection to newly created collection with id as
		// duplicate

		boolean copyStatus = collectionsDao.copyCollectionContents(id, duplicateTableName, listOfAttributesToCopy);

		// drop old collection
		boolean dropStatus = collectionsDao.dropCollection(id);
		// rename collection from duplicate to new collection name
		boolean alterStatus = collectionsDao.alterCollectionName(duplicateTableName, newCollectionId);
		collection.setCollection_id(newCollectionId);
		response.add(createdCollection);

		return (ResponseGenerator.getGenericResponse(response));
	}

	public Map<String, Object> getCollectionAttributes(String collectionId) {

		return collectionsDao.getCollectionAttributes(collectionId);
	}
}
