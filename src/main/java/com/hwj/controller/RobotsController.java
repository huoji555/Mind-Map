package com.hwj.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class RobotsController {

    /**
     * @author Ragty
     * @param  用来声明爬虫爬取规则(声明是声明，爬不爬是你的事)
     * @serialData 2018.8.28
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/robots.txt")
    public String robots(HttpServletRequest request, HttpServletResponse response) throws Exception{
        return "User-agent: *\nDisallow: /\n";
    }


}
