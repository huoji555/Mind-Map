package com.hwj.dao;

import java.util.List;

import com.hwj.entity.LoginUser;

public interface ILoginUserDao extends IBaseDao<LoginUser> {
	public void deleteUserByBatch(List<LoginUser> list);

	public void saveUserByBatch(List<LoginUser> list);

	public void passwordRecBynickName(List<String> list);

	public boolean updatePasswordByNickName(String newPassword, String nickName);

	public String getnickNameByIt(String nickName);
}
