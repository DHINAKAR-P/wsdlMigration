package com.wsdl.mysql;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

@Component("DataLoaderDao")
public class DataLoaderDao {

	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private JdbcTemplate jdbcTemplate;
	 
	@Value("${insertwsdlSchema.sql}")
	private String insert_query;
	
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
	
	@SuppressWarnings("rawtypes")
	Map namedParameters = new HashMap();
	@SuppressWarnings("unchecked")
	public void insertData(String className,List<String> nounAttributes) throws JSONException {
		//System.out.println("======== Begin inserting data ================");
		//query = "INSERT INTO Agentdetail(`entryid`, `form`, `link`, `position`, `unid`, `noteid`, `href`, `read`, `$1`, `siblings`)"
		//		+ "VALUES(:entryid, :form, :link, :position, :unid, :noteid, :href, :read, :$1, :siblings)";
		
	 
		String kk = insert_query;
		//System.out.println("nounAttributes.toString().toString()- >  "+nounAttributes.toString().toString());
		
		System.out.println("kk - > "+kk);
			namedParameters.put("db_name", "wsdldata");
			namedParameters.put("collection_name", className);
			namedParameters.put("attribute",nounAttributes.toString().toString());
			try {
				namedParameterJdbcTemplate.update(kk, namedParameters);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println("======== End of inserting data ================");
	}
}
