package com.hwj.entity;

import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = -3811696279037111626L;

    private Integer id;
    private Integer roleId;
    private Date    createDate;
    private Date    updateDate;
    private String  roleName;
    private String  roleContent;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Column(name = "create_date")
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    @Column(name = "update_date")
    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

    @Column(name = "role_name", nullable = false)
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }

    @Column(name = "role_content")
    public String getRoleContent() { return roleContent; }
    public void setRoleContent(String roleContent) { this.roleContent = roleContent; }

    @Column(name = "role_id")
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

}
