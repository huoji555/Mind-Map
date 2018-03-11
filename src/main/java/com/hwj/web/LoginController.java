package com.hwj.web;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.hwj.entity.AssessLog;
import com.hwj.entity.LoginUser;
import com.hwj.entity.RoleName;
import com.hwj.json.JsonAnalyze;
import com.hwj.tools.ReturnStatus;
import com.hwj.tools.SSHA;
import com.hwj.tools.StatusMap;
import com.hwj.tools.TryCatchAssessLogService;
import com.hwj.tools.TryCatchUserService;

@Controller
public class LoginController {
	
	@Autowired
	private ReturnStatus returnStatus;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private StatusMap statusMap;
	@Autowired
	private TryCatchUserService tryCatchUserService;
	@Autowired
	private SSHA ssha;
	@Autowired
	private TryCatchAssessLogService tryCatchAssessLogService;
	
	
	/**
	 * @author Ragty
	 * @param 用户登录验证接口
	 * @serialData 2018.3.5
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/loginCheck2.do")
	@ResponseBody
	public String loginCheck2(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		String username = String.valueOf(map.get("user"));
		String password = String.valueOf(map.get("password"));
		String verifyImage = String.valueOf(map.get("verifyImage"));
		
		//缓存中生成的验证码
		String result_verifyCode = request.getSession().getAttribute("verifyCode").toString();
		System.out.println(verifyImage + "@@@@@@@@@@@@@@@@@@@@@@"+ result_verifyCode);
		
		//判断验证码是否正确，不正确的话返回一个错误的状态
		if (!checkValidateCode(verifyImage, result_verifyCode)) {
			return this.returnStatus.verifyCodeError;
		}
		
		//判断登录信息(具体表现为是学生，管理员，用户名错误，密码错误)
		String pd = password;
		int isValidUser = this.tryCatchUserService.hasMatchUser(username, pd);
		
		
		if ((isValidUser == 1) || (isValidUser == 4) || (isValidUser == 5)){
			
			HttpSession session2 = request.getSession();

			//在这里设置持续登录的时间
			session2.setAttribute("username", username);
			session2.setMaxInactiveInterval(6*60*60);
			
			
			// 根据request获取客户端IP地址
			String ip = request.getHeader("x-forwarded-for");
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if ((ip == null) || (ip.length() == 0)
					|| ("unknown".equalsIgnoreCase(ip))) {
				ip = request.getRemoteAddr();
				if (ip.equals("127.0.0.1")) {
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ip = inet.getHostAddress();
				}
			}
			System.out.println("ip地址" + ip);			
			
			//获取用户登录时间
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			sDateFormat.setLenient(false);// 严格限制日期转换
			String logintime = sDateFormat.format(new Date());
			
			//将登录信息保存到数据库中
			AssessLog assessLog = new AssessLog();
			assessLog.setIpAddress(ip);
			assessLog.setLogintime(logintime);
			assessLog.setUsername(username);
			
			this.tryCatchAssessLogService.setAssessLog(assessLog);
			
			//根据状态值返回
			if (isValidUser == 4) {
				return this.returnStatus.admin;//管理员
			}
			if (isValidUser == 5) {
				return this.returnStatus.teacher;//教师端
			}
			return this.returnStatus.student;//学生端
			
		}
		
		//未登录成功返回的状态
		if (isValidUser == 2) {
			return this.returnStatus.NotHaveUser;
		}
		if (isValidUser == 3) {
			return this.returnStatus.PasswordError;
		}
		return this.returnStatus.Fail;
		
	}
	
	
	
	/**
	 *@author Ragty
	 *@param 验证用户是否登录 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/iflogin.do")
	@ResponseBody
	public String iflogin(HttpServletRequest request) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		Object nickname = session.getAttribute("username");
		map.put("nickname", nickname);
		map.put("status", Integer.valueOf(1));
		return this.jsonAnalyze.map2Json(map);
	}
	
	
	
	/**
	 * @author Ragty
	 * @param  退出登录操作
	 * @serialData 2018.3.5
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value="/quitLogin.do")
	@ResponseBody
	public String quitLogin(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession();
		session.invalidate();
		return statusMap.a("1");
	}
	
	
	/**
	 * @author Ragty
	 * @param  用户注册接口
	 * @serialData 2018.3.5
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/register2.do")
	@ResponseBody
	public String register(@RequestBody String requestJsonBody,HttpServletRequest request) throws IOException{
		
		Map<String, Object> map=jsonAnalyze.json2Map(requestJsonBody);
		String nickName = String.valueOf(map.get("nickName"));
		String password = String.valueOf(map.get("password"));
		String phoneNumber = String.valueOf(map.get("phoneNumber"));
		String email = String.valueOf(map.get("email"));
		String realName=String.valueOf(map.get("realName"));
		String verifyImage = String.valueOf(map.get("verifyImage"));
		String role = String.valueOf(map.get("role"));
		String result_verifyCode = request.getSession()
				.getAttribute("verifyCode").toString();
		
		//判断输入的验证码是否和系统中的验证码相同
		if (!checkValidateCode(verifyImage, result_verifyCode)) {
			 return this.statusMap.a("1");
		}
		
		//判断注册的用户名是否用过
		if (this.tryCatchUserService.getUserByNickname(nickName) != null) {
			return this.statusMap.a("2");
		}
		
		//将注册信息写入数据库
		LoginUser loginUser = new LoginUser();
		System.out.println("@@@@@@@@@   这里是注册接口    @@@@@@@@@");
		loginUser.setNickName(nickName);
		loginUser.setPassword(password);
		loginUser.setPhoneNumber(phoneNumber);
		loginUser.setEmail(email);
		loginUser.setRealName(realName);
		loginUser.setRoleId(2);  //默认权限设置为2
		
		System.out.println("@@@@@@@@@"+loginUser);
		
		if (this.tryCatchUserService.saveUser(loginUser)) {
			System.out.println("成功保存注册信息");
			return this.statusMap.a("3");
		}
		
		return this.statusMap.a("4");
		
	}
	
	
	
	/**
	 * @author Ragty
	 * @param 判断输入的验证码是否和系统生成的验证码相同
	 * @serialData 2018.3.5
	 * @param verifyCode
	 * @param result_verifyCode
	 * @return
	 */
	protected boolean checkValidateCode(String verifyCode,
			String result_verifyCode) {
		if ((verifyCode == null)
				|| (!result_verifyCode.equalsIgnoreCase(verifyCode))) {
			return false;
		}
		return true;
	}
	
	
	
	

}
