package com.hwj.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name = "admin_error_msg")
public class AdminErrorMsg implements Serializable {

	private static final long serialVersionUID = -9073558092316632462L;
	
	private Integer id;
	private String  adminIp;
	private String  url;
	private Date    create_time;
	private String  method_type;
	private String  controller_name;
	private String  method_name;
	private String  params;                
	private String  error_msg;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {return id;}
	public void setId(Integer id) {	this.id = id;}
	
	@Column(name = "adminIp")
	public String getAdminIp() {return adminIp;}
	public void setAdminIp(String adminIp) {this.adminIp = adminIp;}
	
	@Column(name = "url")
	public String getUrl() {return url;	}
	public void setUrl(String url) {this.url = url;	}
	
	@Column(name = "create_time")
	public Date getCreate_time() {return create_time;}
	public void setCreate_time(Date create_time) {	this.create_time = create_time;}
	
	@Column(name = "method_type")
	public String getMethod_type() {return method_type;}
	public void setMethod_type(String method_type) {this.method_type = method_type;}
	
	@Column(name = "controller_name")
	public String getController_name() {return controller_name;}
	public void setController_name(String controller_name) {this.controller_name = controller_name;}
	
	@Column(name = "method_name")
	public String getMethod_name() {	return method_name;}
	public void setMethod_name(String method_name) {	this.method_name = method_name;}
	
	@Column(name = "params",columnDefinition = "longtext COMMENT '参数信息' ")
	public String getParams() {	return params;}
	public void setParams(String params) {this.params = params;}
	
	@Column(name = "error_msg",columnDefinition = "text COMMENT '异常信息' ")
	public String getError_msg() {return error_msg;}
	public void setError_msg(String error_msg) {this.error_msg = error_msg;}
	
	
	
}
