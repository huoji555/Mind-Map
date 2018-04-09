package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.stereotype.Component;

import com.hwj.entity.Functions;
import com.hwj.service.IFunctionsService;


@Component
public class TryCatchFunctionsService {

	@Autowired
	private IFunctionsService iFunctionsService;

	public List<Functions> getFunctions(String grandparentId, String limit) {
		List<Functions> list;
		try {
			list = iFunctionsService.getFunctions(grandparentId, limit);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	public boolean saveAll(Functions functions) {
		try {
			iFunctionsService.save(functions);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public Functions get(String propertyName, Object value) {
		Functions functions;
		try {
			functions = iFunctionsService.get(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return functions;
	}

	public boolean delete(Functions functions) {
		try {
			iFunctionsService.delete(functions);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public List<Functions> select(String propertyName, Object value) {
		List<Functions> list;
		try {
			list = iFunctionsService.select(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;

		}
		return list;
	}

	public boolean update(Functions functions) {

		try {
			iFunctionsService.update(functions);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	
	
	public List<Functions> getFunctionList(String propertyName,Object value){
		
		List<Functions> list;
		try {
			System.out.println("到这里了");
			list = iFunctionsService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	/*
	 * 生成一个随机数，要求数据库里不存在
	 */

	public String random() {
		String i;

		do {
			i = "" + (int) (Math.random() * 1000 + 100) + "";
			System.out.println(i + "catch");
		} while (!iFunctionsService.get("resourceID", i).equals(null));
		return i;
	}
	
	
	/**
	 * @author Ragty
	 * @param  根据id获取五大版块名称
	 * @serialData 2018.4.9
	 * @param grandparentName
	 * @return
	 */
	public String getInt(String grandparentName) {
		if (grandparentName.equals("1")) {
			return "教育";
		} else if (grandparentName.equals("2")) {
			return "笔记";
		} else if (grandparentName.equals("3")) {
			return "资源";

		} else if (grandparentName.equals("4")) {
			return "消息";
		} else if (grandparentName.equals("5")) {
			return "工作";
		} else {
			return "0";
		}
	}
	
	
	
}
