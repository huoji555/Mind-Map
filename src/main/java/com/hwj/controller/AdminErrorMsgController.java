package com.hwj.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hwj.entity.AdminErrorMsg;
import com.hwj.service.AdminErrorMsgService;
import com.hwj.util.ResultBean;

@RestController
@RequestMapping("/adminErrorMsg")
public class AdminErrorMsgController {
	
	@Autowired
	private AdminErrorMsgService adminErrorMsgService;
	
	
	
	/**
	 * @author Ragty
	 * @describe 分页获取异常 信息方法
	 * @date 2018.10.15
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("getErrorLog")
	public ResultBean<Page<AdminErrorMsg>> getErrorLog (@RequestParam Integer page, @RequestParam Integer size) {
		Page<AdminErrorMsg> lists = adminErrorMsgService.pageException(page, size);
		return new ResultBean<Page<AdminErrorMsg>>(lists);
	}
	
	
	

}
