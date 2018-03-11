package com.hwj.web;

import java.io.IOException;
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
import com.hwj.entity.UploadFile;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFileShareService;
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
	  
	  
	  
	  
	
	
	
}
