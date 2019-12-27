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
@Table(name = "shareMind")
@DynamicInsert(true)
@DynamicUpdate(true)
public class Share implements Serializable{
	
	private static final long serialVersionUID = 6092077883073291366L;
	
	private Integer id;
	private String mindName;  //知识图谱名称
	private String sharetime; // 分享时间
	private String sharetype; // 分享类型
	private String userid;    // 用户
	private String zlid;      // 资料id
	private String zsdid;     // 知识点id
	private String filetype; // 文件类型 
	
	
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
	
	
	@Column(name = "mindName")
	public String getMindName() {
		return mindName;
	}
	public void setMindName(String mindName) {
		this.mindName = mindName;
	}
	
	
	@Column(name = "sharetime")
	public String getSharetime() {
		return sharetime;
	}
	public void setSharetime(String sharetime) {
		this.sharetime = sharetime;
	}
	
	
	@Column(name = "sharetype")
	public String getSharetype() {
		return sharetype;
	}
	public void setSharetype(String sharetype) {
		this.sharetype = sharetype;
	}
	
	
	@Column(name = "userid")
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
	@Column(name = "zlid")
	public String getZlid() {
		return zlid;
	}
	public void setZlid(String zlid) {
		this.zlid = zlid;
	}
	
	
	@Column(name = "zsdid")
	public String getZsdid() {
		return zsdid;
	}
	public void setZsdid(String zsdid) {
		this.zsdid = zsdid;
	}
	
	
	@Column(name = "filetype")
	public String getFiletype() {
		return filetype;
	}
	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}
	
	
	
	@Override
	public String toString() {
		return "Share [id=" + id + ", mindName=" + mindName + ", sharetime="
				+ sharetime + ", sharetype=" + sharetype + ", userid=" + userid
				+ ", zlid=" + zlid + ", zsdid=" + zsdid + ", filetype="
				+ filetype + "]";
	}
	
}
