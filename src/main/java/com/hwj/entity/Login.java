package com.hwj.entity;

/**
 * @author Ragty
 * @see    登陆表
 * time    2018.1.15
 */

public class Login {
	private String userName;
	private String passWord;
	private String verifyCode;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

}
