package com.hwj;

import com.hwj.entity.MindMap;
import com.hwj.repository.AdminRepository;
import com.hwj.repository.MindMapRepository;
import com.hwj.service.MindMapService;
import com.hwj.util.JsonAnalyze;
import com.hwj.vo.Mind;
import com.hwj.vo.MindNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MindMapTransfer {

    @Autowired
    private MindMapRepository mindMapRepository;
    @Autowired
    private MindMapService mindMapService;
    @Autowired
    private JsonAnalyze jsonAnalyze;

    @Test
    public void mapDataTransfer() throws Exception{

        List<MindMap> list = mindMapRepository.findAll();


        for (ListIterator<MindMap> it = list.listIterator(); it.hasNext();) {
            MindMap mindMap = it.next();
            List<Mind> mindList = jsonAnalyze.parseMindList(mindMap.getMapList());
            List<MindNode> mapList = new ArrayList<MindNode>();


            for(ListIterator<Mind> it1 = mindList.listIterator(); it1.hasNext();) {
                Mind mind = it1.next();
                MindNode mindNode = new MindNode();
                String color = mind.getColor();

                if (color == null) { color = "原色"; }

                mindNode.setColor(transfer(color));
                mindNode.setParentid(mind.getParentid());
                mindNode.setTopic(mind.getNodename());
                mindNode.setId(mind.getNodeid());

                mapList.add(mindNode);
            }

            mindMap.setCreateDate(new Date());
            mindMap.setUpdateDate(new Date());
            mindMap.setMapList(jsonAnalyze.list2Json(mapList));

            mindMapRepository.save(mindMap);
        }

    }


    public String transfer(String color){
        switch(color){
            case "浅紫":
                color="#CD96CD";
                break;
            case "郁金色":
                color="#fdb933";
                break;
            case "抹茶":
                color="#6BB073";
                break;
            case "咖色":
                color="#BF7F50";
                break;
            case "玫瑰红":
                color="#FF0000";
                break;
            case "原色":
                color="#1abc9c";
                break;
            case "圣诞红":
                color="#BF0A10";
                break;
            case "深紫":
                color="#9b59b6";
                break;
            case "藏青":
                color="#34495e";
                break;
            case "要什么颜色":
                color="#733C80";
                break;
            case "天蓝":
                color="#426ab3";
                break;
            case "砖红":
                color="#e74c3c";
                break;
            case "碳灰":
                color="#404040";
                break;
            case "亮粉":
                color="#ff3399";
                break;
            case "凑数色":
                color="#8B1A1A";
                break;
        }
        return color;
    }




}
