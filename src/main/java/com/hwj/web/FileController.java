package com.hwj.web;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.InetAddress;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import com.hwj.entity.FileStream;
import com.hwj.entity.UploadFile;
import com.hwj.entityUtil.BeSaveFileUitl;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.FileUpload;
import com.hwj.tools.StatusMap;
import com.hwj.tools.StreamToBlob;
import com.hwj.tools.TryCatchFileCollectionService;
import com.hwj.tools.TryCatchFileShareService;
import com.hwj.tools.TryCatchFileStreamService;
import com.hwj.tools.TryCatchUploadFileService;

@Controller
public class FileController {

	
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private FileUpload fileUpload;
	@Autowired
	private TryCatchUploadFileService tryCatchUploadFileService;
	@Autowired
	private TryCatchFileCollectionService tryCatchFileCollectionService;
	@Autowired
	private TryCatchFileShareService tryCatchFileShareService;
	@Autowired
	private TryCatchFileStreamService tryCatchFileStreamService;
	@Autowired
	private StreamToBlob streamToBlob;
	
	
	/**
	 * @author Ragty
	 * @param 映射资源页面
	 * @serialData 2018.3.7
	 * @return
	 */
	@RequestMapping(value = "view.do")
    public String view(){
    	return "view";
    }
	
	
	/**
	 * @author Ragty
	 * @param  映射资源收藏页面
	 * @serialData 2018.3.7
	 * @return
	 */
	@RequestMapping(value = "collection.do")
	public String collection(){
		 return "collection";
	}
	
	
	/**
	 * @author Ragty
	 * @param  映射资源分享界面
	 * @serialData 2018.3.7
	 * @return
	 */
	@RequestMapping( value = "fileShare.do")
	public String fileShare(){
		return "fileShare";
	}
	
	
	/**
	 * @author Ragty
	 * @param  映射资源搜索界面
	 * @serialData 2018.3.7
	 * @return
	 */
	@RequestMapping( value = "search.do")
	public String search(){
		return "search";
	}
	
	
	/**
	 * @author Ragty
	 * @param  映射资源高级搜索界面
	 * @serialData 2018.3.7
	 * @return
	 */
	@RequestMapping( value = "higher.do")
	public String higher(){
		return "higher";
	}
	
	
	/**
	 * @author Ragty
	 * @param  映射资源回收站
	 * @serialData 2018.3.7
	 * @return
	 */
	@RequestMapping(value = "delFound.do")
	public String delFound(){
		return "delFound";
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  获取资源页面当级文件
	 * @serialData 2018.3.6
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAllFile.do")
	@ResponseBody
	public String getAllFile(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws Exception {

		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String f_parentid = String.valueOf(map.get("f_parentid"));
		HttpSession session = request.getSession();
		Object userid = session.getAttribute("username");

		// 根据取到的部分数据，去数据库中获取整条数据
		List<UploadFile> list = tryCatchUploadFileService.getUploadeFile(
				"userid", userid, "f_parentid", f_parentid);
		System.out.println(list);
		if (list == null || list.equals(null)) {

			return statusMap.a("2");

		}

		//遍历取出的list，防止其中有相同文件(更加安全)
		for (int i = 0; i < list.size(); i++) {
			UploadFile uploadFile1 = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				UploadFile uploadFile2 = list.get(j);
				
				if (uploadFile1.getFiles().equals(uploadFile2.getFiles())) {
					list.remove(j);
				}
				
			}
		}
		return jsonAnalyze.list2Json(list);

	}
	
	
	/**
	 * @author Ragty
	 * @param  资源页面新建文件夹接口
	 * @serialData 2018.3.6
	 * @param requestJsonBody
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveAllFile.do")
	@ResponseBody
	public String saveAllFile(@RequestBody String requestJsonBody,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String f_parentid = String.valueOf(map.get("f_parentid"));
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		String fileType = "file";
		String filename = String.valueOf(map.get("f_name"));
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");

		String f_id = df.format(new Date());
		SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String f_uploadtime = df2.format(new Date());

		UploadFile uploadFile = new UploadFile();
		uploadFile.setFiles(f_id);
		uploadFile.setFilename(filename);
		uploadFile.setUploadtime(f_uploadtime);
		uploadFile.setFiletype(fileType);
		uploadFile.setUserid(userid);
		uploadFile.setF_parentid(f_parentid);

		if (tryCatchUploadFileService.saveUploadFile(uploadFile)) {
			return statusMap.a("1");
		}
		return statusMap.a("2");
	}
	
	
	
	/**
	 * @author Ragty
	 * @param 资源界面上传文件
	 * @serialData 2018.3.6
	 * @param parentid
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadallfile.do")
	@ResponseBody
	// 利用request_Param传值
	public String uploadallfile(
			@RequestParam("upload_parentid") String parentid,
			@RequestParam("wenjian") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 输出后只是一个编码
		System.out.println(file.getInputStream());

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		String f_uploadtime = df.format(new Date());
		SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmss");
		String f_id = df2.format(new Date());
		String f_name = file.getOriginalFilename();

		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		if (userid.equals("null") || userid.equals(null)) {
			return statusMap.a("2");
		}

		try {
			if (tryCatchUploadFileService.haveUploadFile("userid", "filename",
					userid, f_name)) {
				return statusMap.a("3");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 确定文件类型,然后进一步确定文件扩展名
		String fileType = "";

		String fileExtension = file
				.getOriginalFilename()
				.substring(file.getOriginalFilename().lastIndexOf(".") + 1,
						file.getOriginalFilename().length()).toLowerCase();

		if ((fileExtension.equals("bmp")) || (fileExtension.equals("drw"))
				|| (fileExtension.equals("dxf"))
				|| (fileExtension.equals("eps"))
				|| (fileExtension.equals("gif"))
				|| (fileExtension.equals("jpg"))
				|| (fileExtension.equals("png"))
				|| (fileExtension.equals("pcd"))
				|| (fileExtension.equals("pcx"))) {
			fileType = "picture";
		} else if ((fileExtension.equals("avi"))
				|| (fileExtension.equals("mpeg"))
				|| (fileExtension.equals("mpg"))
				|| (fileExtension.equals("dat"))
				|| (fileExtension.equals("ra")) || (fileExtension.equals("rm"))
				|| (fileExtension.equals("wmv"))
				|| (fileExtension.equals("mp4"))
				|| (fileExtension.equals("swf"))
				|| (fileExtension.equals("f4v"))) {
			fileType = "video";
		} else if ((fileExtension.equals("cd"))
				|| (fileExtension.equals("ogg"))
				|| (fileExtension.equals("mp3"))
				|| (fileExtension.equals("asf"))
				|| (fileExtension.equals("wma"))
				|| (fileExtension.equals("wav"))
				|| (fileExtension.equals("rm"))
				|| (fileExtension.equals("midi"))
				|| (fileExtension.equals("ape"))) {
			fileType = "doc";
		} else {
			fileType = "other";
		}

		String ip = InetAddress.getLocalHost().getHostAddress();
		System.out.println("ip" + ip);

		// 看文件大小
		byte[] newsPageByte = file.getBytes();
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");

		System.out.println("REALPATH" + realPath
				+ "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

		//String fileURL = realPath + "upload";
		String fileURL = "C:\\upload";
		
		// 存相对路径
		BeSaveFileUitl be = new BeSaveFileUitl();
		be.setFileExtension(fileExtension);
		be.setFilesByte(newsPageByte);
		be.setFileURL(fileURL);

		String Url2 = "";
		String Url3 = "";
		String[] string = this.fileUpload.saveFile(be);
		

		//代表文件上传成功
		if ("1".equals(string[0])) {
			String URL = string[1];
			// 文件的真实路径，将之代替截图路径存入
			String url = URL.replaceAll("\\\\", "/") + "." + fileExtension;
			String real = realPath.replaceAll("\\\\", "/");
			
			System.out.println("-------------------");
			System.out.println(url);
			System.out.println(real);
			System.out.println("-------------------");
			
			Url3 = url; // 文件在服务器中的真实路径，用来删除
			Url2 = "http://"  + ip + ":8080/"+ url.split("C:/")[1]; //代表服务器中的地址

		}

		ByteArrayInputStream bis = new ByteArrayInputStream(newsPageByte);

		Blob blob = streamToBlob.toBlob(file.getInputStream());

		FileStream fileStream = new FileStream();
		fileStream.setFilename(f_name);
		fileStream.setF_id(f_id);
		fileStream.setFileExtension(fileExtension);
		fileStream.setParentid("0");
		fileStream.setUserid(userid);
		fileStream.setTrueUrl(Url2);
		fileStream.setFileStream(blob);
		fileStream.setFileType(fileType);
		fileStream.setDelStatus("0");
		fileStream.setUploadTime(f_uploadtime);

		tryCatchFileStreamService.saveFileStream(fileStream);

		// 将上传文件的数据保存到数据库
		UploadFile uploadFile = new UploadFile();
		uploadFile.setFilename(f_name);
		uploadFile.setFiles(f_id); // 文件id
		uploadFile.setFilepath(Url2); // 文件的输出路径（在服务器上的访问路径）
		uploadFile.setFileroot(Url3); // 文件在pc上的实际路径
		uploadFile.setZlms("该资料现在没有描述");
		uploadFile.setFiletype(fileType);
		uploadFile.setUploadtime(f_uploadtime);
		uploadFile.setUserid(userid);
		uploadFile.setF_parentid(parentid);
		uploadFile.setFirstStatus("1");

		if (tryCatchUploadFileService.saveUploadFile(uploadFile)) {
			System.out.println("成功");
			return statusMap.a("1");
		}
		return statusMap.a("2");
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  删除资源界面上传的文件及文件夹
	 * @serialData 2018.3.6
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteallfile.do")
	@ResponseBody
	public String deleteallfile(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws Exception {
		System.out.println(requestJsonBody + "222222");
		// 根据部分数据获取到改文件的f_id 然后进行删除
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String f_id = String.valueOf(map.get("f_id"));
		HttpSession session = request.getSession();
		Object uid = session.getAttribute("username");

		UploadFile uploadFile = tryCatchUploadFileService.getUploadFile(
				"userid", "files", uid, f_id);

		// 删除文件夹
		if (uploadFile.getFiletype().equals("file")) {
			System.out.println("@@@@@@@@@@删除文件夹");
			if (tryCatchUploadFileService.deleteUploadFile(uploadFile)) {
				return statusMap.a("1");
			}
			System.out.println("删除文件夹失败");
			return statusMap.a("2");
		}

		String realPath = uploadFile.getFileroot();

		// 删除真实文件
		File file = new File(realPath);

		if (!file.exists()) {
			System.out.println("文件不存在");

		} else {
			System.out.println("文件存在");
			System.out.println("即将删除文件");
			file.delete();
			System.out.println("成功,已将文件删除");
		}

		// 将文件放入回收站（修改delStatus）
		try {
			FileStream fileStream = tryCatchFileStreamService.getFileStream1(
					"f_id", "userid", f_id, uid);

			if (!fileStream.equals("null")) {
				fileStream.setDelStatus("1");
				tryCatchFileStreamService.updateFileStream(fileStream);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		Boolean delUpload = tryCatchUploadFileService.delAllUploadFile(
				"userid", "files", uid, f_id);
		Boolean delCollection = tryCatchFileCollectionService
				.delAllFileCollection("userid", uid, "f_id", f_id);
		Boolean delShare = tryCatchFileShareService.delAllFileShare("userid",
				uid, "f_id", f_id);
		System.out.println(delCollection + " #### " + delShare + "  $$ "
				+ delUpload);

		if (delUpload || delCollection || delShare) {
			System.out.println("正常情况到这里");
			return statusMap.a("1");
		} else {
			System.out.println("zheli@@@@@@@@@@@@@");
			return statusMap.a("2");
		}

	}
	
	
	
}
