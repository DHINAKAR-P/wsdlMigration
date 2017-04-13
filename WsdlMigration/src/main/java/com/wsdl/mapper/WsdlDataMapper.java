package com.wsdl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.wsdl.domain.WsdlData;

public class WsdlDataMapper  implements RowMapper<WsdlData>  {

	@Override
	public WsdlData mapRow(ResultSet rs, int rowNum) throws SQLException {
		WsdlData theWsdl = new WsdlData();
		theWsdl.setId(rs.getLong("id"));
		theWsdl.setWsdl_endpoint(rs.getString("wsdl_endpoint"));
		theWsdl.setProject_id(rs.getLong("project_id"));
		theWsdl.setUser_id(rs.getLong("user_id"));
		return theWsdl;
	}

}
