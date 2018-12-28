package com.hwj.controller;

import com.google.common.collect.Maps;
import com.hwj.entity.MindMap;
import com.hwj.entity.ShareMap;
import com.hwj.service.MindMapService;
import com.hwj.service.ShareMapService;
import com.hwj.util.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;


@RestController
@RequestMapping("/shareMap")
public class ShareMapController {

    @Autowired
    private ShareMapService shareMapService;
    @Autowired
    private MindMapService mindMapService;


    /*
     * @auther: Ragty
     * @describe: 获取分享的图谱(列表)
     * @param: [page, size]
     * @return: com.hwj.util.ResultBean<org.springframework.data.domain.Page<com.hwj.entity.ShareMap>>
     * @date: 2018/12/28
     */
    @GetMapping("getShareMap")
    public ResultBean<Page<ShareMap>> getShareMap(@RequestParam String page, @RequestParam String size) {

        Sort sort = new Sort(Sort.Direction.DESC,"update_date");
        Pageable pageable = new PageRequest(Integer.parseInt(page), Integer.parseInt(size), sort);

        Page<ShareMap> list = shareMapService.getShareMapList("", "", null, null, pageable);
        return new ResultBean<>(list);

    }



    /**
     * @auther: Ragty
     * @describe: 分享图谱
     * @param: [mapid, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/12/28
     */
    @PostMapping("share")
    public ResultBean<Map<String,Object>> shareMap(@RequestParam String mapid, HttpServletRequest request){

        Map<String,Object> result = Maps.newHashMap();
        HttpSession session = request.getSession();
        String userid = String.valueOf(session.getAttribute("admin"));
        MindMap mindMap = mindMapService.queryMindByMapid(mapid);

        if ( !userid.equals(mindMap.getUserid()) ) {
            result.put("status",400);
            result.put("message","不是您的图谱");
            return new ResultBean<>(result);
        }

        if ( shareMapService.existsShareMap(mapid) ) {
            result.put("status",400);
            result.put("message","请勿重复分享");
            return new ResultBean<>(result);
        }

        ShareMap shareMap = new ShareMap();
        shareMap.setMapid(mapid);
        shareMap.setMapname(mindMap.getMapname());
        shareMap.setShareId(mindMap.getUserid());
        shareMap.setShareDate(new Date());
        shareMap.setUpdateDate(new Date());

        shareMapService.save(shareMap);

        result.put("status",200);
        result.put("message","分享成功");
        return new ResultBean<>(result);
    }



    /**
     * @auther: Ragty
     * @describe: 删除分享图谱
     * @param: [mapid, mindUser, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/12/28
     */
    @GetMapping("delete")
    public ResultBean<Map<String,Object>> deleteShareMap(@RequestParam String mapid, @RequestParam String mindUser,
                                                         HttpServletRequest request) {

        Map<String,Object> result = Maps.newHashMap();
        HttpSession session = request.getSession();
        String userid = String.valueOf(session.getAttribute("admin"));

        if ( !userid.equals(mindUser) ) {
            result.put("status",400);
            result.put("message","不是您的图谱");
            return new ResultBean<>(result);
        }

        shareMapService.delete(mapid);
        result.put("status",200);
        result.put("message","delete success");
        return new ResultBean<>(result);

    }



}
