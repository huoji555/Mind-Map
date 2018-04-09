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
		System.out.println(list+"&*&*&*&&*&*");
		
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
	
	
	
	
	
	
}
