package com.wsdl.domain;

public class WsdlAttributes {

	private Long id;
	private String attributeType;
	private  String attributename;
	private Long classId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAttributeType() {
		return attributeType;
	}
	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}
	public String getAttributename() {
		return attributename;
	}
	public void setAttributename(String attributename) {
		this.attributename = attributename;
	}
	public Long getClassId() {
		return classId;
	}
	public void setClassId(Long classId) {
		this.classId = classId;
	}
	@Override
	public String toString() {
		return "WsdlAttributes [id=" + id + ", attributeType=" + attributeType + ", attributename=" + attributename
				+ ", classId=" + classId + "]";
	}
	
}
