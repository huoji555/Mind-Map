package com.hwj.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 8620977251597416239L;

    private Integer id;
    private String  icon;
    private String  title;
    private Date    createDate;
    private Date    updateDate;
    private String  href;
    private String  parentid;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    @Column(name = "icon")
    public String getIcon() { return icon; }
    public void setIcon(String icon) { this.icon = icon; }

    @Column(name = "title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @Column(name = "create_date")
    public Date getCreateDate() { return createDate; }
    public void setCreateDate(Date createDate) { this.createDate = createDate; }

    @Column(name = "update_date")
    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

    @Column(name = "href")
    public String getHref() { return href; }
    public void setHref(String href) { this.href = href; }

    @Column(name = "parentid")
    public String getParentid() { return parentid; }
    public void setParentid(String parentid) { this.parentid = parentid; }

}
