package com.hwj.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.MindNode;
import com.hwj.entity.UploadFile;
import com.hwj.entityUtil.MindMapUtil;
import com.hwj.entityUtil.MindNode2Util;
import com.hwj.entityUtil.MindNodeTool;
import com.hwj.entityUtil.MindNodeUtil;
import com.hwj.entityUtil.Node2;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFileStreamService;
import com.hwj.tools.TryCatchMindMapService;
import com.hwj.tools.TryCatchUploadFileService;

@Controller
public class MindMapController {
      
	@Autowired
    private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private TryCatchFileStreamService tryCatchFileStreamService;
	@Autowired
	private TryCatchUploadFileService tryCatchUploadFileService;
	@Autowired
	private TryCatchMindMapService tryCatchMindMapService;
	
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.3.22
	 * @param 映射思维导图学生端界面
	 * @return
	 */
	@RequestMapping("/mindmap2.do")
	public String mindmap2(){
		return "mindmap2";
	}
	
	
	/**
	 * @author Ragty
	 * @param  映射各个节点的笔记页面
	 * @serialData 2018.3.22
	 * @return
	 */
	@RequestMapping("/biji.do")
	public String biji(){
		return "biji";
	}
	
	
	/**
	 * @author Ragty
	 * @param  映射教师端思维导图界面
	 * @serialData 2018.3.22
	 * @return
	 */
	@RequestMapping("/mindMapTeacher")
	public String mindMapTeacher(){
		return "mindMapTeacher";
	}
	
	
	/**
	 * @author Ragty
	 * @param  用MindMap新建知识图谱
	 * @serialData 2018.3.22
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/newMindMap.do")
	@ResponseBody
	public String newMindMap(HttpServletRequest request) throws IOException{
		
		MindMapUtil mindMapUtil = new MindMapUtil();
		Map<String, Object> map4 = new HashMap<String, Object>();
		String j = String.valueOf(System.currentTimeMillis());
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "jsMind remote");
		map.put("author", "hizzgdev@163.com");
		map.put("version", "0.2");

		Map<String, String> map3 = new HashMap<String, String>();
		map3.put("id", String.valueOf(j));
		map3.put("topic", "新建知识图谱");
		
		map4.put("meta", map);
		map4.put("format", "node_tree");
		map4.put("data", map3);
		
		String mapid = String.valueOf(j);
		String datas = this.jsonAnalyze.object2Json(map4);
		datas = datas.replace("\"", "'");
		
		mindMapUtil.setDatas(datas);
		mindMapUtil.setMapid(mapid);
		
		return jsonAnalyze.object2Json(mindMapUtil);
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  用户获取自己建好的思维导图(以分页列表的形式出现)
	 * @serialData 2018.3.22
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getMindNode.do")
	@ResponseBody
	public String getMindNode(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String parentid=String.valueOf(map.get("parentid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		Integer currentPage=(Integer) map.get("currentPage");
		Integer pageSize=12;
		
		System.out.println("要看看当前页数"+currentPage);
		
		if (userid.equals("null")||userid.equals(null)){
			return statusMap.a("1");
		}
		
		List<MindNode> list = new ArrayList<MindNode>();
		list = tryCatchMindMapService.
				getMindNodeByPage(currentPage, pageSize, "userid", userid, "parentid", parentid);
		
		if (list == null){
			return statusMap.a("2");
		}
		
		return jsonAnalyze.list2Json(list);
	}
	
	
	/**
	 * @author Ragty
	 * @param  用户新建思维导图(不写名字都为知识图谱)
	 * @serialData 2018.3.22
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getNodeDatexinjian.do")
	@ResponseBody
	public String getNodeDatexinjian(HttpServletRequest request) throws IOException{
		
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
		map.put("topic", "新建思维导图");
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
		
		System.out.println("@@@@@包装后的数据" + datas);// 这部分主要返回给前台，显示思维导图
		
		map4.put("datas", datas);
		map4.put("kcmc", id);
		map4.put("mindJson2", "success");
		map4.put("state", Integer.valueOf(1));
		
		MindNode mindNode = new MindNode(); // 这部分往数据库中存
		mindNode.setNodeid(id);
		mindNode.setParentid("00100");
		mindNode.setType(id);
		mindNode.setUserid(userid);
		mindNode.setNodename("知识图谱");
		
		if (tryCatchMindMapService.savaMindNodeObject(mindNode)){
			return jsonAnalyze.map2Json(map4);
		}
		
		return statusMap.a("3");
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  打开一个思维导图(把数据库里的元素有机重组)
	 * @serialData 2018.3.22
	 * @param nodeid
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getNodeData.do")
	@ResponseBody
	public String getNodeData(@RequestParam String nodeid,
			HttpServletRequest request) throws IOException{
		
        String type = tryCatchMindMapService.
        		      getMindNode("nodeid", nodeid).get(0).getType();		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if(userid.equals("null") || userid.equals(null)){
			return statusMap.a("2");
		}
		
		List<MindNode> list = tryCatchMindMapService.
				       getMindNode("userid", userid, "type", type);

		if (list.equals(null) || list.size()<=0){
			return null;
		}
		
		//包装数据（能在jsmind中显示的数据）
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> meta = new HashMap<String, Object>();
		meta.put("name", "jsMind remote");
		meta.put("author", "hizzgdev@163.com");
		meta.put("version", "0.2");
		
		data.put("meta", meta);
		data.put("format", "node_tree");
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		for(int i=0; i<list.size(); i++){
			Map<String, String> map = new HashMap<String, String>();
			MindNode mindNode =list.get(i);
			map.put("id", mindNode.getNodeid());
			map.put("topic", mindNode.getNodename());
			map.put("parentid", mindNode.getParentid());
			list2.add(map);
		}
		
		List dataList = list2;
		HashMap nodeList = new HashMap();
		Node2 root = null;
		MindNode2Util mindNode2Util = new MindNode2Util();
		
		Set entrySet = nodeList.entrySet();
		for(Iterator it = entrySet.iterator(); it.hasNext();){
			Node2 node = (Node2) ((Map.Entry) it.next()).getValue();
			
			if ((node.parentid == null) || (node.parentid.equals("00100"))) {
				System.out.println("node的值@@@@@@@@@@@@@@@"+node);
				root = node;
			} else {
				try {
					((Node2) nodeList.get(node.parentid)).addChild(node); // 重点，在主节点后面加子节点
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
		}
		
		mindNode2Util.setState("1");
		
		data.put("data", root.toString());
		
		String datas = this.jsonAnalyze.object2Json(data).toString();
		
		datas = datas.replace("\"", "'");
		datas = datas.replace(" ", "");
		datas = datas.replace("'{", "{");
		datas = datas.replace("}'", "}");
		mindNode2Util.setDatas(datas);
		mindNode2Util.setKcmc(type);
		mindNode2Util.setMindJson2("success");
		
		return this.jsonAnalyze.object2Json(mindNode2Util);
        
	}
	
	
	/**
	 * @author Ragty
	 * @param  打开子节点后的思维导图
	 * @serialData 2018.3.22
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMindNode2.do")
	@ResponseBody
	public String getMindNode2(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws Exception {
		String nodeid = mindNodeTool.getNodeid();
		String parentid = ((MindNode) this.tryCatchMindMapService.getMindNode(
				"nodeid", nodeid).get(0)).getParentid();
		String type = ((MindNode) this.tryCatchMindMapService.getMindNode(
				"nodeid", nodeid).get(0)).getType();
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}

		List<MindNode> list = new ArrayList<MindNode>();
		list = this.tryCatchMindMapService.getMindNode("userid", userid,
				"type", type);

		System.out.println(list + "##########打开子节点");
		MindNode2Util mindNode2Util = new MindNode2Util();
		if ((list == null) || (list.size() <= 0)) {
			return null;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> meta = new HashMap<String, Object>();
		meta.put("name", "jsMind remote");
		meta.put("author", "hizzgdev@163.com");
		meta.put("version", "0.2");
		data.put("meta", meta);
		data.put("format", "node_tree");

		List<MindNode> list2 = new ArrayList<MindNode>();
		list2 = this.tryCatchMindMapService.getMindNode("userid", userid,
				"type", type);

		List<Map<String, String>> list3 = new ArrayList();

		// 可以显示取出的子节点及它的子数据（分层取数据）
		list3 = this.tryCatchMindMapService.getzijiedian(nodeid, userid);

		System.out.println(list3 + "list");
		List dataList = list3;
		System.out.println(dataList + "  zheishi  jiajijfi");

		HashMap nodeList = new HashMap();

		System.out.println(nodeList + "  zheishi  jiajijfi");
		Node2 root = null;
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node2 node = new Node2();
			node.id = ((String) dataRecord.get("id"));
			node.topic = ((String) dataRecord.get("topic"));
			node.parentid = ((String) dataRecord.get("parentid"));
			nodeList.put(node.id, node);
		}
		System.out.println(root + "  root  jiajijfi");

		Set entrySet = nodeList.entrySet();
		System.out.println(entrySet + "  entrySet  jiajijfi");
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node2 node = (Node2) ((Map.Entry) it.next()).getValue();
			if ((node.parentid == null) || (node.parentid.equals(parentid))) {
				root = node;
			} else {
				
				try {
					((Node2) nodeList.get(node.parentid)).addChild(node);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
		System.out.println("sdfsdf :" + root.toString());
		System.out.println("sdsfsdfds:" + root);
		mindNode2Util.setState("1");

		data.put("data", root.toString());

		String datas = this.jsonAnalyze.object2Json(data).toString();

		System.out.println("datatatat:" + datas);
		datas = datas.replace("\"", "'");
		datas = datas.replace(" ", "");
		datas = datas.replace("'{", "{");
		datas = datas.replace("}'", "}");
		mindNode2Util.setDatas(datas);
		mindNode2Util.setKcmc(type);
		mindNode2Util.setMindJson2("success");
		return this.jsonAnalyze.object2Json(mindNode2Util);
	}
	
	
	/**
	 * @author Ragty
	 * @param  根据一个节点获取整个思维导图
	 * @serialData 2018.3.22
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMindNode3.do")
	@ResponseBody
	public String getMindNode3(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws Exception {
		String nodeid = mindNodeTool.getNodeid();
		String type = ((MindNode) this.tryCatchMindMapService.getMindNode(
				"nodeid", nodeid).get(0)).getType();
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}

		List<MindNode> list = new ArrayList<MindNode>();
		list = this.tryCatchMindMapService.getMindNode("userid", userid,
				"type", type);

		MindNode2Util mindNode2Util = new MindNode2Util();
		if ((list == null) || (list.size() <= 0)) {
			return null;
		}
		Map<String, Object> data = new HashMap<String, Object>();
		Map<String, Object> meta = new HashMap<String, Object>();
		meta.put("name", "jsMind remote");
		meta.put("author", "hizzgdev@163.com");
		meta.put("version", "0.2");
		data.put("meta", meta);
		data.put("format", "node_tree");

		List<MindNode> list2 = new ArrayList<MindNode>();
		list2 = this.tryCatchMindMapService.getMindNode("userid", userid,
				"type", type);

		System.out.println("让我看看是何方妖孽"+list2);
		
		
		List<Map<String, String>> list3 = new ArrayList<Map<String, String>>();
		for (int i = 0; i < list2.size(); i++) {
			Map<String, String> map2 = new HashMap<String, String>();
			MindNode mindNode = (MindNode) list2.get(i);
			map2.put("id", mindNode.getNodeid());
			map2.put("topic", mindNode.getNodename());
			map2.put("parentid", mindNode.getParentid());

			list3.add(map2);
		}
		System.out.println(list3 + "list");
		List dataList = list3;
		System.out.println(dataList + "  zheishi  jiajijfi");

		HashMap nodeList = new HashMap();

		System.out.println(nodeList + "  zheishi  jiajijfi");
		Node2 root = null;
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node2 node = new Node2();
			node.id = ((String) dataRecord.get("id"));
			node.topic = ((String) dataRecord.get("topic"));
			node.parentid = ((String) dataRecord.get("parentid"));
			node.direction = (String) (dataRecord.get("parentid"));
			nodeList.put(node.id, node);
		}
		System.out.println(root + "  root  jiajijfi");

		Set entrySet = nodeList.entrySet();
		System.out.println(entrySet + "  entrySet  jiajijfi");
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node2 node = (Node2) ((Map.Entry) it.next()).getValue();
			if ((node.parentid == null) || (node.parentid.equals("00100"))) {
				root = node;
			} else {
				
				try {
					((Node2) nodeList.get(node.parentid)).addChild(node);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
			}
		}
		System.out.println("sdfsdf :" + root.toString());
		System.out.println("sdsfsdfds:" + root);
		mindNode2Util.setState("1");

		data.put("data", root.toString());

		String datas = this.jsonAnalyze.object2Json(data).toString();

		System.out.println("datatatat:" + datas);
		datas = datas.replace("\"", "'");
		datas = datas.replace(" ", "");
		datas = datas.replace("'{", "{");
		datas = datas.replace("}'", "}");
		mindNode2Util.setDatas(datas);
		mindNode2Util.setKcmc(type);
		mindNode2Util.setMindJson2("success");
		return this.jsonAnalyze.object2Json(mindNode2Util);
	}
	
	
	/**
	 * @author Ragty
	 * @param  新建子节点接口
	 * @serialData 2018.3.22
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveMindNode2.do")
	@ResponseBody
	public String saveMindNode2(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws IOException{
		
		String nodeid = mindNodeTool.getNodeid();
		String nodename = mindNodeTool.getNodename();
		String parentid = mindNodeTool.getParentid();
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid.equals("null") || userid.equals(null)){
			return statusMap.a("2");
		}
		
		String type =tryCatchMindMapService.
				getMindNode("nodeid", parentid).get(0).getType();
		
		MindNode mindNode = new MindNode();
		mindNode.setNodeid(nodeid);
		mindNode.setNodename(nodename);
		mindNode.setParentid(parentid);
		mindNode.setUserid(userid);
		mindNode.setType(type);
		
		boolean a;
		
		if(tryCatchMindMapService.getMindNode("nodeid", nodeid)!=null){
			a = this.tryCatchMindMapService.updateMindNodeObject(mindNode);
			System.out.println("修改");
		} else {
			a = this.tryCatchMindMapService.savaMindNodeObject(mindNode);
			System.out.println("保存");
		}
		
		if(a){
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
	}
	
	
	/**
	 * @author Ragty
	 * @param  修改节点信息接口
	 * @serialData 2018.3.23
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/updateMindNode.do")
	@ResponseBody
	public String updateMindNode(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws IOException{
		
		String nodeid = mindNodeTool.getNodeid();
		String nodename = mindNodeTool.getNodename();
		
		HttpSession session = request.getSession();
		String userid =String.valueOf(session.getAttribute("username"));
		
		if(userid.equals("null") || userid.equals(null)){
			return statusMap.a("2");
		}
		
		MindNode mindNode = tryCatchMindMapService.
				getMindNodeObject("userid", "nodeid", userid, nodeid);
		mindNode.setNodename(nodename);
		
		if(tryCatchMindMapService.updateMindNodeObject(mindNode)){
			return statusMap.a("1");
		}
		return statusMap.a("3");
	}
	
	
	@RequestMapping("/deleteNodeByBatch.do")
	@ResponseBody
	public String deleteNodeByBatch(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String nodeid=String.valueOf(map.get("nodeid"));
		
		HttpSession session =request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid.equals("null") || userid.equals(null)){
			return statusMap.a("2");
		}
		
		List<Map<String, String>> list = new ArrayList<Map<String,String>>();
		list = tryCatchMindMapService.getzijiedian(nodeid, userid);
		
		List dataList = list;
		
		
		//在循环体里执行删除
		for(Iterator it = dataList.iterator(); it.hasNext();){
			Map dataRecord = (Map) it.next();
			
			String id = String.valueOf(dataRecord.get("id"));
			
			MindNode mindNode = tryCatchMindMapService.
					getMindNodeObject("nodeid", "userid", id, userid);
			
			List<UploadFile> listUploadFiles = null;
			try {
				listUploadFiles = tryCatchUploadFileService.getUploadeFile("userid", userid, "zsdid", id);
				if(listUploadFiles.size() <= 0){
					return statusMap.a("3");
				}		
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}
		
		
			
			
			
		return null;
	}
	
	
	
}
