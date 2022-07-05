package com.crudapplication.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crudapplication.constants.CollectionConstants;
import com.crudapplication.entity.Collection;

@RestController
public interface CollectionsController {

	@RequestMapping(method = RequestMethod.POST, value = CollectionConstants.ROUTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> createCollection(@RequestBody Collection collection,
			HttpServletRequest request);

	@RequestMapping(method = RequestMethod.GET, value = CollectionConstants.ROUTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getCollections(HttpServletRequest request);

	@RequestMapping(method = RequestMethod.POST, value = CollectionConstants.ITEM_ROUTE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> putItemIntoCollection(@PathVariable String id,
			@RequestBody Map<String, Object> item, HttpServletRequest request);
	
	@RequestMapping(method = RequestMethod.PATCH, value = CollectionConstants.ITEM_ROUTE_ID, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateCollectionItem(@PathVariable String collectionId,@PathVariable String itemId,
			@RequestBody Map<String, Object> item, HttpServletRequest request);

	@RequestMapping(method = RequestMethod.GET, value = (CollectionConstants.ITEM_ROUTE), produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> getItemsFromCollection(@PathVariable String id,
			@RequestParam Map<String, String> params, HttpServletRequest request);
	
	@RequestMapping(method = RequestMethod.PATCH, value = (CollectionConstants.ROUTE_WITH_PATH_VARIABLE), produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> updateCollectionMetaData (@PathVariable String id,
			@RequestBody Map<String,Object> updatedCollection, HttpServletRequest request);
}
