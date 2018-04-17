package com.hwj.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.LoginUser;
import com.hwj.entity.MindNode;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchMindMapService;

@Controller
public class StudyController {
	
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap statusMap;
    @Autowired
    private TryCatchMindMapService tryCatchMindMapService;
	
	
    
    /**
     * @author Ragty
     * @param  映射学习首页面
     * @serialData 2018.4.17
     * @return
     */
	@RequestMapping("study.do")
	public String study(){
		return "study";
	}
	
	
	/**
	 * @author Ragty
	 * @param  根据多个数据 模糊查询MindNode
	 * @serialData 2018.4.16
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSearchMindByName.do")
	@ResponseBody
	public String getShareMindNodeByName(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map =jsonAnalyze.json2Map(requestJsonBody);
		String queryMessage =  String.valueOf(map.get("searchMessage"));
		Integer currentPage  = (Integer) map.get("currentPage");
		Integer pageSize = (Integer) map.get("pageSize");
		
		HttpSession session=request.getSession();
		String username=String.valueOf(session.getAttribute("username"));
		
		if( (username.equals("null"))||(username.equals(null)) ){
			return statusMap.a("2");
		}
		
		List<MindNode> list = tryCatchMindMapService.queryMindNode(queryMessage, currentPage, pageSize);
		
		if( list == null ){
			return statusMap.a("3");
		}
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		try {
			
			for(int i=0; i< list.size(); i++){
				MindNode mindNode = list.get(i);
				Map<String, String> map2 = new HashMap<String, String>();
				
				map2.put("nodename",mindNode.getNodename() );
	        	map2.put("userid",mindNode.getUserid() );
	        	map2.put("nodeid",mindNode.getNodeid() );
	        	map2.put("realName",mindNode.getUserid() );
				list2.add(map2);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return jsonAnalyze.list2Json(list2);
		
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取查询信息后的总页数
	 * @serialData 2018.4.17
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getSearchMindPage.do")
	@ResponseBody
	public Long getSearchMindPage(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String queryMessage = String.valueOf(map.get("searchMessage"));
		
		Integer pageSize = (Integer) map.get("pageSize");
		
		Long total = tryCatchMindMapService.queryMindPage(queryMessage);
		total=(total-1)/pageSize+1;
		
		if( total.equals("null")||total.equals(null) ){
			return null;
		}
		
		return total;
	}
	
}
