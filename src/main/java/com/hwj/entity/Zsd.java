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
@Table(name = "zsd")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Zsd implements Serializable{

	private static final long serialVersionUID = 2687978210627282386L;
	
	private String zsdid;
	private String zsdmc;
	private String zsdms;
	private String userid;
	
	
	@Id
	@GeneratedValue(generator = "u_assigned")
	@GenericGenerator(name = "u_assigned", strategy = "assigned")
	@Column(name = "zsdid")
	public String getZsdid() {
		return zsdid;
	}
	public void setZsdid(String zsdid) {
		this.zsdid = zsdid;
	}
	
	
	@Column(name = "zsdmc")
	public String getZsdmc() {
		return zsdmc;
	}
	public void setZsdmc(String zsdmc) {
		this.zsdmc = zsdmc;
	}
	
	
	@Column(name = "zsdms", columnDefinition = "longtext")
	public String getZsdms() {
		return zsdms;
	}
	public void setZsdms(String zsdms) {
		this.zsdms = zsdms;
	}
	
	
	@Column(name = "userid")
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	
	@Override
	public String toString() {
		return "zsd [zsdid=" + zsdid + ", zsdmc=" + zsdmc + ", zsdms=" + zsdms
				+ ", userid=" + userid + "]";
	}
	
	
}
