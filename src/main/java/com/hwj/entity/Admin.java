package com.hwj.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "admin")
public class Admin implements Serializable{

	private static final long serialVersionUID = -3821974217235389232L;
	
	private Integer id;
	private String username;
	private String password;
	private String email;
    private Integer roleId;
    private Date createDate;
	private String ip;
	private String salt;


	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {return id;}
	public void setId(Integer id) {this.id = id;}
	
	@Column(name = "username")
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}
	
	@Column(name = "password")
	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	@Column(name = "email")
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	
	@Column(name = "roleId")
	public Integer getRoleId() {return roleId;}
	public void setRoleId(Integer roleId) {this.roleId = roleId;}
	
	@Column(name = "createDate")
	public Date getCreateDate() {return createDate;}
	public void setCreateDate(Date createDate) {this.createDate = createDate;}
	
	@Column(name = "ip")
	public String getIp() {return ip;}
	public void setIp(String ip) {this.ip = ip;}

	@Column(name = "salt")
	public String getSalt() { return salt; }
	public void setSalt(String salt) { this.salt = salt; }
    
}
