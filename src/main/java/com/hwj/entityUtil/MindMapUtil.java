package com.hwj.entityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MindMapUtil {
	private List<Map<String, String>> rows = new ArrayList();
	private String mapid;
	private String nodeid;
	private String url;
	private String type;
	private String datas;
	private String gaishu;
	private String state;
	private String fileName;
	private String imgurl;
	private String name;
	private String teacher;
	private String jianyi;
	private String coursetime;
	private String jsondata;

	public List<Map<String, String>> getRows() {
		return this.rows;
	}

	public void setRows(List<Map<String, String>> rows) {
		this.rows = rows;
	}

	public String getMapid() {
		return this.mapid;
	}

	public void setMapid(String mapid) {
		this.mapid = mapid;
	}

	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDatas() {
		return this.datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}

	public String getGaishu() {
		return this.gaishu;
	}

	public void setGaishu(String gaishu) {
		this.gaishu = gaishu;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeacher() {
		return this.teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getJianyi() {
		return this.jianyi;
	}

	public void setJianyi(String jianyi) {
		this.jianyi = jianyi;
	}

	public String getCoursetime() {
		return this.coursetime;
	}

	public void setCoursetime(String coursetime) {
		this.coursetime = coursetime;
	}

	public String getJsondata() {
		return this.jsondata;
	}

	public void setJsondata(String jsondata) {
		this.jsondata = jsondata;
	}
}
