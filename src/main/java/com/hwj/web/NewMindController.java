package com.hwj.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;
import com.hwj.entityUtil.MindNodeTool;
import com.hwj.entityUtil.Node2;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchMindMapService;
import com.hwj.tools.TryCatchNewMindService;
import com.hwj.tools.TryCatchUserService;

@Controller
public class NewMindController {

	@Autowired
	private TryCatchNewMindService tryCatchNewMindService;
	@Autowired
	private TryCatchMindMapService tryCatchMindMapService;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap statusMap;
	
	
	
	/**
	 * @author Ragty
	 * @param  映射新的知识图谱界面
	 * @serialData 2018.6.8
	 * @return
	 */
	@RequestMapping("/test.do")
	public String test(){
		return "test";
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  新建知识图谱
	 * @serialData 2018.6.8
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("newNewMind.do")
	@ResponseBody
	public String newNewMind(@RequestParam String nodeName, HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		Map<String, Object> map3 = new HashMap<String, Object>();
		Map<String, Object> map4 = new HashMap<String, Object>();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String id = df.format(new Date());
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid.equals(null)||userid.equals("null")){
			return statusMap.a("2");
		}
		
		map.put("id", id);
		map.put("topic", nodeName);
		map2.put("author", "hizzgdev@163.com");
		map2.put("name", "jsMindremote");
		map2.put("version", "0.2");
		map3.put("data", map);
		map3.put("meta", map2);
		map3.put("format", "node_tree");
		

		String datas = jsonAnalyze.object2Json(map3).toString();
		datas = datas.replace("\"", "'");
		datas = datas.replace(" ", "");
		datas = datas.replace("'{", "{");
		datas = datas.replace("}'", "}");
		
		System.out.println(datas);
		System.out.println(map3);
		
		map4.put("datas", datas);
		map4.put("kcmc", id);
		map4.put("mindJson2", "success");
		map4.put("state", Integer.valueOf(1));
		
		String data = jsonAnalyze.map2Json(map4);
		
		String realName = tryCatchUserService.getOneLoginUser("nickName", userid).getRealName();
		
		//存节点数据
		MindNode mindNode = new MindNode();
		mindNode.setNodeid(id);
		mindNode.setNodename(nodeName);
		mindNode.setParentid("00100");

		List<MindNode> list = new ArrayList<MindNode>();
		list.add(mindNode);
		String activeData = jsonAnalyze.list2Json(list);
		
		//生成日期
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MMdd-HHmmss");
		String date = df1.format(new Date());
		
		//存数据库
		MindMap mindmap = new MindMap();
		mindmap.setNodeid(id);
		mindmap.setNodename(nodeName);
		mindmap.setData(data);
		mindmap.setUserid(userid);
		mindmap.setRealname(realName);
		mindmap.setMaplist(activeData);
		mindmap.setDate(date);

		if(tryCatchNewMindService.saveMindMap(mindmap)){
			return data;
		}
		return statusMap.a("3");
		
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  新建子节点(先在这留一个bug)
	 * @serialData 2018.6.10
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveMapNode.do")
	@ResponseBody
	public String saveMapNode(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws IOException{
		
		String nodeid = mindNodeTool.getNodeid();
		String nodename = mindNodeTool.getNodename();
		String parentid = mindNodeTool.getParentid();
		String rootid = mindNodeTool.getRootid();
				
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid.equals("null") || userid.equals(null)){
			return statusMap.a("2");
		}
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("nodeid", rootid,
				"userid", userid);
		String activeList = mindMap.getMaplist();
		String mindUser = mindMap.getUserid();
		
		
		//防止其他人修改自己的知识图谱
		if (!userid.equals(mindUser)){
			return statusMap.a("5");
		}
		
		//更新动态树
		List<MindNode> list = jsonAnalyze.parseList(activeList);
		
		MindNode mindNode = new MindNode();
		mindNode.setNodeid(nodeid);
		mindNode.setNodename(nodename);
		mindNode.setParentid(parentid);
		
		list.add(mindNode);
		
		
		//打开整个知识图谱
		String open = tryCatchMindMapService.openMind(list, rootid);

		mindMap.setMaplist(jsonAnalyze.list2Json(list));    //更新树
		mindMap.setData(open);                              //更新数据
		
		if(tryCatchNewMindService.updateMindMap(mindMap)){
			return open;
		}
		
		return statusMap.a("2");
	}
	
	
	
	/**
	 * @author Ragty
	 * @param 修改知识图谱节点信息
	 * @serialData 2018.6.10
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("updateMapNode.do")
	@ResponseBody
	public String updateMapNode(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws IOException{
		
		String nodeid = mindNodeTool.getNodeid();
		String nodename = mindNodeTool.getNodename();
		String rootid = mindNodeTool.getRootid();
		
		HttpSession session = request.getSession();
		String userid =String.valueOf(session.getAttribute("username"));
		
		if(userid.equals("null") || userid.equals(null)){
			return statusMap.a("2");
		}
		
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("nodeid", rootid,
				"userid", userid);
		String activeList = mindMap.getMaplist();
		String mindUser = mindMap.getUserid();
		
		
		//防止其他人修改自己的知识图谱
		if (!userid.equals(mindUser)){
			return statusMap.a("5");
		}
		
		//更新动态树
		List<MindNode> list = jsonAnalyze.parseList(activeList);

		for(int i= 0; i<list.size(); i++){
			MindNode mindNode = list.get(i);
			
			if( mindNode.getNodeid().equals(nodeid) ){
				mindNode.setNodename(nodename);
				list.set(i, mindNode);          //修改
			}
			
		}
		
		//更改MindMap名
		if(rootid.equals(nodeid)){
			mindMap.setNodename(nodename);
		}
		
		
		//打开知识图谱
		String open = tryCatchMindMapService.openMind(list, rootid);

		mindMap.setMaplist(jsonAnalyze.list2Json(list));    //更新树
		mindMap.setData(open);                              //更新数据
		
		if(tryCatchNewMindService.updateMindMap(mindMap)){
			return statusMap.a("1");
		}
		
		return statusMap.a("3");
		
	}
	
	
	
	
	
	
	
	
	
	
	
}
