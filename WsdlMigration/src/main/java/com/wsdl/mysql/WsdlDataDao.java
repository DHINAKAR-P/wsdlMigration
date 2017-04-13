package com.wsdl.mysql;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.wsdl.domain.WsdlData;
import com.wsdl.mapper.WsdlDataMapper;

@Component("WsdlDataDao")
public class WsdlDataDao {

	
	private DataSource dataSource;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	@Resource(name = "dataSource")
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
	
	@Value("${get_wsdls_from_db.sql}")
	private String getWsdlsFromDb;
	
	public List<WsdlData> getWsdlData (Long project_id ,Long user_id) throws Exception{
		MapSqlParameterSource parameters;
		System.out.println("project_id--- > "+project_id);
		
		parameters = new MapSqlParameterSource();
		parameters.addValue("project_id", project_id); 
		WsdlDataMapper wsdlDatamapper = new WsdlDataMapper();
		parameters.addValue("user_id", user_id);
		List<WsdlData> wsdlList = this.namedParameterJdbcTemplate.query(
				getWsdlsFromDb, parameters, wsdlDatamapper);
		
		if(wsdlList.size()<1){
			throw new Exception("WSDL endpoint  for project  " + project_id
					+ " was not found..!");
		}
		
		return wsdlList;
	}
}
