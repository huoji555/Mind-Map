package com.hwj.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.expr.NewArray;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.FileCollection;
import com.hwj.entity.UploadFile;
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
	@RequestMapping("/getCollectionFile.do")
	@ResponseBody
	public String getCollectionFile(HttpServletRequest request)
			throws IOException {

		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));

		List<FileCollection> list = tryCatchFileCollectionService
				.getFileCollection("userid", userid);

		System.out.println(list + "huoji555");
		if (list == null || list.equals(null)) {

			return statusMap.a("2");
		}

		for (int i = 0; i < list.size(); i++) {

			FileCollection fileCollection1 = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				FileCollection fileCollection2 = list.get(j);

				if (fileCollection1.getF_id().equals(fileCollection2.getF_id())) {
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
	public String collectionDelete(@RequestBody String reuqestBody,
			HttpServletRequest request) throws IOException {

		// 用来取请求体中的数据
		Map<String, Object> map = jsonAnalyze.json2Map(reuqestBody);
		String f_id = String.valueOf(map.get("f_id"));
		String nodeid = String.valueOf(map.get("nodeid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		
		FileCollection fileCollection = new FileCollection();
		
		//不同情况,分别对待
        if(nodeid.equals("null")){
        	System.out.println("到这里了");
        	fileCollection =tryCatchFileCollectionService.
        			getFileCollection1("userid", userid, "f_id", f_id);
        } else {
        	fileCollection = tryCatchFileCollectionService
    				.getFileCollection("userid", userid, "f_id", f_id, "nodeid",
    						nodeid);
        }
		
		System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$"+fileCollection);

		if (tryCatchFileCollectionService.delFileCollection(fileCollection)) {
			return statusMap.a("1");
		} else {
			return statusMap.a("2");
		}

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
	@RequestMapping("/search1.do")
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
	
	
	
	/**
	 * @author Ragty
	 * @param  节点上删除收藏文件
	 * @serialData 2018.3.21
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delCollectUpload.do")
	@ResponseBody
	public String delCollectUpload(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String zlid = String.valueOf(map.get("zlid"));
		String nodeid = String.valueOf(map.get("nodeid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		FileCollection fileCollection = tryCatchFileCollectionService.
				getFileCollection("userid", userid, "nodeid", nodeid,"f_id", zlid);
		
		if(tryCatchFileCollectionService.delFileCollection(fileCollection)){
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
		
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.3.21
	 * @param 显示节点上的收藏文件接口
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getCollectUpload.do")
	@ResponseBody
	public String getCollectUpload(@RequestBody String requestJsonBody,
			HttpServletRequest request ) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String nodeid = String.valueOf(map.get("nodeid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		List<FileCollection> list = tryCatchFileCollectionService.
				getFileCollection("userid", userid, "nodeid", nodeid);
		List<Map<String, String>> list2 =new ArrayList<Map<String,String>>();
		
		String states = null;
		
		if((list == null )|| (list.equals(null))){
			states = "1";
			return statusMap.a("3");
		} else {
			states = "0";
			
			for(int i = 0; i < list.size(); i++){
				
				FileCollection fileCollection = list.get(i);
				Map<String, String> map1 = new HashMap<String, String>();
				
				String type = fileCollection.getFileType();
				
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
				
				map1.put("zlmc", fileCollection.getF_name());
				map1.put("tubiao", tubiao);
				map1.put("zlms", "无任何描述");
				map1.put("zlid", String.valueOf(fileCollection.getF_id()));
				map1.put("filepath", fileCollection.getFilePath());
				map1.put("time", fileCollection.getUploadTime());
				map1.put("fileType", fileCollection.getFileType());
				
				if(list2.size() < list.size()){
					list2.add(map1);
				} 
				
			}
		}
		
		Map<String, Object> map2 =new HashMap<String, Object>();
		map2.put("state", states);
		map2.put("list2", list2);
		map2.put("nodeid", nodeid);
		
		return this.jsonAnalyze.map2Json(map2);
		
	}
	
	
	
}
