package com.hwj.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RobotController {

	
	/**
	 * @author Ragty
	 * @param  防爬虫声明文件
	 * @serialData 2018.9.11
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/robots.txt")
    public String robots(HttpServletRequest request) throws Exception{
        return "User-agent: *\nDisallow: /\n";
    }
	
}
