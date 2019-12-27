package com.hwj.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "resc")
@DynamicUpdate(true)
public class Resc implements Serializable {
	
	private static final long serialVersionUID = -6322255288225089891L;
	private Integer id;
	private String name;
	private String remark;

	@Id
	@GeneratedValue(generator = "a_native")
	@GenericGenerator(name = "a_native", strategy = "native")
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
