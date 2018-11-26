package com.hwj.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "share_map")
public class ShareMap {

    private Integer id;
    private String mapid;
    private String mapname;
    private String shareId;
    private Date shareDate;
    private Date updateDate;


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

    @Column(name = "shareId")
    public String getShareId() { return shareId; }
    public void setShareId(String shareId) { this.shareId = shareId; }

    @Column(name = "shareDate")
    public Date getShareDate() { return shareDate; }
    public void setShareDate(Date shareDate) { this.shareDate = shareDate; }

    @Column(name = "updateDate")
    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

}
