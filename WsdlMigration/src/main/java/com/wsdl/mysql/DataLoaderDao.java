package com.wsdl.mysql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

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

import com.wsdl.domain.WsdlClass;
import com.wsdl.domain.WsdlData;

@Component("DataLoaderDao")
public class DataLoaderDao {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	 
	@Value("${insertwsdlSchema.sql}")
	private String insert_query;
	

	@Value("${insert_Attributes_by_class.sql}")
	private String insert_Attributes_by_class;
	
	@Value("${insert_operation_by_wsdl.sql}")
	private String insertOperationByWsdl;
	
	
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
	
	
	public void insertData(String wsdl_Name,String className,HashMap<String, String> atrribute_type_map,Long projectId,Long userId,Long wsdl_id) throws JSONException {
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		
		SqlParameterSource  namedParameters = new MapSqlParameterSource("class_name", className)
				.addValue("wsdl_name",wsdl_Name)
				.addValue("project_id",projectId)
				.addValue("wsdl_id",wsdl_id)
				.addValue("user_id",userId);
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
				e.printStackTrace();
			}
		System.out.println("======== End of inserting data ================");
	}

	public void insertMethod(HashMap<String, List<String>> s,String returnTypeOfClass,WsdlData wsdldata,WsdlClass wsdlClass) {

		
		SqlParameterSource  namedParameters = new MapSqlParameterSource("class_name", wsdlClass.getClassName())
				.addValue("wsdl_name",wsdlClass.getWsdlname())
				.addValue("project_id",wsdlClass.getProjectId())
				.addValue("wsdl_id",wsdlClass.getWsdlId())
				.addValue("user_id",wsdlClass.getUserId());
		
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
			try {
				namedParameterJdbcTemplate.update(insert_query, namedParameters,generatedKeyHolder, new String[]{"id"});
				Number class_id = generatedKeyHolder.getKey();
				System.err.println("METHOD DAO CLASS INSERTION  - -------- > "+class_id);
				//Attribute insertion based on class			
				for(String key : s.keySet()) {
					SqlParameterSource  attributeInsertion = new MapSqlParameterSource("operation", key)
							.addValue("returnType", returnTypeOfClass)
							.addValue("operation_parameters",s.get(key).toString())
							.addValue("class_id", class_id)
							.addValue("wsdl_id", wsdldata.getId());
					
					namedParameterJdbcTemplate.update(insertOperationByWsdl, attributeInsertion);
			}
			} catch (Exception e) {
				e.printStackTrace();
			}
		System.err.println("======== method inserted ================");
		
		
	}
}
