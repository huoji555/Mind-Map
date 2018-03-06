package com.hwj.web;

import static org.mockito.Matchers.startsWith;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.RoleName;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchRoleNameService;

@Controller
public class RoleNameController {

	@Autowired
	private TryCatchRoleNameService tryCatchRoleNameService;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	
	
	/**
	 * @author Ragty
	 * @param 用户管理页面映射
	 * @serialData 2018.2.4
	 * @return
	 */
	@RequestMapping(value="rolename.do")
	public String goRoleName() {
		return "rolename";
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取所有权限
	 * @serialData 2018.2.5
	 * @throws IOException
	 */
	@RequestMapping(value = "/getRoleNameList.do")
	@ResponseBody
	public String getRoleNameList(HttpServletRequest request ) throws IOException {
		
		List<RoleName> list;
		list=tryCatchRoleNameService.getAll();
		if(list==null) {
			return statusMap.a("2");
		}
		return jsonAnalyze.list2Json(list);
	}
	
	
	/**
	 * @author Ragty
	 * @param 增加权限
	 * @serialData 2018.2.5
	 * @throws IOException
	 */
	@RequestMapping(value = "/addRoleName.do")
	@ResponseBody
	public String addRoleName(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException {
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		String roleName=String.valueOf(map.get("roleName"));
		String remark=String.valueOf("remark");
		
		RoleName roleName2=new RoleName();
		roleName2.setRoleName(roleName);
		roleName2.setRemark(remark);
		
		if(tryCatchRoleNameService.addRoleName(roleName2)) {
			return statusMap.a("1");
		}
		  return statusMap.a("2");
		
	}
	
	
	/**
	 * @author Ragty
	 * @param 删除权限
	 * @serialData 2018.2.5
	 * @throws IOException
	 */
	@RequestMapping(value="/deleteRoleName.do")
	@ResponseBody
	public String deleteRoleName(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException {
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		Integer id=Integer.valueOf((String) map.get("id"));
		
		RoleName roleName=tryCatchRoleNameService.getRoleName(id);
		
		if(tryCatchRoleNameService.deleteRoleName(roleName)) {
			return statusMap.a("1");
		}
		return statusMap.a("2");
		
	}
	
	
	/**
	 * @author Ragty
	 * @param 根据id获取一个权限对象
	 * @serialData 2018.2.5
	 * @throws IOException
	 */
	@RequestMapping(value="/getRoleName.do")
	@ResponseBody
	public String getRoleName(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException {
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		Integer id=Integer.valueOf((String) map.get("id"));
		
		RoleName roleName=tryCatchRoleNameService.getRoleName(id);
		
		if(roleName==null) {
			return statusMap.a("2");
		}
		
		return jsonAnalyze.object2Json(roleName);

	}
	
	
	/**
	 * @author Ragty
	 * @param 修改权限
	 * @serialData 2018.2.5
	 * @throws IOException
	 */
	@RequestMapping(value="/updateRoleName.do")
	@ResponseBody
	public String updateRoleName(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException {
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		Integer id=Integer.valueOf((String) map.get("id"));
		String roleName=String.valueOf(map.get("roleName"));
		String remark=String.valueOf(map.get("remark"));
		
		RoleName roleName2=new RoleName();
		roleName2.setId(id);
		roleName2.setRoleName(roleName);
		roleName2.setRemark(remark);
	 
		if(tryCatchRoleNameService.updateRoleName(roleName2)) {
			return statusMap.a("1");
		}
		
		return statusMap.a("2");

	}
	
	
	/**
	 * @author Ragty
	 * @param 查询符合条件的所有权限
	 * @serialData 2018.2.5
	 * @throws IOException
	 */
	@RequestMapping(value="selectRoleNameList.do")
	@ResponseBody
	public String selectRoleNameList(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException {
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		String remark=String.valueOf(map.get("remark"));
		
		List<RoleName> list=tryCatchRoleNameService.selectRoleNameList("remark",remark);
		
		if(list==null) {
			return statusMap.a("2");
		}
		
		return jsonAnalyze.list2Json(list);
		
	}
	
	
}
