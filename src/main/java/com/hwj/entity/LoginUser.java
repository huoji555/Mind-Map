package com.hwj.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user")
@DynamicUpdate(true)
public class LoginUser implements Serializable {
	
	private static final long serialVersionUID = 4773599809397586094L;
	/**
	 * nickName 昵称 password 密码  role 角色    phoneNumber 电话      email 邮箱   realName 真实姓名
	 */
	private String nickName;
	private String password;
	private String phoneNumber;
	private String email;
	private String realName;
	private int roleId;


	@Id
	@GeneratedValue(generator = "u_assigned")
	@GenericGenerator(name = "u_assigned", strategy = "assigned")
	@Column(name = "name")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "phoneNumber")
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "realName")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}

	
	@Column(name = "roleid")
	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	
	@Override
	public String toString() {
		return "LoginUser [nickName=" + nickName + ", password=" + password
				+ ", phoneNumber=" + phoneNumber + ", email=" + email
				+ ", realName=" + realName + ", roleId=" + roleId + "]";
	}
	
	
}
