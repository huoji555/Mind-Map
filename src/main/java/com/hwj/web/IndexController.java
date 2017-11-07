package com.hwj.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.student;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchStudentService;

@Controller
public class IndexController {
	
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap StatusMap;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	
	
	@RequestMapping(value="/")
	public String index(){
		return "login";
	}

	
	@RequestMapping("saveStudent.do")
	@ResponseBody
	public  String  saveStudent(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		
		String name=String.valueOf(map.get("name"));
		String password=String.valueOf(map.get("password"));
		
		HttpSession session=request.getSession();
		String uid=String.valueOf(session.getAttribute("name"));
		
		System.out.println("Session"+uid);
		
		student student=new student();
		student.setName(name);
		student.setPassword(password);
		
		if( this.tryCatchStudentService.saveStudent(student) ){
			return StatusMap.a("1");
		}else{
			return StatusMap.a("2");
		}

	}
	
	
	@RequestMapping("/getId.do")
	@ResponseBody
	public String getId(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		
		String name=String.valueOf(map.get("name"));
		
		student student=tryCatchStudentService.findByName("name", name);
		
		System.out.println(student);
		System.out.println(student.getId());
		
		Map<String, Object> map1=new HashMap<String, Object>();
		
		map1.put("id",student.getPassword());
		
	    return jsonAnalyze.map2Json(map1);
	
	} 
	
	
	
	
}
