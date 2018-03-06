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
@Table(name = "assesslog")
@DynamicInsert(true)
@DynamicUpdate(true)
public class AssessLog implements Serializable {

	private static final long serialVersionUID = 1473664101334101605L;
	private int id;
	private String username;
	private String ipAddress;
	private String logintime;

	@Id
	@GeneratedValue(generator = "a_native")
	@GenericGenerator(name = "a_native", strategy = "native")
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "ipAddress")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "logintime")
	public String getLogintime() {
		return logintime;
	}

	public void setLogintime(String logintime) {
		this.logintime = logintime;
	}

	@Override
	public String toString() {
		return "AssessLog [id=" + id + ", username=" + username
				+ ", ipAddress=" + ipAddress + ", logintime=" + logintime + "]";
	}

}
