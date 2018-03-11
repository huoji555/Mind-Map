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
@Table(name = "fileCollection")
@DynamicInsert(true)
@DynamicUpdate(true)
public class FileCollection implements Serializable {

	private static final long serialVersionUID = -5459311789311940903L;
	private int id; // 资料编号
	private String f_id; // 文件id
	private String f_name; // 文件名称
	private String fileRoot; // 文件在pc端上的地址
	private String filePath; // 文件在服务器上的位置
	private String userid; // 文件分享者
	private String fileType; // 收藏的文件类型
	private String uploadTime; // 收藏文件的上传时间
	private String nodeid; // 节点id

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

	@Column(name = "f_id")
	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	@Column(name = "f_name")
	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	@Column(name = "fileRoot")
	public String getFileRoot() {
		return fileRoot;
	}

	public void setFileRoot(String fileRoot) {
		this.fileRoot = fileRoot;
	}

	@Column(name = "filePath")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "userid")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "fileType")
	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	@Column(name = "uploadTime")
	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	@Override
	public String toString() {
		return "FileCollection [id=" + id + ", f_id=" + f_id + ", f_name="
				+ f_name + ", fileRoot=" + fileRoot + ", filePath=" + filePath
				+ ", userid=" + userid + ", fileType=" + fileType
				+ ", uploadTime=" + uploadTime + ", nodeid=" + nodeid + "]";
	}
}
