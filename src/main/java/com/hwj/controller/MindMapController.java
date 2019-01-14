package com.hwj.controller;

import com.google.common.collect.Maps;
import com.hwj.entity.MindMap;
import com.hwj.service.AdminService;
import com.hwj.service.MindMapService;
import com.hwj.service.ShareMapService;
import com.hwj.service.ZsdService;
import com.hwj.util.JsonAnalyze;
import com.hwj.util.ResultBean;
import com.hwj.vo.MindNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.listener.Topic;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("/mindmap")
public class MindMapController {

    @Autowired
    private MindMapService mindMapService;
    @Autowired
    private ShareMapService shareMapService;
    @Autowired
    private ZsdService zsdService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private RedisTemplate redisTemplate;
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

        String adminId = adminService.getCurrentUser(request);
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
        mindMap.setMapname(nodeName);
        mindMap.setMapList(jsonAnalyze.list2Json(mapList));
        mindMap.setUserid(adminId);
        mindMap.setCreateDate(new Date());
        mindMap.setUpdateDate(new Date());

        mindMapService.save(mindMap);

        result.put("status", 200);
        result.put("datas", mindMapService.openMind(mapList,null));
        result.put("mapid", mapid);
        result.put("mapUser",adminId);

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
        String adminId = adminService.getCurrentUser(request);


        if (adminId.equals("null") || adminId == "null") {
            result.put("status",201);
            result.put("message","登录超时");
            return new ResultBean<>(result);
        }

        MindMap mindMap = mindMapService.queryMindByMapid(mapid);
        String mindUser = mindMap.getUserid();
        String mapList = mindMap.getMapList();

        if (!adminId.equals(mindUser)){
            result.put("status",400);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        MindNode mindNode = new MindNode();
        mindNode.setId(nodeid);
        mindNode.setTopic(topic);
        mindNode.setParentid(parentid);

        List<MindNode> list = jsonAnalyze.parseList(mapList);
        list.add(mindNode);

        mindMapService.updateMindMap(jsonAnalyze.list2Json(list), new Date(), null, mindMap.getId());

        result.put("status",200);
        result.put("message","修改成功");
        return new ResultBean<>(result);
    }



    /**
     * @auther: Ragty
     * @describe: 修改节点名称
     * @param: [nodeid, nodename, mapid, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/11/16
     */
    @PostMapping("/modifyNode")
    public ResultBean<Map<String,Object>> modifyNode(@RequestParam String nodeid, @RequestParam String nodename,
                                                     @RequestParam String mapid, HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        String adminId = adminService.getCurrentUser(request);


        if (adminId.equals("null") || adminId == "null") {
            result.put("status",201);
            result.put("message","登录超时");
            return new ResultBean<>(result);
        }

        MindMap mindMap = mindMapService.queryMindByMapid(mapid);
        String mindUser = mindMap.getUserid();
        String mapList = mindMap.getMapList();
        String mapname = null;

        if (!adminId.equals(mindUser)){
            result.put("status",400);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        //use the ListIterator (iterator update)
        List<MindNode> list = jsonAnalyze.parseList(mapList);

        for (ListIterator<MindNode> it = list.listIterator(); it.hasNext();) {
            MindNode mindNode =  it.next();

            if (mindNode.getId().equals(nodeid)){
                mindNode.setTopic(nodename);
                it.set(mindNode);
            }
        }

        //if it's roottid ,modify the mapname
        if (mapid.equals(nodeid)) { mapname = nodename; }

        mindMapService.updateMindMap(jsonAnalyze.list2Json(list), new Date(), mapname, mindMap.getId());

        result.put("status",200);
        result.put("message","修改成功");
        return new ResultBean<>(result);
    }




    /**
     * @auther: Ragty
     * @describe: 删除节点
     * @param: [nodeid, mapid, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/11/17
     */
    @PostMapping("/deleteNode")
    public ResultBean<Map<String,Object>> deleteNode(@RequestParam String nodeid, @RequestParam String mapid,
                                                     HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        String adminId = adminService.getCurrentUser(request);

        MindMap mindMap = mindMapService.queryMindByMapid(mapid);
        ValueOperations ops = redisTemplate.opsForValue();
        String mindUser = mindMap.getUserid();
        String mapList = mindMap.getMapList();

        if (!adminId.equals(mindUser)){
            result.put("status",400);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        //delete part(Add the Redis)
        if (nodeid.equals(mapid)) {
            mindMapService.deleteMap(mapid);
            mindMapService.delRedisCache(jsonAnalyze.parseList(mapList));
            shareMapService.delete(mapid);
            zsdService.deleteByMapid(mapid);
            result.put("status",200);
            result.put("message","删除成功");
            return new ResultBean<>(result);
        }

        //获取自己以及之后的节点
        List<MindNode> list = jsonAnalyze.parseList(mapList);
        List<MindNode> storage = new ArrayList<>();

        //之后是循环操作这个，删除每个节点上的数据
        List<MindNode> delList = mindMapService.getChild(list,nodeid,storage);
        for(ListIterator<MindNode> it = delList.listIterator(); it.hasNext();) {
            MindNode mindNode = it.next();
            redisTemplate.delete("zsd"+mindNode.getId());
            zsdService.deleteByNodeid(mindNode.getId());
        }

        List<MindNode> delAfter = mindMapService.getNope(delList,list);

        mindMapService.updateMindMap(jsonAnalyze.list2Json(delAfter),new Date(), null, mindMap.getId());

        result.put("status",200);
        result.put("message","删除成功");
        return new ResultBean<>(result);

    }




    /**
     * @auther: Ragty
     * @describe: 显示子节点
     * @param: [nodeid, mapid]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/11/19
     */
    @PostMapping("/getChildMap")
    public ResultBean<Map<String,Object>> getChildMap(@RequestParam String nodeid,
                                                      @RequestParam String mapid) throws Exception{

        Map<String,Object> result = Maps.newHashMap();

        MindMap mindMap = mindMapService.queryMindByMapid(mapid);
        String mapList = mindMap.getMapList();

        List<MindNode> list = jsonAnalyze.parseList(mapList);
        List<MindNode> storage = new ArrayList<>();
        List<MindNode> childList = mindMapService.getChild(list,nodeid,storage);
        String parentid = null;

        for (Iterator<MindNode> it = childList.iterator(); it.hasNext();) {
            MindNode mindNode = it.next();

            if ( mindNode.getId().equals(nodeid) ) {
                parentid = mindNode.getParentid();
            }
        }

        result.put("status", 200);
        result.put("datas", mindMapService.openMind(childList,parentid));
        return new ResultBean<>(result);
    }





    /**
     * @auther: Ragty
     * @describe: 打开完整图谱
     * @param: [mapid]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/11/20
     */
    @PostMapping("/openMap")
    public ResultBean<Map<String,Object>> openMap(@RequestParam String mapid) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        MindMap mindMap = mindMapService.queryMindByMapid(mapid);

        List<MindNode> list = jsonAnalyze.parseList(mindMap.getMapList());

        result.put("status", 200);
        result.put("datas", mindMapService.openMind(list,null));
        result.put("mapid",mindMap.getMapid());
        result.put("mapUser",mindMap.getUserid());
        return new ResultBean<>(result);

    }




    /**
     * @auther: Ragty
     * @describe: 获取自己的知识图谱
     * @param: [userid, mapname, firstDate, lastDate, request]
     * @return: com.hwj.util.ResultBean<org.springframework.data.domain.Page<com.hwj.entity.MindMap>>
     * @date: 2018/11/20
     */
    @GetMapping("/getMyMap")
    public ResultBean<Page<MindMap>> getMyMap(@RequestParam String page, @RequestParam String size,
                                              HttpServletRequest request) throws Exception{

        String adminId = adminService.getCurrentUser(request);

        Sort sort = new Sort(Sort.Direction.DESC,"update_date");
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size), sort);

        Page<MindMap> list = mindMapService.queryMindMapByPage(adminId, "", null, null, pageable);
        return new ResultBean<>(list);
    }




    /**
     * @auther: Ragty
     * @describe: 拖拽保存
     * @param: [requestJsonBody, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2019/1/2
     */
    @PostMapping("/saveMapPosition")
    public ResultBean<Map<String,Object>> saveMapPosition(@RequestBody String requestJsonBody,
                                                          HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        String adminId = adminService.getCurrentUser(request);

        Map<String,Object> map = jsonAnalyze.json2Map(requestJsonBody);
        String beforeId = String.valueOf(map.get("beforeId"));
        String afterId = String.valueOf(map.get("afterId"));
        String rootId = String.valueOf(map.get("rootid"));

        MindMap mindMap = mindMapService.queryMindByMapid(rootId);

        if(!adminId.equals(mindMap.getUserid())){
            result.put("status",400);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        List<MindNode> list = jsonAnalyze.parseList(mindMap.getMapList());
        for(ListIterator<MindNode> it = list.listIterator(); it.hasNext(); ){
            MindNode mindNode = it.next();
            if(mindNode.getId().equals(beforeId)) { mindNode.setParentid(afterId); }
        }

        mindMap.setMapList(jsonAnalyze.list2Json(list));
        mindMap.setUpdateDate(new Date());
        mindMapService.save(mindMap);

        result.put("status",200);
        result.put("message","拖拽成功");
        return new ResultBean<>(result);

    }




    /**
     * @auther: Ragty
     * @describe: 保存颜色
     * @param: [nodeid, color, mapid]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2019/1/3
     */
    @GetMapping("/setColor")
    public ResultBean<Map<String,Object>> setColor(String mapid, String nodeid,
                                                   String color, HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        String adminId = adminService.getCurrentUser(request);

        MindMap mindMap = mindMapService.queryMindByMapid(mapid);

        if(!adminId.equals(mindMap.getUserid())){
            result.put("status",400);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        List<MindNode> list = jsonAnalyze.parseList(mindMap.getMapList());
        for(ListIterator<MindNode> it = list.listIterator(); it.hasNext(); ){
            MindNode mindNode = it.next();
            if(mindNode.getId().equals(nodeid)) { mindNode.setColor(color);}
        }

        mindMap.setMapList(jsonAnalyze.list2Json(list));
        mindMap.setUpdateDate(new Date());
        mindMapService.save(mindMap);

        result.put("status",200);
        result.put("message","success");
        return new ResultBean<>(result);

    }



}
