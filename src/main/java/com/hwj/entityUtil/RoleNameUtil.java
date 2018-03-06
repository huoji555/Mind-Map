package com.hwj.entityUtil;

public class RoleNameUtil {
	private Integer id;
	private String roleName;
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public RoleNameUtil(Integer id, String roleName, String remark) {
		super();
		this.id = id;
		this.roleName = roleName;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "RoleNameUtil [id=" + id + ", roleName=" + roleName
				+ ", remark=" + remark + "]";
	}

}
