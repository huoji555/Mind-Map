package com.hwj.entityUtil;

public class MindNodeTool {
	private String nodeid;
	private String nodename;
	private String parentid;
	private String userid;
	private String type;

	public String getNodeid() {
		return this.nodeid;
	}

	public void setNodeid(String nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return this.nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public String getParentid() {
		return this.parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String toString() {
		return "MindNodeTool [nodeid=" + this.nodeid + ", nodename="
				+ this.nodename + ", parentid=" + this.parentid + ", userid="
				+ this.userid + ", type=" + this.type + "]";
	}
}
