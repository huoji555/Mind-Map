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

import com.hwj.entity.FileShare;
import com.hwj.entity.MindMap;
import com.hwj.entity.UploadFile;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFileShareService;
import com.hwj.tools.TryCatchNewMindService;
import com.hwj.tools.TryCatchUploadFileService;

@Controller
public class FileShareController {

	  @Autowired
	  private  StatusMap statusMap;
	  @Autowired
	  private JsonAnalyze jsonAnalyze;
	  @Autowired
	  private TryCatchFileShareService tryCatchFileShareService;
	  @Autowired
	  private TryCatchUploadFileService tryCatchUploadFileService;
	  @Autowired
	  private TryCatchNewMindService tryCatchNewMindService;
	  
	  
	  /**
	   * @author Ragty
	   * @param  资源页面获取分享界面接口（再加一层过滤网）
	   * @serialData 2018.3.11
	   * @param requestJsonBody
	   * @param request
	   * @return
	   * @throws IOException
	   */
	  @RequestMapping("/getShareFile.do")
	  @ResponseBody
	  public String getShareFile(@RequestBody String requestJsonBody,HttpServletRequest	request) throws IOException{
		  
		  HttpSession session=request.getSession();
		  String userid	= String.valueOf(session.getAttribute("username"));
		  
		  List<FileShare> list=tryCatchFileShareService.getShareFile("userid", userid);
		  
		  if (userid.equals("null") || userid.equals(null)) {
				return statusMap.a("2");
			}
		  
		  if (list.equals(null)	||	list ==null ){
			  return statusMap.a("2");
		  }
		  
		  // 加一个过滤，只要是同一源文件，只显示一次（显示的是最初上传的那个）
		  for (int i = 0; i < list.size(); i++) {
		
			  FileShare fileShare1 = list.get(i);
		
			  for (int j = i + 1; j < list.size(); j++) {
				  FileShare fileShare2 = list.get(j);
		
				  if (fileShare1.getF_id().equals(fileShare2.getF_id())) {
					  list.remove(j);
				  }
			  }
		
		  }		  
		  
		  return jsonAnalyze.list2Json(list);
	  }
	  
	  
	  
	  /**
	   * @author Ragty
	   * @param  资源页面分享接口
	   * @serialData 2018.3.11
	   * @param requestJsonBody
	   * @param request
	   * @return
	   * @throws IOException
	   */
	  @RequestMapping("/saveShareFile.do")
	  @ResponseBody
	  public String saveShareFile(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		  
		  Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		  String f_id = String.valueOf(map.get("f_id"));
		  
		  HttpSession session=request.getSession();
		  String userid=String.valueOf(session.getAttribute("username"));
		  
		  UploadFile uploadFile = tryCatchUploadFileService.getUploadFile(
				     		"userid", "files", userid, f_id);
		  
		  //防止同同一文件重复分享
		  if( tryCatchFileShareService.haveFileShare("f_name", uploadFile.getFilename(), "userid", userid) ){
			  		return statusMap.a("3");
		  }
		  
		  FileShare fileShare = new FileShare();
		  fileShare.setId(uploadFile.getNo());
		  fileShare.setF_id(uploadFile.getFiles());
		  fileShare.setF_name(uploadFile.getFilename());
		  fileShare.setF_parentid(uploadFile.getF_parentid());
		  fileShare.setFilePath(uploadFile.getFilepath());
		  fileShare.setFileRoot(uploadFile.getFileroot());
		  fileShare.setFileType(uploadFile.getFiletype());
		  fileShare.setUploadTime(uploadFile.getUploadtime());
		  fileShare.setUserid(uploadFile.getUserid());
		  
		  if( tryCatchFileShareService.saveShareFile(fileShare) ){
			  return statusMap.a("1");
		  }
		  
		  return statusMap.a("2");
		  
	  }
	  
	  
	/**
	 * @author Ragty  
	 * @param 节点分享资源接口（已兼容）
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
 	  @RequestMapping("saveNodeFile.do")
	  @ResponseBody
	  public String saveNodeFile(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException {

		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String nodeid = String.valueOf(map.get("nodeid"));
		String rootid = String.valueOf(map.get("rootid"));
		String zlid = String.valueOf(map.get("zlid"));

		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		MindMap mindMap = tryCatchNewMindService.getMindMap("nodeid", rootid);
	    String mindUser = mindMap.getUserid();
	    
	    if (! (userid.equals(mindUser)) ){
			System.out.println("到这里,,,,,");
			return statusMap.a("5");
		}
	    

		UploadFile uploadFile = new UploadFile();
		uploadFile = this.tryCatchUploadFileService.getUploadFile("userid",
				"zsdid", "files", userid, nodeid, zlid);

		String zsdid = null;
		String filename = null;
		try {
			FileShare fileshare = tryCatchFileShareService.getFileShare(
					"userid", userid, "f_id", zlid);
			zsdid = fileshare.getNodeid();
			filename = fileshare.getF_name();
		} catch (Exception e) {
			// TODO: handle exception
		}

		System.out.println("*******" + zsdid);

		if (tryCatchFileShareService.haveFileShare("userid", userid, "f_name",
				filename)) {

			if (zsdid == null) {
				FileShare fileshare = tryCatchFileShareService.getFileShare(
						"userid", userid, "f_id", zlid);
				fileshare.setNodeid(nodeid);

				FileShare fileshare1 = tryCatchFileShareService.getFileShare(
						"userid", userid, "f_id", zlid);
				fileshare1.setNodeid("1"); //这个状态代表是资源页面分享的文件（资源页面分享后，第一次在节点上分享文件）

				if (tryCatchFileShareService.saveShareFile(fileshare)) {
					System.out.println("资源界面已经分享过了");
					tryCatchFileShareService.updateFileShare(fileshare1);
					return statusMap.a("1");
				} else {
					return statusMap.a("2");
				}

			} else if (zsdid != nodeid) {
				FileShare fileshare = tryCatchFileShareService.getFileShare(
						"userid", userid, "f_id", zlid);
				fileshare.setNodeid(nodeid);

				if (tryCatchFileShareService.haveFileShare("f_id", zlid,
						"nodeid", nodeid)) {
					System.out.println("防止同一节点分享同一文件");
					return statusMap.a("3");
				}

				if (tryCatchFileShareService.saveShareFile(fileshare)) {
					System.out.println("在新的节点分享");
					return statusMap.a("1");
				} else {
					return statusMap.a("2");
				}

			}

		}

		FileShare fileShare = new FileShare();
		fileShare.setId(uploadFile.getNo());
		fileShare.setF_name(uploadFile.getFilename());
		fileShare.setF_id(uploadFile.getFiles());
		fileShare.setFileType(uploadFile.getFiletype());
		fileShare.setFilePath(uploadFile.getFilepath());
		fileShare.setFileRoot(uploadFile.getFileroot());
		fileShare.setNodeid(nodeid);
		fileShare.setUploadTime(uploadFile.getUploadtime());
		fileShare.setUserid(userid);
		fileShare.setF_parentid("0");

		if (tryCatchFileShareService.saveShareFile(fileShare)) {
			System.out.println("第一次在该节点分享文件");
			return statusMap.a("1");
		}

		return statusMap.a("2");
	 }
	  
	  
 	  

 	  /**
 	   * @author Ragty
 	   * @param  节点上删除分享文件的接口
 	   * @serialData 2018.3.12
 	   * @param requestJsonBody
 	   * @param request
 	   * @return
 	   * @throws IOException
 	   */
 	  @RequestMapping("/delShareUpload.do")
 	  @ResponseBody
 	  public String delShareUpload(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
 		  
 		  Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
 		  String f_id = String.valueOf(map.get("f_id"));
 		  String nodeid = String.valueOf(map.get("nodeid"));
 		  String rootid = String.valueOf(map.get("rootid"));
 		  HttpSession session=request.getSession();
 		  String userid = String.valueOf(session.getAttribute("username"));
 		  
 		  MindMap mindMap = tryCatchNewMindService.getMindMap("nodeid", rootid);
 	      String mindUser = mindMap.getUserid();
 	    
 	      if (! (userid.equals(mindUser)) ){
 			  System.out.println("到这里,,,,,");
 			  return statusMap.a("5");
 		   }
 		  
 		  FileShare fileShare = this.tryCatchFileShareService.getFileShare(
 				 "userid", userid, "f_id", f_id, "nodeid", nodeid);
 		  
 		  if( tryCatchFileShareService.delShareFile(fileShare) ){
 			  
 			  return statusMap.a("1");
 		  }
 		  
 		  return statusMap.a("2");
 	  }
 	  

 	/**
 	 * @author Ragty
 	 * @serialData 2018.3.12
 	 * @param 节点上获取所有分享文件接口
 	 * @param requestJsonBody
 	 * @param request
 	 * @return
 	 * @throws IOException
 	 */
 	@RequestMapping("/getShareUpload.do")
	@ResponseBody
	public String getShareUpload(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
 		
 		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
 		String nodeid = String.valueOf(map.get("nodeid"));
 		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		String uploadzl = "success";
		String state = null;
		List<FileShare> list = tryCatchFileShareService.getShareFile(
				 "userid", userid, "nodeid", nodeid);
		
		List<Map<String, String>> list2 = new ArrayList<Map<String,String>>();
		
		if ((list == null) || list.equals(null)){
			state = "1";
		} else {
			state = "0";
			
			for( int i=0; i<list.size(); i++){
				FileShare fileShare = list.get(i);
				Map<String, String> map1 = new HashMap<String, String>();
				
				String type = fileShare.getFileType();
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
				
				
				map1.put("zlmc", fileShare.getF_name());
				map1.put("tubiao", tubiao);
				map1.put("zlms", "暂无任何描述"); // 资料描述
				map1.put("zlid", String.valueOf(fileShare.getF_id()));
				map1.put("filepath", fileShare.getFilePath());
				map1.put("time", fileShare.getUploadTime());
				map1.put("fileType", fileShare.getFileType());
				
				if (list2.size() < list.size()){
					list2.add(map1);
				}
			}
		}
 		
		Map<String, Object> map2 = new HashMap<String, Object>();
		map2.put("uploadzl", uploadzl);
		map2.put("state", state);
		map2.put("list2", list2);
		map2.put("nodeid", nodeid);
 		
 		return jsonAnalyze.map2Json(map2);
 		
 	}
	
	
}
