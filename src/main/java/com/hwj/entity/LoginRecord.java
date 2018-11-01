package com.hwj.entity;

import javax.annotation.Generated;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "login_record")
public class LoginRecord implements Serializable {


    private static final long serialVersionUID = 2465728755097356718L;
    private Integer id;
    private String username;
    private String ip;
    private Integer roleId;
    private Date loginTime;


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Column(name = "username")
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    @Column(name = "ip")
    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    @Column(name = "loginTime")
    public Date getLoginTime() { return loginTime; }
    public void setLoginTime(Date loginTime) { this.loginTime = loginTime; }

    @Column(name = "roleID")
    public Integer getRoleId() { return roleId; }
    public void setRoleId(Integer roleId) { this.roleId = roleId; }

}
