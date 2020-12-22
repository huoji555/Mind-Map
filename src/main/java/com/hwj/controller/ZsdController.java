package com.hwj.controller;

import com.google.common.collect.Maps;
import com.hwj.entity.Zsd;
import com.hwj.service.AdminService;
import com.hwj.service.MindMapService;
import com.hwj.service.ZsdService;
import com.hwj.util.JsonAnalyze;
import com.hwj.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/zsd")
public class ZsdController {

    @Autowired
    private ZsdService zsdService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private AdminService adminService;
    @Autowired
    private MindMapService mindMapService;
    @Autowired
    private JsonAnalyze jsonAnalyze;


    /*
     * @auther: Ragty
     * @describe: 保存或者修改知识点
     * @param: [nodeid, zsdms, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2019/1/7
     */
    @PostMapping("save")
    public ResultBean<Map<String,Object>> saveZsd(@RequestBody String requestJsonData,
                                                  HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        Map<String,Object> data = jsonAnalyze.json2Map(requestJsonData);
        String adminId = adminService.getCurrentUser(request);

        String nodeid = String.valueOf(data.get("nodeid"));
        String zsdms = String.valueOf(data.get("zsdms"));
        String mapid = String.valueOf(data.get("mapid"));

        if (adminId.equals("null") || adminId == "null") {
            result.put("status",201);
            result.put("message","登录超时");
            return new ResultBean<>(result);
        }

        String userid = mindMapService.queryMapUser(mapid);
        if ( !adminId.equals(userid) ) {
            result.put("status",400);
            result.put("message","不是您的图");
            return new ResultBean<>(result);
        }

        Zsd zsd = zsdService.findZsd(nodeid);
        if (zsd == null) {
            Zsd zsd1 = new Zsd();
            zsd1.setNodeid(nodeid);
            zsd1.setUserid(adminId);
            zsd1.setMapid(mapid);
            zsd1.setZsdms(zsdms);
            zsdService.save(zsd1);
        } else {
            zsdService.update(zsdms,nodeid);
        }

        //这里目前不加消息队列
        HashOperations hos = redisTemplate.opsForHash();
        hos.put("zsd:"+mapid,nodeid,zsdms);
        redisTemplate.expire("zsd:"+mapid,7, TimeUnit.DAYS);

        result.put("status",200);
        result.put("message","保存成功");
        return new ResultBean<>(result);

    }




    /**
     * @auther: Ragty
     * @describe: 获取知识点
     * @param: [nodeid]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2019/1/8
     */
    @GetMapping("getZsd")
    public ResultBean<Map<String,Object>> getZsd(String nodeid,String mapid) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        HashOperations hos = redisTemplate.opsForHash();

        String redisCache = (String) hos.get("zsd:"+mapid,nodeid);

        if ( redisCache != null ) {
            redisTemplate.expire("zsd:"+mapid,7,TimeUnit.DAYS);
            result.put("status",200);
            result.put("data",redisCache);
        } else {
            Zsd zsd = zsdService.findZsd(nodeid);

            if (zsd == null) {
                result.put("status",400);
                result.put("data","请添加知识点描述");
                return new ResultBean<>(result);
            }

            hos.put("zsd:"+mapid,nodeid,zsd.getZsdms());
            redisTemplate.expire("zsd:"+mapid,7, TimeUnit.DAYS);

            result.put("status",200);
            result.put("data",zsd.getZsdms());
        }

        return new ResultBean<>(result);
    }




    /*
     * @auther: Ragty
     * @describe: 清空知识点内容
     * @param: [nodeid]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2019/1/10
     */
    @GetMapping("delZsd")
    public ResultBean delZsd(String nodeid,String mapid,
                             HttpServletRequest request) {

        String adminId = adminService.getCurrentUser(request);
        String userid = mindMapService.queryMapUser(mapid);

        if( !adminId.equals(userid) ) {
            return new ResultBean();
        }

        HashOperations hos = redisTemplate.opsForHash();
        if (hos.hasKey("zsd:"+mapid,nodeid)) {hos.delete("zsd:"+mapid,nodeid);}
        zsdService.deleteByNodeid(nodeid);

        return new ResultBean<>();

    }






}
