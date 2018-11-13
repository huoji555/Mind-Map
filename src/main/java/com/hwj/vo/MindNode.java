package com.hwj.vo;

import java.io.Serializable;

public class MindNode implements Serializable {

    private static final long serialVersionUID = 7999070780053586988L;

    private String id;
    private String topic;
    private String parentid;
    private String color;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTopic() { return topic; }
    public void setTopic(String topic) { this.topic = topic; }

    public String getParentid() { return parentid; }
    public void setParentid(String parentid) { this.parentid = parentid; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    Children children = new Children();


    /**
     * @auther: Ragty
     * @describe: 拼接图谱JSON字符串
     * @date: 2018/11/12
     */
    public String toString() {
        String result =  "{id : '" + id + "'" +
                            ", topic : '" + topic + "'" +
                            ", parentid : '" + parentid + "'" +
                            ", color : '" + color + "'";

        if (children != null && children.getSize() != 0) {
            result += ", \'children\' :" + children.toString();
        }

        return result+"}";
    }




    /**
     * @auther: Ragty
     * @describe: 添加孩子节点
     * @param: [mindNode]
     * @return: void
     * @date: 2018/11/12
     */
    public void addChild(MindNode mindNode) {
        this.children.addChild(mindNode);
    }



}
