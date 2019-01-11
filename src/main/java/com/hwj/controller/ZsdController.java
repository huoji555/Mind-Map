package com.hwj.controller;

import com.google.common.collect.Maps;
import com.hwj.entity.Zsd;
import com.hwj.service.ZsdService;
import com.hwj.util.JsonAnalyze;
import com.hwj.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
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
        HttpSession session = request.getSession();
        String adminId = String.valueOf(session.getAttribute("admin"));

        String nodeid = String.valueOf(data.get("nodeid"));
        String zsdms = String.valueOf(data.get("zsdms"));
        String mapid = String.valueOf(data.get("mapid"));

        if (adminId.equals("null") || adminId == "null") {
            result.put("status",400);
            result.put("message","登录超时");
            return new ResultBean<>(result);
        }

        Zsd zsd = zsdService.findZsd(nodeid);

        try {
            if ( !adminId.equals(zsd.getUserid()) ) {
                result.put("status",400);
                result.put("message","不是您的图");
                return new ResultBean<>(result);
            }
        } catch (Exception e) {
            //...
        }

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

        ValueOperations ops = redisTemplate.opsForValue();
        ops.set("zsd"+nodeid,zsdms);
        redisTemplate.expire("zsd"+nodeid,7, TimeUnit.DAYS);

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
    public ResultBean<Map<String,Object>> getZsd(String nodeid) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        ValueOperations ops = redisTemplate.opsForValue();

        String redisCache = (String) ops.get("zsd"+nodeid);

        if ( redisCache != null ) {
            redisTemplate.expire("zsd"+nodeid,7,TimeUnit.DAYS);
            result.put("status",200);
            result.put("data",redisCache);
        } else {
            Zsd zsd = zsdService.findZsd(nodeid);

            if (zsd == null) {
                result.put("status",400);
                result.put("data","请添加知识点描述");
                return new ResultBean<>(result);
            }

            ops.set("zsd"+nodeid,zsd.getZsdms());
            redisTemplate.expire("zsd"+nodeid,7,TimeUnit.DAYS);
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
    public ResultBean delZsd(String nodeid) {

        ValueOperations ops = redisTemplate.opsForValue();
        if( ops.get("zsd"+nodeid) != null ){ redisTemplate.delete("zsd"+nodeid); }
        zsdService.deleteByNodeid(nodeid);

        return new ResultBean<>();

    }






}
