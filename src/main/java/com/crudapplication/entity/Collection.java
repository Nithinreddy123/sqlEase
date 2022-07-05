package com.crudapplication.entity;

import java.util.List;
import java.util.Map;

public class Collection {

	private int id;
	private String collection_name;
	private String collection_id;
	private int role;
	private List<String> permissions;
	private List<Map<String, Object>> fields;

	public String getCollection_name() {
		return collection_name;
	}

	public void setCollection_name(String collection_name) {
		this.collection_name = collection_name;
	}

	public String getCollection_id() {
		return collection_id;
	}

	@Override
	public String toString() {
		return "Collection [collection_name=" + collection_name + ", collection_id=" + collection_id + ", roles="
				+ role + ", permissions=" + permissions + ", fields=" + fields + "]";
	}

	public void setCollection_id(String collection_id) {
		this.collection_id = collection_id;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public List<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}

	public List<Map<String, Object>> getFields() {
		return fields;
	}

	public void setFields(List<Map<String, Object>> fields) {
		this.fields = fields;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
