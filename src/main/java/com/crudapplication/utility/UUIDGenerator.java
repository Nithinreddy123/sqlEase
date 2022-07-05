package com.crudapplication.utility;

import java.util.UUID;

public class UUIDGenerator {

	public static String getUUID() {
	return	UUID.randomUUID().toString().replace("-", "");
	}
}
