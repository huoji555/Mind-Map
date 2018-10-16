package com.hwj.entity;

import java.io.Serializable;

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
    
}
