package com.wsdl.domain;

import java.io.Serializable;

public class WsdlData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String wsdl_endpoint;
	
	private Long project_id;
	
	private Long user_id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWsdl_endpoint() {
		return wsdl_endpoint;
	}

	public void setWsdl_endpoint(String wsdl_endpoint) {
		this.wsdl_endpoint = wsdl_endpoint;
	}

	public Long getProject_id() {
		return project_id;
	}

	public void setProject_id(Long project_id) {
		this.project_id = project_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WsdlData [id=" + id + ", wsdl_endpoint=" + wsdl_endpoint + ", project_id=" + project_id + ", user_id="
				+ user_id + "]";
	}
	
}
