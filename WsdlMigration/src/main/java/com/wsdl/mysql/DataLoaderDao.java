package com.wsdl.mysql;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

@Component("DataLoaderDao")
public class DataLoaderDao {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	 
	@Value("${insertwsdlSchema.sql}")
	private String insert_query;
	

	@Value("${insert_Attributes_by_class.sql}")
	private String insert_Attributes_by_class;
	
	
	public DataLoaderDao() {
		
	}
	
	public DataLoaderDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

	}
	
	public void create_table(String createTableSQL) throws SQLException {
		  
	    jdbcTemplate.execute(createTableSQL);
	}
	
	
	public void insertData(String wsdl_Name,String className,HashMap<String, String> atrribute_type_map) throws JSONException {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		SqlParameterSource  namedParameters = new MapSqlParameterSource("class_name", className)
				.addValue("wsdl_name",wsdl_Name);
			try {
				namedParameterJdbcTemplate.update(insert_query, namedParameters,generatedKeyHolder, new String[]{"id"});
				Number class_id = generatedKeyHolder.getKey();
				System.err.println("inserted ID  - -------- > "+class_id);
				//Attribute insertion based on class			
				for (String key : atrribute_type_map.keySet()) {
					SqlParameterSource  attributeInsertion = new MapSqlParameterSource("class_id", class_id)
							.addValue("attribute_type",atrribute_type_map.get(key)).addValue("attribute_name", key); 
					namedParameterJdbcTemplate.update(insert_Attributes_by_class, attributeInsertion);
			}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("======== End of inserting data ================");
	}
}
