package com.hwj.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	/*
	 * 
	 */
}
