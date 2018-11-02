package com.hwj.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hwj.entity.LoginRecord;
import com.hwj.service.LoginRecordService;
import com.hwj.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.google.common.collect.Maps;
import com.hwj.entity.Admin;
import com.hwj.service.AdminService;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private AdminService adminService;
	@Autowired
    private LoginRecordService loginRecordService;
	@Autowired
    private PBKDF2Util pbkdf2Util;
	@Autowired
	private JsonAnalyze jsonAnalyze;
	@Autowired
	private SendMail sendMail;
	
	public String verifyCode = "";
	
	
	/**
	 * @author Ragty
	 * @describe 注册接口
	 * @param requestJsonBody
	 * @param request
	 * @return
	 * @date   2018.10.16
	 * @throws IOException
	 */
	@PostMapping("/register")
	public ResultBean<Map<String, Object>> register(@RequestBody String requestJsonBody, HttpServletRequest request) throws Exception {
		
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);
		Map<String, Object> result = Maps.newHashMap();
		
		String userName = String.valueOf(map.get("username"));
		String password = String.valueOf(map.get("password"));
		String email = String.valueOf(map.get("email"));
		String verfiyCodeFront = String.valueOf(map.get("verfiyCode"));


		//验证码暂且不提,判断前台返回的验证码和手机生成的验证码是否相同
        if(!verfiyCodeFront.equals(verifyCode)){
            result.put("status",201);
            result.put("message","验证码不正确");
            return new ResultBean<Map<String,Object>>(result);
        }
        
        Admin admin = new Admin();
        admin = adminService.queryAdminByUsernameOrEmail(userName, "");
        
        if(admin != null){
            result.put("status",201);
            result.put("message","该用户名已被注册过");
            return new ResultBean<Map<String,Object>>(result);
        }
        
        admin = adminService.queryAdminByUsernameOrEmail("", email);
        
        if(admin != null){
            result.put("status",201);
            result.put("message","该邮箱已被注册过");
            return new ResultBean<>(result);
        }
        
        Admin admin1 = new Admin();
        Date createDate = new Date();
        String ip = "";

        try {
           ip = getClientIp(request);
        }catch (Exception e){
            e.printStackTrace();
        }

        String salt = pbkdf2Util.generateSalt();   //加盐

        admin1.setUsername(userName);
        admin1.setPassword(pbkdf2Util.getEncryptedPassword(password,salt));    //慢哈希比较
        admin1.setEmail(email);
        admin1.setRoleId(2);
        admin1.setIp(ip);
        admin1.setCreateDate(createDate);
        admin1.setSalt(salt);
        
        adminService.save(admin1);
        saveLoginRecord(userName,2,request);
        
        HttpSession session = request.getSession();
        session.setAttribute("admin", userName);
        session.setAttribute("roleId","2");
        session.setMaxInactiveInterval(6*60*200);
        
        result.put("status",200);
        result.put("message","注册成功");
        verifyCode = "";                        //将验证码再次置空，防止重复利用一个验证码
        
        return new ResultBean<Map<String,Object>>(result);
	}




    /**
     * @author Ragty
     * @Describe 登录接口
     * @param admin
     * @param request
     * @Date  2018.10.17
     * @return
     */
	@PostMapping("/login")
    public ResultBean<Map<String,Object>> login(@RequestBody Admin admin, HttpServletRequest request) throws Exception{

	    Map<String,Object> result = Maps.newHashMap();
	    String username = admin.getUsername();
	    String password = admin.getPassword();
	    String email = admin.getEmail();

        if (email != null) {
            username = adminService.queryAdminByUsernameOrEmail("",email).getUsername();
        }

        Admin admin1 = adminService.queryAdminByUsernameOrEmail(username,"");
        String salt = admin1.getSalt();
        password = pbkdf2Util.getEncryptedPassword(password,salt);

	    int a = 0;
	    a = adminService.hasMatchAdmin(username,password);

        if(a == 0){
            result.put("status",201);
            result.put("message","用户不存在");
            return new ResultBean<Map<String,Object>>(result);
        } else if (a == 2) {
            result.put("status",201);
            result.put("message","用户名或密码错误");
            return new ResultBean<Map<String,Object>>(result);
        }

        int roleId = admin1.getRoleId();
        Date currentDate = new Date();

        HttpSession session = request.getSession();
        session.setAttribute("admin", username);
        session.setAttribute("roleId",roleId);
        session.setMaxInactiveInterval(6*60*200);

        saveLoginRecord(username,roleId,request);

        result.put("status",200);
        result.put("message","登录成功");      //登录成功后，需要判断他的权限（同时加个session）
        result.put("roleId",roleId);

        return new ResultBean<Map<String,Object>>(result);
    }




    /**
     * @author Ragty
     * @Describe 退出接口
     * @param request
     * @Date  2018.10.17
     * @return
     */
    @PostMapping("/logOut")
    public ResultBean<Map<String,Object>> logOut(HttpServletRequest request) {

	    Map<String,Object> result = Maps.newHashMap();
	    HttpSession session = request.getSession();
	    session.invalidate();

        result.put("status",200);
        result.put("message","退出成功");
        return new ResultBean<Map<String,Object>>(result);
    }




    /**
     * @auther: Ragty
     * @describe: 判断用户是否登录
     * @param: [request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/10/26 
     */
    @PostMapping("/ifLogin")
    public ResultBean<Map<String,Object>> ifLogin(HttpServletRequest request) {
        Map<String,Object> result = Maps.newHashMap();
        HttpSession session = request.getSession();
        String adminId = String.valueOf(session.getAttribute("admin"));
        String roleId = String .valueOf(session.getAttribute("roleId"));


        if (adminId.equals("null") || adminId == "null"){
            result.put("status",201);
            result.put("roleId","");
            result.put("adminId","");
            result.put("message","未登录，非法操作");
            return new ResultBean<>(result);
        }

        result.put("status",200);
        result.put("roleId",roleId);
        result.put("adminId",adminId);
        result.put("message","已登录");
        return new ResultBean<>(result);

    }




    /**
     * @auther: Ragty
     * @describe: 修改密码接口
     * @param: [orignalPwd, newPwd, request]
     * @return: com.hwj.util.ResultBean<java.util.Map<java.lang.String,java.lang.Object>>
     * @date: 2018/10/26
     */
    @GetMapping("/updatePwd")
    public ResultBean<Map<String,Object>> updatePwd(@RequestParam String orignalPwd, @RequestParam String newPwd,
                                                    HttpServletRequest request) throws Exception{

        Map<String,Object> result = Maps.newHashMap();
        HttpSession session = request.getSession();
        String adminId = String.valueOf(session.getAttribute("admin"));

        Admin admin = adminService.queryAdminByUsernameOrEmail(adminId,"");
        String salt = admin.getSalt();
        String password = pbkdf2Util.getEncryptedPassword(orignalPwd.trim(),salt);
        String ip = "";
        int a = 0;

        a = adminService.hasMatchAdmin(adminId,password);

        if (a == 2) {
            result.put("status",201);
            result.put("message","原密码不正确");
            return new ResultBean<>(result);
        }

        try {
            ip = getClientIp(request);
        }catch (Exception e){
            e.printStackTrace();
        }

        String newSalt = pbkdf2Util.generateSalt();

        admin.setPassword(pbkdf2Util.getEncryptedPassword(newPwd.trim(),newSalt));
        admin.setSalt(newSalt);
        adminService.save(admin);

        result.put("status",200);
        result.put("message","修改成功");
        return new ResultBean<>(result);
    }




	/**
	 * @author Ragty
	 * @Describe 发送邮件接口
	 * @Date  2018.10.16
	 * @param requestJsonBody
	 * @return
	 * @throws IOException 
	 */
	@PostMapping("/verifyCode")
	public ResultBean<Map<String, Object>> verifyCode(@RequestBody String requestJsonBody) throws Exception{
		Map<String, Object> result = Maps.newHashMap();
		Map<String, Object> map = jsonAnalyze.json2Map(requestJsonBody);

		String username = String.valueOf(map.get("username"));
		String email = String.valueOf(map.get("email"));


		Admin admin0 = new Admin();
		admin0 = adminService.queryAdminByUsernameOrEmail(username,"");
        if(admin0 != null){
            result.put("status",201);
            result.put("message","该用户名已被注册过");
            return new ResultBean<>(result);
        }


		Admin admin = new Admin();
		admin = adminService.queryAdminByUsernameOrEmail("",email);
        if(admin != null){
            result.put("status",201);
            result.put("message","该邮箱已被注册过");
            return new ResultBean<>(result);
        }


		verifyCode = createCode();
		sendMail.send(email, "【Mind Map】", "验证码："+verifyCode+",您正在绑定邮箱，验证码告知他人将导致数据信息被盗，请勿泄露");
		
		result.put("status",200);
        result.put("message","请求成功");
        return new ResultBean<Map<String, Object>>(result);
	}
	
	
	
	/**
	 * @author Ragty
	 * @Describe 获取客户端IP
	 * @Date  2018.10.16
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public String getClientIp(HttpServletRequest request) throws Exception{

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

        return ip;
    }
	
	
	
	//生成验证码
    public static String createCode(){
        Random random = new Random();
        String result="";
        for (int i=0;i<6;i++)
        {
            result += random.nextInt(10);
        }
        return result;
    }



    /**
     * @auther: Ragty
     * @describe: 保存用户登录信息
     * @param: [username, roleId, request]
     * @return: void
     * @date: 2018/11/1
     */
    public void saveLoginRecord (String username, Integer roleId, HttpServletRequest request) throws Exception{

	    LoginRecord loginRecord = new LoginRecord();
	    loginRecord.setUsername(username);
	    loginRecord.setRoleId(roleId);
	    loginRecord.setIp(getClientIp(request));
	    loginRecord.setLoginTime(new Date());

	    loginRecordService.save(loginRecord);
    }

}
