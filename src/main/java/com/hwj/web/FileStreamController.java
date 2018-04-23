package com.hwj.web;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Blob;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.FileStream;
import com.hwj.entity.UploadFile;
import com.hwj.entityUtil.BeSaveFileUitl;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.FileUpload;
import com.hwj.tools.StatusMap;
import com.hwj.tools.StreamToBlob;
import com.hwj.tools.TryCatchFileStreamService;
import com.hwj.tools.TryCatchUploadFileService;

@Controller
public class FileStreamController {

	@Autowired
    private StatusMap statusMap;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private TryCatchFileStreamService tryCatchFileStreamService;
	@Autowired
	private TryCatchUploadFileService tryCatchUploadFileService;
	@Autowired
	private FileUpload fileUpload;
	@Autowired
	private StreamToBlob streamToBlob;
	
	
	/**
	 * @author Ragty
	 * @param  资源页面回收站获取回收文件
	 * @serialData 2018.3.21
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getFileStream.do")
	@ResponseBody
	public String getFileStream(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		map.clear();
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		List<FileStream> list = tryCatchFileStreamService.getFileStream("userid", userid);
		
		// 在这里将异常抛出，因为blob字段类型被代理，不能序列化
		for (int i = 0; i < list.size(); i++) {
			FileStream fileStream = list.get(i);
			fileStream.setFileStream(null);
		}
		
		return jsonAnalyze.list2Json(list);
	}
	
	
	/**
	 * @author Ragty	
	 * @param  删除回收站中的文件流
	 * @serialData 2018.3.21
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/delFileStream.do")
	@ResponseBody
	public String delFileStream(@RequestBody String requestJsonBody,
			HttpServletRequest request) throws IOException{
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String f_id = String.valueOf(map.get("f_id"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		FileStream fileStream = tryCatchFileStreamService.getFileStream1(
				"f_id", "userid", f_id, userid);
		
		if(tryCatchFileStreamService.delFileStream(fileStream)){
			return statusMap.a("1");
		}
		
		return statusMap.a("2");
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  数据库恢复文件(根据流恢复文件)
	 * @serialData 2018.3.21
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
    @RequestMapping("/writreFileStream.do")
    @ResponseBody
    public String writreFileStream(@RequestBody String requestJsonBody,
    		HttpServletRequest request) throws IOException{
    	
    	
    	Map<String,Object> map = jsonAnalyze.json2Map(requestJsonBody);
		String f_id = String.valueOf(map.get("f_id"));
		String nodeid = String.valueOf(map.get("nodeid"));
		
		HttpSession session = request.getSession();
		String userid = String.valueOf(session.getAttribute("username"));
		
		FileStream fileStream = tryCatchFileStreamService.
				getFileStream1("userid", "f_id", userid, f_id);
		Blob blob = fileStream.getFileStream();
		byte[] QN = streamToBlob.blobToBytes(blob);
		String fileExtension = fileStream.getFileExtension();
    	
		String realPath = request.getSession().getServletContext()
				.getRealPath("/");
		String fileURL = realPath + "upload";
		
		BeSaveFileUitl be = new BeSaveFileUitl();
		be.setFileExtension(fileExtension);
		be.setFilesByte(QN);
		be.setFileURL(fileURL);
		
		String ip = InetAddress.getLocalHost().getHostAddress();
		
		
		String Url2 = "";
		String Url3 = "";
		String[] string = this.fileUpload.saveFile(be);
		
		//文件上传成功的话
		if ("1".equals(string[0])) {
			String URL = string[1];
			// 文件的真实路径，将之代替截图路径存入
			String url = URL.replaceAll("\\\\", "/") + "." + fileExtension;
			String real = realPath.replaceAll("\\\\", "/");
			
			Url3 = url; // 文件在服务器中的真实路径，用来删除
			Url2 = "http://"  + ip + ":8080/"+ url.split(real)[1]; //代表服务器中的地址

		}
		
		UploadFile uploadFile = new UploadFile();
		uploadFile.setFilename(fileStream.getFilename());
		uploadFile.setFiles(f_id); // 文件id
		uploadFile.setFilepath(Url2); // 文件的输出路径（在服务器上的访问路径）
		uploadFile.setFileroot(Url3); // 文件在pc上的实际路径
		uploadFile.setZlms("该资料现在没有描述");
		uploadFile.setFiletype(fileStream.getFileType());
		uploadFile.setUploadtime(fileStream.getUploadTime());
		uploadFile.setUserid(userid);
		uploadFile.setF_parentid(fileStream.getParentid());
		uploadFile.setFirstStatus("1");
		
		if (!uploadFile.equals("null")) {
			uploadFile.setZsdid(nodeid);
		}
		
		
		// 防止恢复数据后,被恢复的文件依旧在回收站中
		fileStream.setDelStatus("0");
		tryCatchFileStreamService.updateFileStream(fileStream);
		
		if(tryCatchUploadFileService.saveUploadFile(uploadFile)){
			return statusMap.a("1");
		}
		
    	return statusMap.a("2");
    }
	

    
}
