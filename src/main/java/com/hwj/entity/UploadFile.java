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
@Table(name="uploadfile")
@DynamicInsert(true)
@DynamicUpdate(true)
public class UploadFile implements Serializable{

	private static final long serialVersionUID = -5974559092278774248L;
	
	private int no; // 编号
	private String filename; // 资料名称
	private String files; // 资料id
	private String filepath; // 文件在服务器上的路径
	private String oldfilepath; // 旧路径(一个比较没用的路径)
	private String fileroot; // 根路径，服务器在pc端访问的绝对路径
	private String zlms; // 资料描述
	private String uploadtime; // 上传时间
	private String userid; // 用户id
	private String zsdid; // 知识点id (nodeid)节点id
	private String filetype; // 文件类型
	private String fileusertype; // 文件用户类型
	private String f_parentid; // 父 id
	private String firstStatus; // 看它是不是第一次上传
	
	
	
	@Id
	@GeneratedValue(generator = "a_native")
	@GenericGenerator(name = "a_native", strategy = "native")
	@Column(name = "no")
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	@Column(name = "filename")
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	
	@Column(name = "files")
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	
	@Column(name = "filepath")
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	
	@Column(name = "oldfilepath")
	public String getOldfilepath() {
		return oldfilepath;
	}
	public void setOldfilepath(String oldfilepath) {
		this.oldfilepath = oldfilepath;
	}
	
	@Column(name = "fileroot")
	public String getFileroot() {
		return fileroot;
	}
	public void setFileroot(String fileroot) {
		this.fileroot = fileroot;
	}
	
	@Column(name = "zlms")
	public String getZlms() {
		return zlms;
	}
	public void setZlms(String zlms) {
		this.zlms = zlms;
	}
	
	@Column(name = "uploadtime")
	public String getUploadtime() {
		return uploadtime;
	}
	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}
	
	@Column(name = "userid")
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	
	@Column(name = "fileusertype")
	public String getFileusertype() {
		return fileusertype;
	}
	public void setFileusertype(String fileusertype) {
		this.fileusertype = fileusertype;
	}
	
	@Column(name = "f_parentid")
	public String getF_parentid() {
		return f_parentid;
	}
	public void setF_parentid(String f_parentid) {
		this.f_parentid = f_parentid;
	}
	
	@Column(name = "firstStatus")
	public String getFirstStatus() {
		return firstStatus;
	}
	public void setFirstStatus(String firstStatus) {
		this.firstStatus = firstStatus;
	}
	

	@Override
	public String toString() {
		return "UploadFile [no=" + no + ", filename=" + filename + ", files="
				+ files + ", filepath=" + filepath + ", oldfilepath="
				+ oldfilepath + ", fileroot=" + fileroot + ", zlms=" + zlms
				+ ", uploadtime=" + uploadtime + ", userid=" + userid
				+ ", zsdid=" + zsdid + ", filetype=" + filetype
				+ ", fileusertype=" + fileusertype + ", f_parentid="
				+ f_parentid + ", firstStatus=" + firstStatus + "]";
	}
	
}
