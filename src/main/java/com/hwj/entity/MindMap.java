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
@Table(name = "mindMap")
@DynamicInsert(true)
@DynamicUpdate(true)
public class MindMap implements Serializable {
	
	private static final long serialVersionUID = 1177531226916736908L;
	private String id;
	private String data;
	private String uid;
	private String name;

	@Id
	@GeneratedValue(generator = "u_assigned")
	@GenericGenerator(name = "u_assigned", strategy = "assigned")
	@Column(name = "mapid")
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "data")
	public String getData() {
		return this.data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Column(name = "nickName")
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "MindMap [id=" + this.id + ", data=" + this.data + ", uid="
				+ this.uid + ", name=" + this.name + "]";
	}
}
