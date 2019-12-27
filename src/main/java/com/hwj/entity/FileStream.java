package com.hwj.entity;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenericGenerator;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import sun.misc.BASE64Encoder;

@Entity
@Table(name = "filestream")
@DynamicInsert(true)
@DynamicUpdate(true)
public class FileStream implements Serializable {

	private static final long serialVersionUID = -6653607291982029132L;
	
	private int id;
	private String filename;
	private String fileExtension;
	private Blob fileStream;
	private String f_id;
	private String userid;
	private String nodeid;
	private String parentid;
	private String trueUrl;
	private String delStatus;
	private String fileType;
	private String uploadTime;
	private ByteArrayInputStream fileStream1;

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

	@Column(name = "filename")
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "fileExtension")
	public String getFileExtension() {
		return fileExtension;
	}

	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}

	@Column(name = "userid")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "nodeid")
	public String getNodeid() {
		return nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	@Column(name = "parentid")
	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	@Column(name = "trueUrl")
	public String getTrueUrl() {
		return trueUrl;
	}

	public void setTrueUrl(String trueUrl) {
		this.trueUrl = trueUrl;
	}

	@Column(name = "f_id")
	public String getF_id() {
		return f_id;
	}

	public void setF_id(String f_id) {
		this.f_id = f_id;
	}

	@Lob
	@JsonIgnore
	@Column(name = "fileStream", columnDefinition = "longblob")
	public Blob getFileStream() {
		return fileStream;
	}

	public void setFileStream(Blob fileStream) {
		this.fileStream = fileStream;
	}

	@Column(name = "delStatus")
	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
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

	@Lob
	@Column(name = "fileStream1")
	public ByteArrayInputStream getFileStream1() {
		return fileStream1;
	}

	public void setFileStream1(ByteArrayInputStream fileStream1) {
		this.fileStream1 = fileStream1;
	}

	@Override
	public String toString() {
		return "FileStream [id=" + id + ", filename=" + filename
				+ ", fileExtension=" + fileExtension + ", fileStream="
				+ fileStream + ", f_id=" + f_id + ", userid=" + userid
				+ ", nodeid=" + nodeid + ", parentid=" + parentid
				+ ", trueUrl=" + trueUrl + ", delStatus=" + delStatus
				+ ", fileType=" + fileType + ", uploadTime=" + uploadTime
				+ ", fileStream1=" + fileStream1 + "]";
	}

}
