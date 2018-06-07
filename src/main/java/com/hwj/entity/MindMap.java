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
	private String nodeid;                  //图谱id
	private String nodename;                //图谱名称
	private String data;                    //图谱数据
	private String userid;                  //图谱作者
	private String realname;                //图谱作者真实姓名


	@Id
	@GeneratedValue(generator = "u_assigned")
	@GenericGenerator(name = "u_assigned", strategy = "assigned")
	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}
	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}
	
	@Column(name = "nodename")
	public String getNodename() {
		return nodename;
	}
	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	@Column(name = "data", columnDefinition = "longtext")
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	@Column(name = "userid")
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Column(name = "realname")
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	
	
	
	@Override
	public String toString() {
		return "MindMap [nodeid=" + nodeid + ", nodename=" + nodename
				+ ", data=" + data + ", userid=" + userid + ", realname="
				+ realname + "]";
	}
	
	
}
