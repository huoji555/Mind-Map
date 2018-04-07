package com.hwj.web;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hwj.entity.FileCollection;
import com.hwj.entity.FileShare;
import com.hwj.entity.FileStream;
import com.hwj.entity.LoginUser;
import com.hwj.entity.MindNode;
import com.hwj.entity.Share;
import com.hwj.entity.UploadFile;
import com.hwj.entity.Zsd;
import com.hwj.entityUtil.BeSaveFileUitl;
import com.hwj.entityUtil.MindMapUtil;
import com.hwj.entityUtil.MindNode2Util;
import com.hwj.entityUtil.MindNodeTool;
import com.hwj.entityUtil.MindNodeUtil;
import com.hwj.entityUtil.Node2;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.FileUpload;
import com.hwj.tools.StatusMap;
import com.hwj.tools.StreamToBlob;
import com.hwj.tools.TryCatchFileCollectionService;
import com.hwj.tools.TryCatchFileShareService;
import com.hwj.tools.TryCatchFileStreamService;
import com.hwj.tools.TryCatchMindMapService;
import com.hwj.tools.TryCatchShareService;
import com.hwj.tools.TryCatchUploadFileService;
import com.hwj.tools.TryCatchUserService;
import com.hwj.tools.TryCatchZsdService;

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
	@Autowired
	private TryCatchFileCollectionService tryCatchFileCollectionService;
	@Autowired
	private TryCatchFileShareService tryCatchFileShareService;
	@Autowired
	private TryCatchShareService tryCatchShareService;
	@Autowired
	private TryCatchZsdService tryCatchZsdService;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private StreamToBlob streamToBlob;
	@Autowired
	private FileUpload fileUpload;
	
	
	
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
	public String getNodeDatexinjian(@RequestParam String nodeName,
			HttpServletRequest request) throws IOException{
		
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
		mindNode.setNodename(nodeName);
		
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
		
		System.out.println(type+"$#$$##$$#$"+nodeid);
		
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
		
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node2 node = new Node2();
			node.id = ((String) dataRecord.get("id"));
			node.topic = ((String) dataRecord.get("topic"));
			node.parentid = ((String) dataRecord.get("parentid"));
			nodeList.put(node.id, node);
		}
		
		
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
	
	
	
	/**
	 * @author Ragty
	 * @param  删除节点以及节点之后的节点(未删除知识点，需构建知识点层)
	 * @serialData 2018.3.23
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/deleteNodeByBatch.do")
	@ResponseBody
    public String deleteNodeByBatch(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
    	
    	Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
    	String nodeid=String.valueOf(map.get("nodeid"));
    	
    	HttpSession session=request.getSession();
    	String userid=String.valueOf(session.getAttribute("username"));
    	
    	if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}
    	
    	
    	List<Map<String, String>> list3 = new ArrayList();
		list3 = this.tryCatchMindMapService.getzijiedian(nodeid, userid);
		List dataList = list3;
		
		System.out.println(list3 + "list");
		
		
		
		//在循环体里执行删除
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			
			String id=String.valueOf(dataRecord.get("id"));
			
			MindNode mindNode=this.tryCatchMindMapService.getMindNodeObject("nodeid", "userid", id, userid);
			
			System.out.println("这他妈就是节点"+mindNode);
			
			List<UploadFile> listUploadFile=null;
			try {
				listUploadFile=this.tryCatchUploadFileService.getUploadeFile("userid", userid, "zsdid", id);
				if(listUploadFile.size()<=0){
					return statusMap.a("3");
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			//内循环(将一个节点上的所有文件斩草除根)
			try {
				
			for(int i=0;i<listUploadFile.size();i++){
				UploadFile uploadFile = null;
				FileShare fileShare = null;
				FileCollection fileCollection = null;
				String realPath = null;
				String firstStatus=null;
				String zlid=null;
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				
			//Step1.获取节点上关于文件的信息
			try {
				uploadFile=listUploadFile.get(i);
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				zlid=uploadFile.getFiles();
				System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+zlid);
				firstStatus=uploadFile.getFirstStatus();
				System.out.println("小样》》》"+firstStatus);
				
				if( !( zlid.equals(null)||zlid.equals("null")) ){
					uploadFile = this.tryCatchUploadFileService.getUploadFile("zsdid",
							"files", id, zlid);
					fileShare = this.tryCatchFileShareService.getFileShare("nodeid",
							 id, "f_id", zlid);
					fileCollection = this.tryCatchFileCollectionService
							.getFileCollection1("nodeid", id, "f_id", zlid);
					realPath = uploadFile.getFileroot();
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			
		//Step2.将节点上的文件相关资料删除
		try {
				
			if(firstStatus.equals("1")){
				
				File file = new File(realPath);

				System.out.println(file + "进入了呢");

				if (!file.exists()) {
					System.out.println("文件不存在");
				} else {
					System.out.println("文件存在");
					System.out.println("即将删除文件");
					file.delete();
					System.out.println("成功,已将文件删除");
				}

				// 修改回收站状态
				try {
					FileStream fileStream = tryCatchFileStreamService
							.getFileStream1("userid", "f_id", userid, zlid);

					if (!fileStream.equals("null")) {
						fileStream.setDelStatus("1");
						tryCatchFileStreamService.updateFileStream(fileStream);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

				if ((tryCatchUploadFileService.delAllUploadFile("userid", "files",
						userid, zlid))
						&& (tryCatchFileShareService.delAllFileShare("userid",
								userid, "f_id", zlid))
						&& (tryCatchFileCollectionService.delAllFileCollection(
								"userid", userid, "f_id", zlid))) {
					System.out.println("大清洗式的删除");
					
				   }
				
			}else{
				
				if ((tryCatchUploadFileService.deleteUploadFile(uploadFile))
						&& (tryCatchFileShareService.delShareFile(fileShare))
						&& (tryCatchFileCollectionService
								.delFileCollection(fileCollection))) {
					System.out.println("只删除节点上的文件");
				}
				
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
			
			}    //内循环结尾
			
		} catch (Exception e) {
			// TODO: handle exception
		}        //内循环异常抛出      
			
			
			System.out.println("!!!!!!!!!!!!!!!!删除知识点");
			//Step3.将节点上的知识点删除
			try {
				
				Zsd zsd=tryCatchZsdService.getZsd1("userid", "zsdid", userid, id);
				if( !(zsd == null) ){
					this.tryCatchZsdService.deleteZsd(zsd);
				}
				
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			//step4.若是删除整个图(将分享过的思维导图也一并删除掉)
			try {
				
				if( mindNode.getParentid().equals( "00100" )  ) {
					
					Share share=this.tryCatchShareService.getshare("userid", userid, "zsdid", mindNode.getNodeid());
					if( !(share == null) ){
						System.out.println("删除分享过的思维导图"+share);
						this.tryCatchShareService.delShare(share);
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			
			//将节点删除
			if(tryCatchMindMapService.deleteMindNodeObject(mindNode)){
				return statusMap.a("1");
			} else{
				return statusMap.a("3");
			}
			
		}
    	    
    	return null;
    }
	
	
	/**
	 * @author Ragty
	 * @param  节点上上传文件接口
	 * @serialData 2018.3.28
	 * @param mindNodeTool
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping({ "/setUpload.do" })
	@ResponseBody
	public String setUpload(MindNodeTool mindNodeTool,
			@RequestParam("fileToUpload") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String nodeid = mindNodeTool.getNodeid();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String uploadtima = df.format(new Date());
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		String useridBefore = tryCatchMindMapService
				.getMindNode("nodeid", nodeid).get(0).getUserid();
		
		if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}
		
		//禁止非本节点用户在节点上传文件
		if (! (userid.equals(useridBefore)) ){
			System.out.println("到这里了");
			return statusMap.a("5");
		}
		
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String files = df2.format(new Date());
		String filename = file.getOriginalFilename();
		
		String zsdid = null;
		String zlid = null;
		try {
			UploadFile uploadFile = tryCatchUploadFileService.getUploadFile(
					"userid", "filename", "firstStatus", userid, filename, "1");
			zsdid = uploadFile.getZsdid();
			zlid = uploadFile.getFiles();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//判断资源页面有无上传过，没上传过的话（直接上传），否则直接添加数据
		if (tryCatchUploadFileService.haveUploadFile("userid", "filename",
				userid, filename)) {
			
			if(zsdid == null){
				UploadFile uploadFile = tryCatchUploadFileService.getUploadFile(
						"userid", "filename", "firstStatus", userid, filename, "1");
				
				uploadFile.setZsdid(nodeid);
				uploadFile.setFirstStatus("0");
				
				UploadFile uploadFile1 = tryCatchUploadFileService
						.getUploadFile("userid", "filename", "firstStatus",
								userid, filename, "1");
				uploadFile1.setZsdid("1");
				
			} else if (zsdid != nodeid) {

				UploadFile uploadFile = tryCatchUploadFileService
						.getUploadFile("userid", "filename", userid, filename);
				uploadFile.setZsdid(nodeid);
				uploadFile.setFirstStatus("0");

				if (tryCatchUploadFileService.haveUploadFile("files", "zsdid",
						zlid, nodeid)) {
					System.out.println("防止在该节点重复上传文件");
					return statusMap.a("3");
				}

				if (tryCatchUploadFileService.saveUploadFile(uploadFile)) {
					System.out.println("在新节点上传该文件");
					return statusMap.a("1");
				} else {
					return statusMap.a("2");
				}

			}
			
		}
		
		
		//直接上传文件
		String type = "";

		String fileExtension = file.getOriginalFilename().substring(
				file.getOriginalFilename().lastIndexOf(".") + 1,
				file.getOriginalFilename().length());
		if ((fileExtension.equals("bmp")) || (fileExtension.equals("drw"))
				|| (fileExtension.equals("dxf"))
				|| (fileExtension.equals("eps"))
				|| (fileExtension.equals("gif"))
				|| (fileExtension.equals("jpg"))
				|| (fileExtension.equals("png"))
				|| (fileExtension.equals("pcd"))
				|| (fileExtension.equals("pcx"))) {
			type = "picture";
		} else if ((fileExtension.equals("avi"))
				|| (fileExtension.equals("mpeg"))
				|| (fileExtension.equals("mpg"))
				|| (fileExtension.equals("dat"))
				|| (fileExtension.equals("ra")) || (fileExtension.equals("rm"))
				|| (fileExtension.equals("wmv"))
				|| (fileExtension.equals("mp4"))
				|| (fileExtension.equals("swf"))
				|| (fileExtension.equals("f4v"))) {
			type = "video";
		} else if ((fileExtension.equals("cd"))
				|| (fileExtension.equals("ogg"))
				|| (fileExtension.equals("mp3"))
				|| (fileExtension.equals("asf"))
				|| (fileExtension.equals("wma"))
				|| (fileExtension.equals("wav"))
				|| (fileExtension.equals("rm"))
				|| (fileExtension.equals("midi"))
				|| (fileExtension.equals("ape"))) {
			type = "doc";
		} else {
			type = "other";
		}
		byte[] newsPageByte = file.getBytes();
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");

		String fileURL = realPath + "upload";
		String trueURL = "upload";

		BeSaveFileUitl be = new BeSaveFileUitl();
		be.setFileExtension(fileExtension);
		be.setFilesByte(newsPageByte);
		be.setFileURL(fileURL);

		BeSaveFileUitl be1 = new BeSaveFileUitl();
		be1.setFileExtension(fileExtension);
		be1.setFilesByte(newsPageByte);
		be1.setFileURL(trueURL);

		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("ip" + ip);

		String Url = "";
		String Url2 = "";
		String Url3 = "";
		String[] string = this.fileUpload.saveFile(be);
		String[] string1 = this.fileUpload.saveFile(be1);
		if ("1".equals(string[0])) {
			String URL = string[1];
			String url = URL.replaceAll("\\\\", "/") + "." + fileExtension;

			String URL1 = string1[1];
			String url1 = URL1.replaceAll("\\\\", "/") + "." + fileExtension;

			String url2 = "";

			if (type == "video") {
				url2 = URL1.replaceAll("\\\\", "/") + "." + fileExtension;
			}
			if (type == "doc") {
				url2 = URL1.replaceAll("\\\\", "/") + "." + fileExtension;
			}
			if (type == "picture") {
				url2 = URL1.replaceAll("\\\\", "/") + "." + fileExtension;
			}
			if (type == "other") {
				url2 = URL1.replaceAll("\\\\", "/") + "." + fileExtension;
			}

			Url2 = "http://" + ip + ":8080/upload"
					+ url2.substring(url1.indexOf("/"));// 文件的输出路劲
			Url = "http://" + ip + ":8080" + url.substring(url1.indexOf("/"));// 文件的输入路径
			Url3 = url;
			System.out.println(Url2);
		}

		Blob blob = streamToBlob.toBlob(file.getInputStream());

		// 数据备份
		FileStream fileStream = new FileStream();
		fileStream.setFilename(filename);
		fileStream.setF_id(files);
		fileStream.setFileExtension(fileExtension);
		fileStream.setParentid("0");
		fileStream.setUserid(userid);
		fileStream.setTrueUrl(Url2);
		fileStream.setFileStream(blob);
		fileStream.setFileType(type);
		fileStream.setDelStatus("0");
		fileStream.setNodeid(nodeid);
		fileStream.setUploadTime(uploadtima);

		tryCatchFileStreamService.saveFileStream(fileStream);

		UploadFile uploadFile = new UploadFile();
		uploadFile.setFilename(filename);
		uploadFile.setFiles(files); // 文件id
		uploadFile.setFilepath(Url2); // 文件的输出路径（在服务器上的访问路径）
		uploadFile.setFileroot(Url3); // 文件在pc上的实际路径
		uploadFile.setOldfilepath(Url);// 文件在实际中的路径(没用)
		uploadFile.setZlms("该资料现在没有描述");
		uploadFile.setFiletype(type);
		uploadFile.setUploadtime(uploadtima);
		uploadFile.setZsdid(nodeid);
		uploadFile.setUserid(userid);
		uploadFile.setF_parentid("0");
		uploadFile.setFirstStatus("1");
		if (this.tryCatchUploadFileService.saveUploadFile(uploadFile)) {
			System.out.println("第一次在节点上传该文件");
			return statusMap.a("1");
		}
		return statusMap.a("2");
		
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.3.29
	 * @param  节点上获取上传文件接口 
	 * @param mindNodeUtil
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getUploadfile.do")
	@ResponseBody
	public String getUploadfile(MindNodeUtil mindNodeUtil,
			HttpServletRequest request) throws IOException{
		
		String nodeid = mindNodeUtil.getNodeid();
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}
		
		String uploadzl = "success";
		String state = null;
		
		List<UploadFile> list = tryCatchUploadFileService.getUploadeFile(
				 "userid", userid, "zsdid", nodeid);
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		if ((list == null) || (list.equals(null))) {
			state = "1";
		} else {
			state = "0";
			for(int i = 0; i < list.size(); i++){
				UploadFile uploadFile = list.get(i);
				Map<String, String> map = new HashMap<String, String>();
				
				String type = uploadFile.getFiletype();
				String tubiao = "";
				if (type.equals("doc")) {
					tubiao = "/assets/avatars/word.jpg";
				} else if (type.equals("video")) {
					tubiao = "/assets/avatars/shipin.jpg";
				} else if (type.equals("picture")) {
					tubiao = "/assets/avatars/tupian.jpg";
				} else if (type.equals("other")) {
					tubiao = "/assets/avatars/tuzhi.jpg";
				} else if (type.equals("5")) {
					tubiao = "/assets/avatars/yinpin.jpg";
				}
				
				map.put("zlmc", uploadFile.getFilename());
				map.put("tubiao", tubiao);
				map.put("zlms", uploadFile.getZlms()); // 资料描述
				map.put("zlid", String.valueOf(uploadFile.getFiles()));
				map.put("filepath", uploadFile.getFilepath());
				map.put("time", uploadFile.getUploadtime());
				map.put("fileType", uploadFile.getFiletype());
				
				if (list2.size() < list.size()){
					list2.add(map);
				}
				
			}   //<!-- for -->
			
		}    //<!-- else -->
		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("uploadzl", uploadzl);
		map2.put("state", state);
		map2.put("list2", list2);
		map2.put("nodeid", nodeid);
		
		return jsonAnalyze.map2Json(map2);
	}
	
	
	/**
	 * @author Ragty
	 * @param  删除节点上的上传文件接口 
	 * @serialData 2018.3.29
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delUpload.do")
	@ResponseBody
	public String delUpload(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{

		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String zlid = String.valueOf(map.get("zlid"));
		String nodeid = String.valueOf(map.get("nodeid"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid.equals("") || userid == null){
			return statusMap.a("2");
		}
		
		UploadFile uploadFile = null;
		FileShare fileShare = null;
		FileCollection fileCollection = null;
		String realPath = null;
		
		try {
			uploadFile = this.tryCatchUploadFileService.getUploadFile("zsdid",
					"files", nodeid, zlid);
			fileShare = this.tryCatchFileShareService.getFileShare("nodeid",
					nodeid, "f_id", zlid);
			fileCollection = this.tryCatchFileCollectionService
					.getFileCollection1("nodeid", nodeid, "f_id", zlid);
			realPath = uploadFile.getFileroot();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		// 状态是1，真实删除所有文件，状态是0或者null就删除节点上的
        if(uploadFile.getFirstStatus().equals("1")){
        	
        	File file = new File(realPath);
        	
        	if(!file.exists()){
        		System.out.println("文件不存在");
        	} else{
        		System.out.println("即将删除文件");
        		file.delete();
        		System.out.println("已删除文件");
        	}
        	
        	//修改回收站状态
        	try {
				FileStream fileStream = tryCatchFileStreamService.getFileStream1(
						"userid", "f_id", userid, zlid);
				
				if (!fileStream.equals("null")) {
					fileStream.setDelStatus("1");
					tryCatchFileStreamService.updateFileStream(fileStream);
				}
				
				
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
        	//清空相关数据
        	if ((tryCatchUploadFileService.delAllUploadFile("userid", "files",
					userid, zlid))
					&& (tryCatchFileShareService.delAllFileShare("userid",
							userid, "f_id", zlid))
					&& (tryCatchFileCollectionService.delAllFileCollection(
							"userid", userid, "f_id", zlid))) {
				System.out.println("大清洗式的删除");
				return statusMap.a("1");
			}
			return statusMap.a("3");
        	
        } else {
        	
        	//只删除与该节点上传文件有关的数据
        	if ((tryCatchUploadFileService.deleteUploadFile(uploadFile))
					&& (tryCatchFileShareService.delShareFile(fileShare))
					&& (tryCatchFileCollectionService
							.delFileCollection(fileCollection))) {
				System.out.println("只删除节点上的文件");
				return statusMap.a("1");
			}
			return statusMap.a("3");
        }
		
	}
	
	
	/**
	 * @author Ragty
	 * @param  分享知识图谱的接口
	 * @serialData 2018.4.2
	 * @param nodeid
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("setShare.do")
	@ResponseBody
	public String setShare(@RequestParam String nodeid,
			HttpServletRequest request) throws IOException{
		
		String type = tryCatchMindMapService.getMindNode("nodeid", nodeid)
				.get(0).getType();
		String mindUser = tryCatchMindMapService.getMindNode("nodeid", nodeid)
				.get(0).getUserid();
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if (userid == null || userid.equals("")) {
			return statusMap.a("2");
		}
		
		//防止分享别人的知识图谱
		if (!userid.equals(mindUser)){
			System.out.println("执行到了这里");
			return statusMap.a("5");
		}
		
		try {
			Share share=this.tryCatchShareService.getshare("userid", userid, "zsdid",type);
			System.out.println("%%%%%Share"+share);
			if(!share.equals("null")){
				return statusMap.a("3");  //将状态3设置为防止重复上传
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Share share = new Share();
		share.setMindName(tryCatchMindMapService.getMindNode("nodeid", type)
				.get(0).getNodename());
		share.setUserid(userid);
		share.setSharetype("mindnode");
		share.setZsdid(type);
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		share.setSharetime(df.format(new Date()));
		if (tryCatchShareService.saveShare(share)) {

			return statusMap.a("1"); // 分享成功
		}
		return statusMap.a("2"); // 分享失败
		
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取到思维导图分享的列表信息
	 * @serialData 2018.4.2
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping ("/getShareMindNode.do")
	@ResponseBody
	public String getShareMindNode(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map1 = jsonAnalyze.json2Map(requestJsonBody);
		String sharetype=String.valueOf(map1.get("sharetype"));
		Integer currentPage=(Integer) map1.get("currentPage");
		Integer pageSize=12;
		
		List<Share> list = null;
		try {
			list = tryCatchShareService.getSharePageByOne(
					currentPage, pageSize, "sharetype", sharetype);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		if(userid.equals("null")||userid.equals(null)){
			return statusMap.a("2");    //未登录用户
		}
		
		if (list == null) {
			return statusMap.a("1");     //没有获取到分享的思维导图
		}
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		for(int i=0; i<list.size(); i++){
			Share share = list.get(i);
		    Map<String, String> map = new HashMap<String, String>();
		    map.put("nodeid", share.getZsdid());        // 获取分享的思维导图根节点id
			map.put("nodename", share.getMindName());   // 获取分享的思维导图的名字
			map.put("userid", share.getUserid());       // 获取分享的思维导图的拥有者
			map.put("sharetime", share.getSharetime()); // 获取分享思维导图的时间
			list2.add(map);
		}
		
		return jsonAnalyze.list2Json(list2);
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取分享的思维导图的总页数
	 * @serialData 2018.4.2
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping ("/getShareMindNodeTotal.do")
	@ResponseBody
	public Long getShareMindNodeTotal(@RequestBody String requestJsonBody, 
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String sharetype=String.valueOf(map.get("sharetype"));
		
		Integer pageSize=12;
		Long total;
		try {
			total=this.tryCatchShareService.countShareByOne("sharetype", sharetype);
			total=(total-1)/pageSize+1;
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		if( total.equals("null")||total.equals(null) ){
			return null;
		}
		
		return total;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取分享到的思维导图的具体内容
	 * @serialData 2018.4.2
	 * @param userid
	 * @param nodeid
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getShareMindNodeObject.do")
	@ResponseBody
	public String getShareMindNodeObject(@RequestParam String userid,
			@RequestParam String nodeid, HttpServletRequest request) throws IOException{
		
        String type = null;
        List<MindNode> list = new ArrayList<MindNode>();
		try {
			type = ((MindNode) this.tryCatchMindMapService.getMindNode("nodeid",nodeid).get(0)).getType();
			list = this.tryCatchMindMapService.getMindNode("userid", userid,
					"type", type);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		System.out.println("$$%$%%%$"+list);
		

		HttpSession session = request.getSession();
		String uid = String.valueOf(session.getAttribute("username"));
		if (uid.equals("null") || uid.equals(null)) {
			return statusMap.a("2");
		}

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
					((Node2) nodeList.get(node.parentid)).addChild(node);  //解决空指针
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
			}
		}
		System.out.println("sdfsdf :" + root.toString());
		System.out.println("sdsfsdfds:" + root);
		mindNode2Util.setState("1");

		data.put("data", root.toString());

		String datas = this.jsonAnalyze.object2Json(data).toString();  //调用底层封装的两个方法

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
	 * @param  保存各个节点的知识点
	 * @serialData 2018.4.3
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveZsd.do")
	@ResponseBody
	public String saveZsd(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String zsdid = String.valueOf(map.get("zsdid"));
		String zsdmc = String.valueOf(map.get("zsdmc"));
		String zsdms = String.valueOf(map.get("zsdms"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		Zsd zsd= new Zsd();
		zsd.setZsdid(zsdid);
		zsd.setZsdmc(zsdmc);
		zsd.setZsdms(zsdms);
		zsd.setUserid(userid);
		
        String mindUser = tryCatchMindMapService.getMindNode("nodeid", zsdid).get(0).getUserid();
		
		//禁止在别人的知识图谱里添加知识点
		if( !(userid.equals(mindUser)) ){
			return statusMap.a("3");
		}
		
		if(tryCatchZsdService.getZsd1("userid", "zsdid", userid, zsdid) == null){
			
			if(tryCatchZsdService.saveZsd(zsd)){
				return statusMap.a("1");
			} else {
				return statusMap.a("2");
			}
			
		} else {
			
			if(tryCatchZsdService.update(zsd)){
				return statusMap.a("1");
			} else {
				return statusMap.a("2");
			}
			
		}
		
	}
	
	/**
	 * @author Ragty
	 * @param  知识图谱每个节点获取该节点的知识点内容
	 * @serialData 2018.4.3
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getZsd.do")
	@ResponseBody
	public String getZsd(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map3 = jsonAnalyze.json2Map(requestJsonBody);
		String nodeid = String.valueOf(map3.get("nodeid"));
		
		String mindUser = tryCatchMindMapService.getMindNode("nodeid", nodeid).get(0).getUserid();
		
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> map2 = new HashMap<String, Object>();
		
		Zsd zsd=null;
		
		try {
			zsd = tryCatchZsdService.getZsd1("userid", "zsdid", mindUser, nodeid);
			map.put("zsdmc", zsd.getZsdmc());
			map.put("zsdms", zsd.getZsdms());
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		map2.put("zsdid", nodeid);
		map2.put("userid", mindUser);
		map2.put("map", map);
		
		return jsonAnalyze.map2Json(map2);
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取我的知识图谱的总页数
	 * @serialData 2018.4.3
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/myMindMapTotal.do")
	@ResponseBody
	public Long myMindMapTotal(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String parentid=String.valueOf(map.get("parentid"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		Long total=null;
    	Integer pageSize=12;
    	try {
    		total=this.tryCatchMindMapService.countByTwoMind("userid", "parentid", userid, parentid);
    		total=(total-1)/pageSize+1;   
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
    	if( total.equals("null")||total.equals(null) ){
    		return null;
    	}
		return total;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取所有用户的知识图谱(用于教师端查看所有用户的知识图谱)
	 * @serialData 2018.4.4
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/teacherMind.do")
	@ResponseBody
	public String teacherMind(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String,Object> map=jsonAnalyze.json2Map(requestJsonBody);
		String parentid=String.valueOf(map.get("parentid"));
		Integer currentPage=(Integer) map.get("currentPage");
		
		HttpSession session=request.getSession();
		String userid=String.valueOf(session.getAttribute("username"));
		
		if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}
		
		Integer pageSize=12;
		List<MindNode> list=tryCatchMindMapService.getMindNodeByPage(currentPage, pageSize, "parentid", parentid);
		
		if(list.equals(null) || list.equals("null")){
			return statusMap.a("3");
		}
		
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		try {
			
			for(int i=0; i<list.size(); i++){
				MindNode mindNode = list.get(i);
				String realName = tryCatchUserService.getOneLoginUser("nickName", mindNode.getUserid()).getRealName();
		        
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("nodename",mindNode.getNodename() );
	        	map1.put("userid",mindNode.getUserid() );
	        	map1.put("nodeid",mindNode.getNodeid() );
	        	map1.put("realName",realName );
                list2.add(map1);
                
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return jsonAnalyze.list2Json(list2);
	}
	
	
	/**
	 * @author Ragty
	 * @param  获取总共的知识图谱的页数（一页12条，服务于教师端）
	 * @serialData 2018.4.4
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/teacherMindTotal.do")
	@ResponseBody
	public Long teacherMindTotal(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map =jsonAnalyze.json2Map(requestJsonBody);
		String parentid = String.valueOf(map.get("parentid"));
		
		Integer pageSize = 12;
		Long total=null;
		
		try {
			total = tryCatchMindMapService.countByOneMind("parentid", parentid);
			total = (total-1)/pageSize+1;
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if( total.equals("null")||total.equals(null) ){
			return null;
		}
		
		return total;
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取教师端搜索到的个人思维导图的列表(可以写一个深度搜索)
	 * @serialData 2018.4.4
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/findTeacherMind.do")
	@ResponseBody
	public String findTeacherMind(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map =jsonAnalyze.json2Map(requestJsonBody);
		String realName =  String.valueOf(map.get("realName"));
		Integer currentPage  = (Integer) map.get("currentPage");
		Integer pageSize = 12;
		
		String userid=null;
		try {
			userid=this.tryCatchUserService.getOneLoginUser("realName", realName).getNickName();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		HttpSession session=request.getSession();
		String username=String.valueOf(session.getAttribute("username"));
		
		if( (username.equals("null"))||(username.equals(null)) ){
			return statusMap.a("2");
		}
		
		List<MindNode> list = tryCatchMindMapService.getMindNodeByPage(
				currentPage, pageSize, "userid", userid, "parentid", "00100");
		
		System.out.println(list == null);
		if( list == null ){
			return statusMap.a("3");
		}
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		
		try {
			
			for(int i=0; i< list.size(); i++){
				MindNode mindNode = list.get(i);
				Map<String, String> map2 = new HashMap<String, String>();
				LoginUser loginUser=this.tryCatchUserService.getOneLoginUser("nickName", mindNode.getUserid());
	        	String realName1=loginUser.getRealName();
				
				map2.put("nodename",mindNode.getNodename() );
	        	map2.put("userid",mindNode.getUserid() );
	        	map2.put("nodeid",mindNode.getNodeid() );
	        	map2.put("realName",realName1 );
				list2.add(map2);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		return jsonAnalyze.list2Json(list2);
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取搜索后的知识图谱总页数
	 * @serialData 2018.4.4
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/findTeacherMindTotal.do")
	@ResponseBody
	public Long findTeacherMindTotal(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String realName=String.valueOf(map.get("realName"));
		
		Integer pageSize=12;
		String userid=null;
		try {
			userid=this.tryCatchUserService.getOneLoginUser("realName", realName).getNickName();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		if( (userid.equals("null"))||(userid.equals(null)) ){
			return null;        
		}
		
		Long total=null;
		try {
			total=this.tryCatchMindMapService.countByTwoMind("userid", "parentid", userid, "00100");
			total=(total-1)/pageSize+1; 
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
		if( total.equals("null")||total.equals(null) ){
			return null;
		}
		return total;
		
	}
	
	
	/**
	 * @author Ragty
	 * @param  删除分享知识图谱列表中的知识图谱
	 * @serialData 2018.4.4
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/deleteShareMindMap.do")
    @ResponseBody
    public String deleteShareMindMap(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
        Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
        String nodeid=String.valueOf(map.get("nodeid"));
        String deleteUser=String.valueOf(map.get("deleteUser"));
        
        HttpSession session=request.getSession();
        String userid=String.valueOf(session.getAttribute("username"));
        
        if( userid.equals("null")|| userid.equals(null) ){
        	return statusMap.a("2");           //尚未登录的
        }
        
        if( !userid.equals(deleteUser) ){
        	return statusMap.a("3");           //没有删除权限的
        }
        
        Share share=null;
        try {
			share=this.tryCatchShareService.getshare("userid", userid, "zsdid", nodeid);
			
			if(share!=null){
              
				if(this.tryCatchShareService.delShare(share)){
					return statusMap.a("1");      //删除成功
				}else{
					return statusMap.a("5");      //删除失败
				}
				
			}
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
        return null;
    }
	
	
    
	/**
	 * @author Ragty
	 * @param  保存节点拖拽移动后的位置(draggale.js中写脚本)
	 * @serialData 2018.4.4
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveNodePostion.do")
	@ResponseBody
	public String saveNodePostion(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String beforeid=String.valueOf(map.get("beforeId"));
    	String afterid=String.valueOf(map.get("afterId"));
    	
    	HttpSession session=request.getSession();
    	String userid=String.valueOf(session.getAttribute("username"));
    	
    	if( userid.equals("null")||userid.equals(null) ){
    		return statusMap.a("2");
    	}
    	
    	MindNode mindNode=this.tryCatchMindMapService.getMindNodeObject(beforeid);   //获取到拖动的节点
    	
    	//添加权限，不是自己的不能拖
    	String userName=String.valueOf(mindNode.getUserid());
    	if(!userName.equals(userid)){
    		return statusMap.a("4");
    	}
    	
    	mindNode.setParentid(afterid);
    	
    	if(this.tryCatchMindMapService.updateMindNodeObject(mindNode)){
    		return statusMap.a("1");
    	}

    	return statusMap.a("3");
		
	}
	
	
	
}
