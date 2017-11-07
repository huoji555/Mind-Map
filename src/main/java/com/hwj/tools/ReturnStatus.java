/*
 *@包名：com.hwj.tools        
 *@文档名：ReturnStatus.java 
 *@功能：状态返回  
 *@作者：李振强        
 *@创建时间：2014.5.14
 *@版权：河北北方学院信息技术研究所 
 */
package com.hwj.tools;

import org.springframework.stereotype.Component;

@Component
public class ReturnStatus {
	public String Success;
	public String Fail;
	public String NotHaveUser;
	public String CannotAnalyzeData;
	public String CardIDError;
	public String PhoneError;
	public String OldPasswordError;
	public String RoleError;
	public String PasswordError;
	public String LoadingNewsError;
	public String UserNicknameRepeat;
	public String UserNameError;
	public String Auditing;
	public String NicknameCodedError;
	public String NameCodedError;
	public String verifyCodeError;
	public String student;
	public String teacher;
	public String admin;

	public ReturnStatus() throws Exception {
		this.init();
	}

	/*
	 * @方法名： init()
	 * 
	 * @功能：实例化返回状态
	 * 
	 * @功能说明：实例化返回状态
	 * 
	 * @作者：李振强
	 * 
	 * @创建时间：2014.5.14
	 * 
	 * @修改时间：2014.5.14
	 */
	private void init() throws Exception {
		StatusMap statusMap = new StatusMap();
		Success = statusMap.status("Success");
		Fail = statusMap.status("Fail");
		NotHaveUser = statusMap.status("NotHaveUser");
		CannotAnalyzeData = statusMap.status("CannotAnalyzeData");
		CardIDError = statusMap.status("CardIDError");
		PhoneError = statusMap.status("PhoneError");
		OldPasswordError = statusMap.status("OldPasswordError");
		RoleError = statusMap.status("RoleError");
		PasswordError = statusMap.status("PasswordError");
		LoadingNewsError = statusMap.status("LoadingNewsError");
		UserNicknameRepeat = statusMap.status("UserNicknameRepeat");
		UserNameError = statusMap.status("UserNameError");
		Auditing = statusMap.status("Auditing");
		NicknameCodedError = statusMap.status("NicknameCodedError");
		NameCodedError = statusMap.status("NameCodedError");
		verifyCodeError = statusMap.status("verifyCodeError");
		student = statusMap.status("student");
		teacher = statusMap.status("teacher");
		admin = statusMap.status("admin");
	}
}
