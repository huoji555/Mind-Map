package com.hwj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "functions")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Functions implements Serializable {

	private static final long serialVersionUID = 7878635190411726875L;
	
	private String resourceName;         //模块具体名字
	private String resourceID;           //模块具体名字ID
	private String resourceGrade;        //
	private String accessPath;           //访问该模块的具体地址
	private String parentName;
	private String parentID;
	private String forder;
	private String grandparentName;      //侧栏模块名称
	private String grandparentId;        //侧栏模块id
	private String limits;

	@Id
	@GeneratedValue(generator = "u_assigned")
	@GenericGenerator(name = "u_assigned", strategy = "assigned")
	@Column(name = "resourceName")
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	@Column(name = "resourceID")
	public String getResourceId() {
		return resourceID;
	}

	public void setResourceId(String resourceID) {
		this.resourceID = resourceID;
	}

	@Column(name = "resourceGrade")
	public String getResourceGrade() {
		return resourceGrade;
	}

	public void setResourceGrade(String resourceGrade) {
		this.resourceGrade = resourceGrade;
	}

	@Column(name = "accessPath")
	public String getAccessPath() {
		return accessPath;
	}

	public void setAccessPath(String accessPath) {
		this.accessPath = accessPath;
	}

	@Column(name = "parentName")
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Column(name = "parentID")
	public String getParentId() {
		return parentID;
	}

	public void setParentId(String parentID) {
		this.parentID = parentID;
	}

	@Column(name = "forder")
	public String getForder() {
		return forder;
	}

	public void setForder(String forder) {
		this.forder = forder;
	}

	@Column(name = "grandparentName")
	public String getGrandparentName() {
		return grandparentName;
	}

	public void setGrandparentName(String grandparentName) {
		this.grandparentName = grandparentName;
	}

	@Column(name = "grandparentId")
	public String getGrandparentId() {
		return grandparentId;
	}

	public void setGrandparentId(String grandparentId) {
		this.grandparentId = grandparentId;
	}

	@Column(name = "limits")
	public String getLimits() {
		return limits;
	}

	public void setLimits(String limits) {
		this.limits = limits;
	}

	@Override
	public String toString() {
		return "Functions [resourceName=" + resourceName + ", resourceID="
				+ resourceID + ", resourceGrade=" + resourceGrade
				+ ", accessPath=" + accessPath + ", parentName=" + parentName
				+ ", parentID=" + parentID + ", forder=" + forder
				+ ", grandparentName=" + grandparentName + ", grandparentId="
				+ grandparentId + ", limits=" + limits + "]";
	}

}
