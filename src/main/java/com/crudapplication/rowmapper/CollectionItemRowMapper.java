package com.crudapplication.rowmapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

public class CollectionItemRowMapper implements RowMapper<Map<String, Object>> {

	private final Logger logger = LoggerFactory.getLogger(CollectionItemRowMapper.class);

	@Override
	public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub

		Map<String, Object> item = new HashMap<String, Object>();

		ResultSetMetaData meta = rs.getMetaData();
		int ncols = meta.getColumnCount();

		for (int colc = 1; colc <= ncols; colc++) {

			String label = meta.getColumnLabel(colc);
			Object value = rs.getObject(colc);
			item.put(label, value);

		}

		return item;
	}

}
