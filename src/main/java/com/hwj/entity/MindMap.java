package com.hwj.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "mind_map",
        indexes = {@Index(name = "mapid",columnList = "mapid")})
public class MindMap implements Serializable {

    private static final long serialVersionUID = -4169287576837116459L;
    private Integer id;
    private String mapid;          //图谱ID
    private String mapname;        //图谱名称
    private String userid;         //图谱作者
    private String mapList;        //图谱数据(内容)
    private Date createDate;       //创建时间
    private Date updateDate;       //更新时间


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Column(name = "mapid")
    public String getMapid() { return mapid; }
    public void setMapid(String mapid) { this.mapid = mapid; }

    @Column(name = "mapname")
    public String getMapname() { return mapname; }
    public void setMapname(String mapname) { this.mapname = mapname; }

    @Column(name = "userid")
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }

    @Column(name = "mapList",columnDefinition = "longtext")
    public String getMapList() { return mapList; }
    public void setMapList(String mapList) { this.mapList = mapList; }

    @Column(name = "createDate")
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    @Column(name = "updateDate")
    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }


}
