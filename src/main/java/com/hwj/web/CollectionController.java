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

import com.hwj.entity.FileCollection;
import com.hwj.entity.UploadFile;
import com.hwj.entity.student;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchFileCollectionService;
import com.hwj.tools.TryCatchUploadFileService;

@Controller
public class CollectionController {

	@Autowired
	private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private TryCatchUploadFileService tryCatchUploadFileService;
	@Autowired
	private TryCatchFileCollectionService tryCatchFileCollectionService;
	
	
	/**
	 * @author Ragty
	 * @param  资源界面显示收藏文件接口
	 * @serialData 2018.3.13
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("getCollectionFile.do")
	@ResponseBody
	public String getCollectionFile(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		List<FileCollection> list = tryCatchFileCollectionService.getFileCollection("userid", userid);
		
		if(list==null || list.equals(null)){
			return statusMap.a("2");
		}
		
		//去重
		for(int i=0; i<list.size(); i++){
			FileCollection fileCollection = list.get(i);
			for(int j=i+1; j<list.size(); j++){
				FileCollection fileCollection2 = list.get(j);
				if(fileCollection.getF_id() == fileCollection2.getF_id()){
					list.remove(j);
				}
			}
			
		}
		
		return jsonAnalyze.list2Json(list);
		
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  删除资源页面的收藏文件(也可删除节点上收藏的文件)
	 * @serialData 2018.3.20
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/collectionDelete.do")
   	@ResponseBody
   	public String collectionDelete(@RequestBody String requestJsonBody, HttpServletRequest request) throws IOException{
		
        Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
        
        String f_id = String.valueOf(map.get("f_id"));
		String nodeid = String.valueOf(map.get("nodeid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		FileCollection fileCollection = tryCatchFileCollectionService.
				getFileCollection("userid", userid, "nodeid", nodeid, "f_id", f_id);
		
		if(tryCatchFileCollectionService.delFileCollection(fileCollection)){
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
				
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.3.20
	 * @param  资源页面收藏文件接口(已防止同一文件重复上传)
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveCollectionFile.do")
	@ResponseBody
	public String saveCollectionFile(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String f_id = String.valueOf(map.get("f_id"));
		String nodeid = String.valueOf(map.get("nodeid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		UploadFile uploadFile = tryCatchUploadFileService.
				getUploadFile("userid", "zsdid", "files", userid, nodeid, f_id);
		
		if(tryCatchFileCollectionService.
				haveFileCollection("userid", userid, "f_id", f_id )){
			return statusMap.a("3");
		}
		
		FileCollection fileCollection = new FileCollection();
		fileCollection.setId(uploadFile.getNo());
		fileCollection.setF_id(uploadFile.getFiles());
		fileCollection.setF_name(uploadFile.getFilename());
		fileCollection.setFilePath(uploadFile.getFilepath());
		fileCollection.setFileRoot(uploadFile.getFileroot());
		fileCollection.setFileType(uploadFile.getFiletype());
		fileCollection.setUploadTime(uploadFile.getUploadtime());
		fileCollection.setUserid(uploadFile.getUserid());
		
		
		if(tryCatchFileCollectionService.saveFileCollection(fileCollection)){
			return statusMap.a("1");
		}
		
           return statusMap.a("2");
	}
	
	
	
	/**
	 * @author Ragty
	 * @serialData 2017.9.20
	 * @param 资源界面搜索功能(this is piece of shit)
	 * @param requestbody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/search.do")
	@ResponseBody
	public String search(@RequestBody String requestbody,
			HttpServletRequest request) throws IOException {

		Map<String, Object> map = jsonAnalyze.json2Map(requestbody);
		HttpSession session = request.getSession();
		String nickName = String.valueOf(session.getAttribute("username"));
		String number = String.valueOf(map.get("number"));
		String message = String.valueOf(map.get("message"));
		String fileType = "";

		System.out.println(message+ "!!!!!!!!!!!!!!!!!!!!!!!");
		
		// 用switch语句也可以执行
		if (number.equals("1")) {
			fileType = "doc";
		}else if (number.equals("2")) {
			fileType = "video";
		}else if (number.equals("3")) {
			fileType = "file";
		}else if (number.equals("4")) {
			fileType = "other";
		}else if (number.equals("0")) {
			List<UploadFile> list0 = tryCatchUploadFileService.getUploadFile("userid", nickName);
			return jsonAnalyze.list2Json(list0);
		}
		
		System.out.println(fileType);
		
		//UploadFile uploadFile=tryCatchUploadFileService.getUploadFile("userid","filetype", "filename", nickName, fileType, message);
		List<UploadFile> list2=tryCatchUploadFileService.getUploadeFile("userid", nickName, "filetype", fileType);
		
		int i=0;
		while(i<list2.size()){
			UploadFile uploadFile=list2.get(i);
			i++;
			System.out.println(uploadFile.getFilename()+"@@@@@@@@@@"+message);
			if(uploadFile.getFilename().equals(message)){
				System.out.println(uploadFile+"sds");
				return jsonAnalyze.object2Json(uploadFile);
			}
		}
		
		return jsonAnalyze.list2Json(list2);

	}	
	
	
	/**
	 * @author Ragty
	 * @param  节点上收藏资源文件(已防止重复上传)
	 * @serialData 2018.3.20
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/saveCollectUpload.do")
	@ResponseBody
	public String saveCollectUpload(@RequestBody String requestJsonBody, 
			HttpServletRequest request) throws IOException{

		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String zlid = String.valueOf(map.get("zlid"));
		String nodeid = String.valueOf(map.get("nodeid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		UploadFile uploadFile = this.tryCatchUploadFileService.getUploadFile(
				"userid", "files", "zsdid", userid, zlid, nodeid);
		
		String filename = null;
		String zsdid = null;
		
		try {
			FileCollection fileCollection1 = tryCatchFileCollectionService
					.getFileCollection("userid", userid, "f_id", zlid,
							"nodeid", nodeid);
			filename = fileCollection1.getF_name();
			zsdid = fileCollection1.getNodeid();
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		if (tryCatchFileCollectionService.haveFileCollection("userid", userid,
				"f_name", filename)) {

			if (zsdid == null) {
				FileCollection fileCollection = tryCatchFileCollectionService
						.getFileCollection("userid", userid, "f_id", zlid,
								"nodeid", nodeid);
				fileCollection.setNodeid(nodeid);

				FileCollection fileCollection1 = tryCatchFileCollectionService
						.getFileCollection("userid", userid, "f_id", zlid,
								"nodeid", nodeid);   //用来存原资源页面收藏过的文件
				fileCollection1.setNodeid("1");

				if (tryCatchFileCollectionService
						.saveFileCollection(fileCollection)) {
					System.out.println("已经在资源界面收藏过该文件");
					tryCatchFileCollectionService
							.updateFileCollection(fileCollection1);
					return statusMap.a("1");
				} else {
					return statusMap.a("2");
				}
			}
			if (zsdid != nodeid) {
				FileCollection fileCollection = tryCatchFileCollectionService
						.getFileCollection1("userid", userid, "f_id", zlid);
				fileCollection.setNodeid(nodeid);

				if ((tryCatchFileCollectionService.haveFileCollection("f_id",
						zlid, "nodeid", nodeid))) {
					System.out.println("防止节点上重复收藏一个文件");
					return statusMap.a("3");
				}

				if (tryCatchFileCollectionService
						.saveFileCollection(fileCollection)) {
					System.out.println("在新节点收藏该文件");
					return statusMap.a("1");
				} else {
					return statusMap.a("2");
				}

			}

		}
		
		FileCollection fileCollection = new FileCollection();
		fileCollection.setId(uploadFile.getNo());
		fileCollection.setF_id(zlid);
		fileCollection.setF_name(uploadFile.getFilename());
		fileCollection.setFilePath(uploadFile.getFilepath());
		fileCollection.setFileRoot(uploadFile.getFileroot());
		fileCollection.setFileType(uploadFile.getFiletype());
		fileCollection.setNodeid(uploadFile.getZsdid());
		fileCollection.setUploadTime(uploadFile.getUploadtime());
		fileCollection.setUserid(userid);
		
		if(tryCatchFileCollectionService.saveFileCollection(fileCollection)){
			System.out.println("第一次收藏该文件");
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
		
	}
	
	
	
	
	
	
}
