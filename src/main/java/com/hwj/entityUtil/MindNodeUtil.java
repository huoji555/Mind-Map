package com.hwj.entityUtil;

import java.util.ArrayList;
import java.util.List;

import com.hwj.entity.MindNode;

public class MindNodeUtil {
	private String state;
	private String mapid;
	private String nodeid;
	private List<MindNode> list = new ArrayList();
	private String map;

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
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

	public List<MindNode> getList() {
		return this.list;
	}

	public void setList(List<MindNode> list) {
		this.list = list;
	}

	public String getMap() {
		return this.map;
	}

	public void setMap(String map) {
		this.map = map;
	}
}
