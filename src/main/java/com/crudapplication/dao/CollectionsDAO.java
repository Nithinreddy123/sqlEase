package com.crudapplication.dao;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.crudapplication.entity.Collection;

public interface CollectionsDAO {

	public Collection createCollection(Collection collection);

	public List<String> getCollectionsByRoleId(int roleId);

	public Map<String, Object> getCollectionAttributes(String collectionId);

	public Map<String, Object> putItemIntoCollection(String id, Map<String, Object> item);

	public List<Map<String, Object>> getItemsFromCollection(String collectionId,Map<String,String> params);
	
	public boolean copyCollectionContents(String source,String destination,List<Map<String,Object>> attributes);
	
	public boolean alterCollectionName(String source,String destination);
	
	public boolean dropCollection(String collectionId);
}
