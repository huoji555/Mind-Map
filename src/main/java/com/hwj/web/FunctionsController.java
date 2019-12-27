package com.hwj.web;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.Functions;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFunctionsService;

@Controller
public class FunctionsController {

	@Autowired
	private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private TryCatchFunctionsService tryCatchFunctionsService;
	
	
	/**
	 * @author Ragty
	 * @param  映射后台模块管理界面
	 * @serialData 2018.4.9
	 * @return
	 */
	@RequestMapping("/module.do")
	public String  getMoudle(){
		return "module";
	}
	
	
	
	@RequestMapping("/introduce.do")
	public String introduce(){
		return "introduce";
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取后台功能树的方法(根据五大板块id)
	 * @serialData 2018.4.8
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getFunctions.do")
	@ResponseBody
	public String getFunctions(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String grandparentId = String.valueOf(map.get("grandparentID"));  //获取五大板块id
		
		List<Functions> list = new ArrayList<Functions>();
		
		list = tryCatchFunctionsService.getFunctionList("grandparentId", grandparentId);
		
		if(list == null){
			return statusMap.a("5");
		}
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
	    for(int i=0; i<list.size(); i++){
	    	Functions functions2 = list.get(i);
	    	Map<String, String> map2 = new HashMap<String, String>();
	    	map2.put("resourceID", functions2.getResourceId());
			map2.put("resourceName", functions2.getResourceName());
			map2.put("parentID", functions2.getParentId());
			map2.put("resourceGrade", functions2.getResourceGrade());
			map2.put("accessPath", functions2.getAccessPath());
			list2.add(map2);
	    }
		
	    return jsonAnalyze.list2Json(list2);
	}
	
	
	/**
	 * @author Ragty
	 * @param  根据菜单等级id获取相应等级下的所有菜单
	 * @serialData 2018.4.9
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getModuleList.do")
	@ResponseBody
	public String getModuleList(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String resourceGrade = String.valueOf(map.get("resourceGrade"));
		
		List<Functions> list = tryCatchFunctionsService.getFunctionList("resourceGrade", resourceGrade);
		
		if(list == null){
			return statusMap.a("2");
		}
		
		return null;
	}
	
	
	/**
	 * @author Ragty
	 * @param  后台管理系统增加二级菜单(增加模块)
	 * @serialData 2018.4.9
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/addModule.do")
	public String addModule(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String resourceName = String.valueOf(map.get("resourceName"));
		String grandparentId = String.valueOf(map.get("grandparentId"));
		String grandparentName = tryCatchFunctionsService.getInt(grandparentId);
		
		String resourceID = tryCatchFunctionsService.random();
		String resourceGrade = "2";
		
		Functions functions = new Functions();
		functions.setParentId("1");
		functions.setGrandparentId(grandparentId);
		functions.setResourceId(resourceID);
		functions.setResourceName(resourceName);
		functions.setResourceGrade(resourceGrade);
		functions.setGrandparentName(grandparentName);

	    if(tryCatchFunctionsService.saveAll(functions)){
	    	return statusMap.a("1");
	    }
		
	    return statusMap.a("2");
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  删除模块接口（删除时级联删除模块下的功能）
	 * @serialData 2018.4.9
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/deleteModule.do")
	@ResponseBody
	public String deleteModule(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map =jsonAnalyze.json2Map(requestJsonBody);
		String resourceName = String.valueOf(map.get("resourceName"));
		
		Functions functions = tryCatchFunctionsService.get("resourceName", resourceName);
		String resourceGrade = functions.getResourceGrade();
		
		//级联删除下面的模块
		if(resourceGrade == "2"){
			String resourceId = functions.getResourceId();
			List<Functions> list = tryCatchFunctionsService.getFunctionList("parentId", resourceId);
			
			for(int i=0; i<list.size(); i++){
				Functions functions2 = list.get(i);
			    tryCatchFunctionsService.delete(functions2);	
			}
			
		}
		
		if(tryCatchFunctionsService.delete(functions)){
			return statusMap.a("1");
		} 
		
		return statusMap.a("2");
	}
	
	
	/**
	 * @author Ragty
	 * @param  修改模块位置
	 * @serialData 2018.4.9
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateModule.do")
	@ResponseBody
	public String updateModule(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String grandparentId = String.valueOf(map.get("grandparentId"));
		String resourceName = String.valueOf(map.get("resourceName"));
		String grandparentName = tryCatchFunctionsService.getInt(grandparentId);
		
		Functions functions = tryCatchFunctionsService.get("resourceName", resourceName);
		functions.setGrandparentId(grandparentId);
		functions.setGrandparentName(grandparentName);
		functions.setResourceName(resourceName);
		
		if(tryCatchFunctionsService.update(functions)){
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
	}
	
	
	
}
