package com.hwj.web;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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

import com.hwj.entity.FileCollection;
import com.hwj.entity.FileShare;
import com.hwj.entity.FileStream;
import com.hwj.entity.MindMap;
import com.hwj.entity.MindNode;
import com.hwj.entity.Share;
import com.hwj.entity.UploadFile;
import com.hwj.entity.Zsd;
import com.hwj.entityUtil.MindNodeTool;
import com.hwj.entityUtil.Node2;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFileCollectionService;
import com.hwj.tools.TryCatchFileShareService;
import com.hwj.tools.TryCatchFileStreamService;
import com.hwj.tools.TryCatchMindMapService;
import com.hwj.tools.TryCatchNewMindService;
import com.hwj.tools.TryCatchShareService;
import com.hwj.tools.TryCatchUploadFileService;
import com.hwj.tools.TryCatchUserService;
import com.hwj.tools.TryCatchZsdService;

@Controller
public class NewMindController {

	@Autowired
	private TryCatchNewMindService tryCatchNewMindService;
	@Autowired
	private TryCatchMindMapService tryCatchMindMapService;
	@Autowired
	private TryCatchUploadFileService tryCatchUploadFileService;
	@Autowired
	private TryCatchFileShareService tryCatchFileShareService;
	@Autowired
	private TryCatchFileCollectionService tryCatchFileCollectionService;
	@Autowired
	private TryCatchFileStreamService tryCatchFileStreamService;
	@Autowired
	private TryCatchZsdService tryCatchZsdService;
	@Autowired
	private TryCatchShareService tryCatchShareService;
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
	 * @param  新建子节点
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
	
	
	
	
	/**
	 * @author Ragty
	 * @param  删除数据库（学习是不可能学习的，只是删库跑路才能勉强维持的了生活）
	 * @serialData 2018.06.11(批量删除节点以及数据)
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("delMapNode.do")
	@ResponseBody
	public String delMapNode(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
    	String nodeid = String.valueOf(map.get("nodeid"));
    	String rootid = String.valueOf(map.get("rootid"));
    			
    	
    	HttpSession session=request.getSession();
    	String userid=String.valueOf(session.getAttribute("username"));
    	
    	if (userid.equals("null") || userid.equals(null)) {
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
		
		List<MindNode> list = jsonAnalyze.parseList(activeList);
		
		List<MindNode> list2 = new ArrayList<MindNode>();
		
		//用来存储取出要删除的子节点
		List<MindNode> list3 = tryCatchNewMindService.getChild(list, nodeid, list2);
		
		
		
		//循环处理数据
		for (Iterator it = list.iterator(); it.hasNext();) {
			
			MindNode mindNode = (MindNode) it.next();
			String id= mindNode.getNodeid();	
			
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
			
		}
		
		
		//step4.若是删除整个图(将分享过的思维导图也一并删除掉,并删掉整个图)
		try {
			
			if( nodeid.equals(rootid) ) {
				
				Share share=this.tryCatchShareService.getshare("userid", userid, "zsdid", nodeid);
				if( !(share == null) ){
					System.out.println("删除分享过的思维导图"+share);
					this.tryCatchShareService.delShare(share);
				}
				
				if(tryCatchNewMindService.delMindMap(mindMap)){
					System.out.println("删除整个图");
					return statusMap.a("1");
				}
				
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
    	    
		
		//这边更新数据库
		//get data after delete 时间复杂度O(n)
		List<MindNode> list4 = tryCatchNewMindService.getNope(list3, list);
		
		
		//打开树
		String open = tryCatchMindMapService.openMind(list4, rootid);
		
		mindMap.setMaplist(jsonAnalyze.list2Json(list4));    //更新树
		mindMap.setData(open);                              //更新数据
		
		if(tryCatchNewMindService.updateMindMap(mindMap)){
			return statusMap.a("1");
		}
		
		return statusMap.a("3");
		
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取自己的知识图谱列表
	 * @serialData 2018.6.12
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getMyMap.do")
	@ResponseBody
	public String getMyMap(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		Integer currentPage=(Integer) map.get("currentPage");
		Integer pageSize = (Integer) map.get("pageSize");
		
		if (userid.equals("null")||userid.equals(null)){
			return statusMap.a("1");
		}
		
		List<MindMap> list = new ArrayList<MindMap>();
		list = tryCatchNewMindService.getMindMapByPage(currentPage, pageSize, "userid", userid);

		if (list == null){
			return statusMap.a("2");
		}
		
		return jsonAnalyze.list2Json(list);
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取我的知识图谱总页数
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getMyMapPage.do")
	@ResponseBody
	public Long getMyMapPage(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String parentid=String.valueOf(map.get("parentid"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		Long total=null;
    	Integer pageSize=(Integer) map.get("pageSize");
    	try {
    		total=this.tryCatchNewMindService.countByOneMind("userid", userid);
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
	 * @param  打开某个具体的知识图谱
	 * @serialData 2018.6.12
	 * @param nodeid
	 * @param request
	 * @return
	 */
	@RequestMapping("openMyMap.do")
	@ResponseBody
	public String openMyMap(@RequestParam String nodeid,
			HttpServletRequest request){
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", nodeid);
		
		return mindMap.getData();
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  分享知识图谱
	 * @param rootid（这里是rootid）
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("shareMap.do")
	@ResponseBody
	public String shareMap(@RequestParam String rootid,
			HttpServletRequest request) throws IOException{
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", rootid);
		String mindUser = mindMap.getUserid();
		
		//权限
		if (!userid.equals(mindUser)){
			return statusMap.a("5");
		}
		
		try {
			Share share=this.tryCatchShareService.getshare("userid", userid, "zsdid",rootid);
			System.out.println("%%%%%Share"+share);
			if(!share.equals("null")){
				return statusMap.a("3");  //将状态3设置为防止重复上传
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		//DataBase handle
		Share share = new Share();
		share.setMindName(mindMap.getNodename());
		share.setUserid(mindUser);
		share.setSharetype("mindnode");
		share.setZsdid(rootid);
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		share.setSharetime(df.format(new Date()));
		
		if (tryCatchShareService.saveShare(share)) {

			return statusMap.a("1"); // 分享成功
		}
		return statusMap.a("2"); // 分享失败
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取分享的知识图谱列表
	 * @serialData 2018.6.12
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getShareMap.do")
	@ResponseBody
	public String getShareMap(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map1 = jsonAnalyze.json2Map(requestJsonBody);
		String sharetype=String.valueOf(map1.get("sharetype"));
		Integer currentPage= (Integer) map1.get("currentPage");
		Integer pageSize= (Integer) map1.get("pageSize");
		
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
	 * @param  获取分享的知识图谱总页数
	 * @serialData 2018.6.12
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getShareMapTotal.do")
	@ResponseBody
	public Long getShareMapTotal(@RequestBody String requestJsonBody, 
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String sharetype=String.valueOf(map.get("sharetype"));
		
		Integer pageSize= (Integer) map.get("pageSize");
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
	 * @param  打开分享过的知识图谱
	 * @serialData 2018.6.12
	 * @param userid
	 * @param nodeid
	 * @param request
	 * @return
	 */
	@RequestMapping("openShareMap.do")
	@ResponseBody
	public String openShareMap(@RequestParam String userid,
			@RequestParam String nodeid, HttpServletRequest request){
		
		HttpSession session = request.getSession();
		String uid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", nodeid);
		
		return mindMap.getData();
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  删除分享列表中的知识图谱
	 * @serialData 2018.6.12
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("delShareMap.do")
	@ResponseBody
	public String delShareMap(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
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
        return statusMap.a("10");
		
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取选中节点后面的子节点
	 * @serialData 2018.6.12
	 * @param mindNodeTool
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("getMapChild.do")
	@ResponseBody
	public String getMapChild(MindNodeTool mindNodeTool,
			HttpServletRequest request) throws IOException{
		
		String rootid = mindNodeTool.getRootid();
		String nodeid = mindNodeTool.getNodeid();
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", rootid);
		String activeList = mindMap.getMaplist();
		
        List<MindNode> list = jsonAnalyze.parseList(activeList);
		
		List<MindNode> list2 = new ArrayList<MindNode>();
		
		//用来存储取出选中节点及它的子节点
		List<MindNode> list3 = tryCatchNewMindService.getChild(list, nodeid, list2);
		
		//包装数据
		String open = tryCatchNewMindService.openChildMap(list3, nodeid, rootid);
		return open;
	}
	
	
	
	
	/**
	 * @author Ragty
	 * @param  根据一个节点获取整个知识图谱
	 * @serialData 2018.6.12
	 * @param mindNodeTool
	 * @param request
	 * @return
	 */
	@RequestMapping("getCompleteMap.do")
	@ResponseBody
	public String getCompleteMap(MindNodeTool mindNodeTool,
			HttpServletRequest request){
		
        String rootid = mindNodeTool.getRootid();
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", rootid);
		
		return mindMap.getData();
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  修改节点颜色
	 * @serialData 2018.6.12
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("setMapColor.do")
	@ResponseBody
	public String setMapColor(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String nodeid = String.valueOf(map.get("nodeid"));
		String color = String.valueOf(map.get("color"));
		String rootid = String.valueOf(map.get("rootid"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", rootid);
		String  maplist = mindMap.getMaplist();
		String  mindUser = mindMap.getUserid();
		
		List<MindNode> list = jsonAnalyze.parseList(maplist);
		
		//权限
		if (!userid.equals(mindUser)){
			return statusMap.a("3");
		}
		
		//改颜色
		for(int i=0; i<list.size(); i++){
    		MindNode mind = list.get(i);
    		
    		if( mind.getNodeid().equals(nodeid) ){
				mind.setColor(color);
				list.set(i, mind);
			}
    	}
		
		//更新数据
		String open = tryCatchMindMapService.openMind(list, rootid);

		mindMap.setMaplist(jsonAnalyze.list2Json(list));    //更新树
		mindMap.setData(open);                              //更新数据
		
		if(tryCatchNewMindService.updateMindMap(mindMap)){
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
		
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  保存节点拖动后的位置
	 * @serialData 2018.6.12
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("saveMapPosition.do")
	@ResponseBody
	public String saveMapPosition(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String beforeid=String.valueOf(map.get("beforeId"));
    	String afterid=String.valueOf(map.get("afterId"));
    	String rootid = String.valueOf(map.get("rootid"));
    	
    	HttpSession session=request.getSession();
    	String userid=String.valueOf(session.getAttribute("username"));
    	
    	if( userid.equals("null")||userid.equals(null) ){
    		return statusMap.a("2");
    	}
    	
    	
    	MindMap mindMap = tryCatchNewMindService.getMindMap("userid", userid, "nodeid", rootid);
		String  maplist = mindMap.getMaplist();
		String  mindUser = mindMap.getUserid();
		
		//权限
		if (!userid.equals(mindUser)){
			return statusMap.a("4");
		}
    	
		
		List<MindNode> list = jsonAnalyze.parseList(maplist);
    	
		//保存拖动后的位置
    	for(int i=0; i<list.size(); i++){
    		MindNode mind = list.get(i);
    		
    		if( mind.getNodeid().equals(beforeid) ){
				mind.setParentid(afterid);
				list.set(i, mind);
			}
    	}
    	
    	//更新数据
		String open = tryCatchMindMapService.openMind(list, rootid);
	
		mindMap.setMaplist(jsonAnalyze.list2Json(list));    //更新树
		mindMap.setData(open);                              //更新数据
		
		if(tryCatchNewMindService.updateMindMap(mindMap)){
			return statusMap.a("1");
		}
		return statusMap.a("10");
	}
	
	
}
