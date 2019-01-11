package com.hwj.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "zsd")
public class Zsd implements Serializable {

    private static final long serialVersionUID = -6077048315542290920L;

    private int id;
    private String nodeid;
    private String zsdms;
    private String userid;
    private String mapid;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    @Column(name = "nodeid")
    public String getNodeid() { return nodeid; }
    public void setNodeid(String nodeid) { this.nodeid = nodeid; }

    @Column(name = "zsdms",columnDefinition = "longtext")
    public String getZsdms() { return zsdms; }
    public void setZsdms(String zsdms) { this.zsdms = zsdms; }

    @Column(name = "userid")
    public String getUserid() { return userid; }
    public void setUserid(String userid) { this.userid = userid; }

    @Column
    public String getMapid() { return mapid; }
    public void setMapid(String mapid) { this.mapid = mapid; }

}
