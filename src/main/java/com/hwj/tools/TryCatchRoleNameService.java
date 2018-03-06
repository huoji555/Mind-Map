package com.hwj.tools;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.Functions;
import com.hwj.entity.RoleName;
import com.hwj.service.IFunctionsService;
import com.hwj.service.IRoleNameService;


@Component
public class TryCatchRoleNameService {

	@Autowired
	private IRoleNameService iRoleNameService;
	@Autowired
	private IFunctionsService iFunctionsService;

	/**
	 * @author Ragty
	 * @param 增加权限名字
	 * @serialData 2018.2.4
	 * @return  boolean值
	 */
	public boolean addRoleName(RoleName roleName) {
		try {
			iRoleNameService.save(roleName);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 获取所有权限名字
	 * @serialData 2018.2.4
	 * @return 权限list
	 */
	public List<RoleName> getAll() {
		List<RoleName> list;
		try {
			list = iRoleNameService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	
	/**
	 * @author Ragty
	 * @param 删除权限
	 * @serialData 2018.2.4
	 * @return  boolean值
	 */
	public boolean deleteRoleName(RoleName roleName) {
		try {
			iRoleNameService.delete(roleName);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 修改权限
	 * @serialData 2018.2.4
	 * @return boolean值
	 */
	public boolean updateRoleName(RoleName roleName) {
		try {
			iRoleNameService.update(roleName);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	
	/**
	 * @author Ragty
	 * @param 根据id值获取整个权限名字
	 * @serialData 2018.2.4
	 * @return RoleName对象
	 */
	public RoleName getRoleName(Serializable id) {
		RoleName roleName = new RoleName();
		try {
			roleName = iRoleNameService.get(id);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return roleName;
	}

	
	/**
	 * @author Ragty
	 * @param 根据一条信息查询并获取符合条件的所有权限
	 * @serialData 2018.2.4
	 * @return  list
	 */
	public List<RoleName> selectRoleNameList(String propertyName, Object value) {
		List<RoleName> list;
		try {
			list = iRoleNameService.getAll(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}

	
	/**
	 * @author Ragty
	 * @param 获取所有赋权的模块信息
	 * @param propertyName
	 * @param value
	 * @serialData 2018.2.4
	 * @return list
	 */
	public List<Functions> getModuleList(String propertyName, Object value) {
		List<Functions> list;
		try {
			list = iFunctionsService.getAll(propertyName, value);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;

	}

	
	/**
	 * @author Ragty
	 * @param 根据一条信息查询符合条件的模块
	 * @param resourceName
	 * @param value
	 * @return functions对象
	 */
	public Functions getFunctions2(String resourceName, Object value) {
		Functions functions = new Functions();
		try {
			functions = iFunctionsService.get(resourceName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return functions;
	}

	

}
