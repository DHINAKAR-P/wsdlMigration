package com.wsdl.domain;

public class WsdlClass {

	private Long id;
	private String className;
	private String wsdlname;
	private Long projectId;
	private Long userId;
	private Long wsdlId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getWsdlname() {
		return wsdlname;
	}
	public void setWsdlname(String wsdlname) {
		this.wsdlname = wsdlname;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getWsdlId() {
		return wsdlId;
	}
	public void setWsdlId(Long wsdlId) {
		this.wsdlId = wsdlId;
	}
	@Override
	public String toString() {
		return "WsdlClass [id=" + id + ", className=" + className + ", wsdlname=" + wsdlname + ", projectId="
				+ projectId + ", userId=" + userId + ", wsdlId=" + wsdlId + "]";
	}
	
}
