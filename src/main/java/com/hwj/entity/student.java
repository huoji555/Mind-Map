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
@Table(name="student")
@DynamicInsert(true)
@DynamicUpdate(true)
public class student  implements Serializable{
	
	
	private static final long serialVersionUID = -4413835171248641817L;
	
	private int     id;
	private String  name;
	private String  password;
	
	
	@Id
	@GeneratedValue(generator="a_native")
	@GenericGenerator(name="a_native",strategy="native")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public student() {
	}
	
	@Override
	public String toString() {
		return "student [id=" + id + ", name=" + name + ", password="
				+ password + "]";
	}
	

}
