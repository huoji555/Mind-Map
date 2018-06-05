package com.hwj.web;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSON;
import nl.captcha.Captcha;	
import nl.captcha.backgrounds.TransparentBackgroundProducer;
import nl.captcha.gimpy.DropShadowGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.DefaultWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hwj.entity.student;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchStudentService;
import com.hwj.tools.TryCatchUserService;

@Controller
public class IndexController {
	
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap StatusMap;
	@Autowired
	private TryCatchStudentService tryCatchStudentService;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	
	
	/**
	 * @author Ragty
	 * @serialData 2017.11.8
	 * @param  用来映射首页面
	 * @return
	 */
	@RequestMapping(value={"/","index.do"})
	public String index(){
		return "index";
	}

	
	
	/**
	 * @author Ragty
	 * @param 映射注册页面
	 * @serialData 2018.2.4
	 * @return
	 */
	@RequestMapping(value="register.do")
	public String register(){
		return "register";
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2018.2.4
	 * @param 映射登陆界面
	 * @return
	 */
	@RequestMapping(value="testLogin.do")
	public String testLogin(){
		return "testLogin";
	}
	
	
	/**
	 * @author Ragty
	 * @serialData 2017.11.8
	 * @param  映射学生个人信息界面
	 * @return
	 */
    @RequestMapping(value="stuinfo.do")
    public String stuinfo(){
    	return "stuinfo";
    }
    
    
    /**
     * @author Ragty
     * @param  映射权限不足页面
     * @serialData  2017.11.8
     * @return
     */
    @RequestMapping({ "/access.do" })
	public String access() {
		return "access";
	}

    /**
     * @author Ragty
     * @param  映射知识图谱聊天界面
     * @serialData 2018.6.5
     * @return
     */
    @RequestMapping(value = "mindChat.do")
    public String mindChat(){
    	return "mindChat";
    }
    
    
    /**
     * @author Ragty
     * @param  映射管理员用户界面
     * @serialData 2017.11.8
     * @return 
     */
	@RequestMapping({ "/main.do" })
	public String main() {
		return "main";
	}

    
    /**
     * @author Ragty
     * @param 生成验证码
     * @serialData 2018.2.7
     * @param request
     * @param response
     */
	@RequestMapping({ "/getVerifyCode.do" })
	public void getVerifyMCode(Model model, HttpServletRequest request,
			HttpServletResponse response) {
		
		//字体颜色
		List<Color> colors = new ArrayList();
		colors.add(Color.GREEN);
		colors.add(Color.BLUE);
		colors.add(Color.ORANGE);
		colors.add(Color.RED);

		//字体型号
		List<Font> fonts = new ArrayList<Font>();
		fonts.add(new Font("Geneva", 2, 32));
		fonts.add(new Font("Courier", 3, 32));
		fonts.add(new Font("Arial", 1, 32));
		
		WordRenderer wordRenderer = new DefaultWordRenderer(colors, fonts);// 验证码文本生成器
		
		Captcha captcha = new Captcha.Builder(150, 50).addText(wordRenderer)
				.gimp(new DropShadowGimpyRenderer())
				.addBackground(new TransparentBackgroundProducer()).build();

		request.getSession().setAttribute("verifyCode", captcha.getAnswer());
		
		System.out.println("新版本验证码是@@@@@@"+captcha.getAnswer());
		
		CaptchaServletUtil.writeImage(response, captcha.getImage());
	}
    
	
	
	//以下只供测试使用
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
	
	
	@RequestMapping("/deleteStudent.do")
	@ResponseBody
	public String deleteStudent(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		String name=String.valueOf(map.get("name"));
		
		student student= tryCatchStudentService.findByName("name", name);

		student.setPassword("223");
		
		if(tryCatchStudentService.delete(student)){
	    	return StatusMap.a("1");
	    }
		
		return StatusMap.a("2");
		
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
