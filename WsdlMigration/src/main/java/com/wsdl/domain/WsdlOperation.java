package com.wsdl.domain;

import java.io.Serializable;

public class WsdlOperation  implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String operation;
	private String operation_parameters;
	private String returnType;
	private Long wsdl_id;
	private Long class_id;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getOperation_parameters() {
		return operation_parameters;
	}
	public void setOperation_parameters(String operation_parameters) {
		this.operation_parameters = operation_parameters;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Long getWsdl_id() {
		return wsdl_id;
	}
	public void setWsdl_id(Long wsdl_id) {
		this.wsdl_id = wsdl_id;
	}
	public Long getClass_id() {
		return class_id;
	}
	public void setClass_id(Long class_id) {
		this.class_id = class_id;
	}
	@Override
	public String toString() {
		return "WsdlOperation [id=" + id + ", operation=" + operation + ", operation_parameters=" + operation_parameters
				+ ", returnType=" + returnType + ", wsdl_id=" + wsdl_id + ", class_id=" + class_id + "]";
	}
}
