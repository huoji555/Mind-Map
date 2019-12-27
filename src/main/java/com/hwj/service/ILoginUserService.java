package com.hwj.service;

import java.util.List;

import com.hwj.entity.LoginUser;

public interface ILoginUserService extends IBaseService<LoginUser>{

	public int hasMatchUser(String nickName, String passWord, String role);

	public int hasMatchUser(String nickName, String passWord);

	public int deletedeleteByUserNickName(LoginUser user);

	public void deleteUserByBatch(List<LoginUser> list);

	public void saveUserByBatch(List<LoginUser> list);

	public void passwordRecBynickName(List<String> list);

	public boolean updatePasswordByNickName(String newPassword, String nickName);

	public String getnickNameByIt(String nickName);
	
	
}
