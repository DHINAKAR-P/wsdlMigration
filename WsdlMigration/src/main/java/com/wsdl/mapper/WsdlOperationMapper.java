package com.wsdl.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.wsdl.domain.WsdlOperation;

public class WsdlOperationMapper  implements RowMapper<WsdlOperation>  {

	@Override
	public WsdlOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
		WsdlOperation wsdlOperation = new WsdlOperation();
		wsdlOperation.setId(rs.getLong("id"));
		wsdlOperation.setClass_id(rs.getLong("class_id"));
		wsdlOperation.setWsdl_id(rs.getLong("wsdl_id"));
		wsdlOperation.setOperation(rs.getString("operation"));
		wsdlOperation.setOperation_parameters(rs.getString("operation_parameters"));
		wsdlOperation.setReturnType(rs.getString("returnType"));
		
		return wsdlOperation;
	}

}
