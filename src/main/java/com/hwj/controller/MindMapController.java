package com.hwj.controller;

import com.google.common.collect.Maps;
import com.hwj.entity.MindMap;
import com.hwj.service.MindMapService;
import com.hwj.util.JsonAnalyze;
import com.hwj.util.ResultBean;
import com.hwj.vo.MindNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.Topic;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/mindmap")
public class MindMapController {

    @Autowired
    private MindMapService mindMapService;
    @Autowired
    private JsonAnalyze jsonAnalyze;



    /**
     * @auther: Ragty
     * @describe: 新建图谱
     * @param: [nodeName, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/11/8
     */
    @PostMapping("/newMap")
    public ResultBean<Map<String,Object>> newMap(@RequestParam String nodeName,
                                                 HttpServletRequest request) throws Exception{

        Map<String, Object> result = Maps.newHashMap();

        HttpSession session = request.getSession();
        String adminId = String.valueOf(session.getAttribute("admin"));
        String mapid = UUID.randomUUID().toString().replace("-","");

        if (adminId.equals("null") || adminId == "null") {
            result.put("status",201);
            result.put("message","登陆超时");
            return new ResultBean<>(result);
        }


        /*数据保存*/
        MindNode mindNode = new MindNode();
        mindNode.setId(mapid);
        mindNode.setParentid("00100");
        mindNode.setTopic(nodeName);

        List<MindNode> mapList = new ArrayList();
        mapList.add(mindNode);

        MindMap mindMap = new MindMap();
        mindMap.setMapid(mapid);
        mindMap.setMapList(jsonAnalyze.list2Json(mapList));
        mindMap.setUserid(adminId);
        mindMap.setCreateDate(new Date());
        mindMap.setUpdateDate(new Date());

        mindMapService.save(mindMap);

        result.put("status", 200);
        result.put("datas", mindMapService.openMind(mapList));
        result.put("mapid", mapid);

        return new ResultBean<>(result);
    }



    /**
     * @auther: Ragty
     * @describe: 新建子节点
     * @param: [nodeid, topic, parentid, mapid, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/11/16
     */
    @PostMapping("/addNode")
    public ResultBean<Map<String,Object>> addNode(@RequestParam String nodeid, @RequestParam String topic,
                                                  @RequestParam String parentid, @RequestParam String mapid,
                                                  HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        HttpSession session = request.getSession();
        String adminId = String.valueOf(session.getAttribute("admin"));


        if (adminId.equals("null") || adminId == "null") {
            result.put("status",201);
            result.put("message","登录超时");
            return new ResultBean<>(result);
        }

        MindMap mindMap = mindMapService.queryMindByMapid(mapid);
        String mindUser = mindMap.getUserid();
        String mapList = mindMap.getMapList();

        if (!adminId.equals(mindUser)){
            result.put("status",201);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        MindNode mindNode = new MindNode();
        mindNode.setId(nodeid);
        mindNode.setTopic(topic);
        mindNode.setParentid(parentid);

        List<MindNode> list = jsonAnalyze.parseList(mapList);
        list.add(mindNode);

        mindMap.setMapList(jsonAnalyze.list2Json(list));
        mindMap.setUpdateDate(new Date());
        mindMapService.save(mindMap);

        result.put("status",200);
        result.put("message","修改成功");
        return new ResultBean<>(result);
    }





}
