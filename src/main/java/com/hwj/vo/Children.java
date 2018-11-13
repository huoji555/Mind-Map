package com.hwj.vo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Children {

    private List list = new ArrayList();          //存放节点数组

    public int getSize(){ return list.size(); }   //当前数组长度


    /**
     * @auther: Ragty
     * @describe: 拼接孩子节点的JSON字符串
     * @param: []
     * @return: java.lang.String
     * @date: 2018/11/12
     */
    public String toString() {

        String result = "[";

        for (Iterator it = list.iterator(); it.hasNext();) {
            result += ( (MindNode)it.next() ).toString();
            result += ",";
        }

        result = result.substring(0, result.length()-1);
        result += "]";
        return result;
    }



    public void  addChild(MindNode mindNode) {
        list.add(mindNode);
    }



}
