package com.hwj.serviceImpl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hwj.dao.IBaseDao;
import com.hwj.dao.ILoginUserDao;
import com.hwj.entity.Functions;
import com.hwj.entity.LoginUser;
import com.hwj.service.ILoginUserService;

@Service("loginUserService")
public class LoginUserServiceImpl extends BaseServiceImpl<LoginUser> implements
		ILoginUserService {
	

	@Autowired
	@Qualifier(value="ILoginUserDao")
	private ILoginUserDao iloginUserDao;
	
	
	/**
	* 注入DAO
	*/
	@Resource(name = "ILoginUserDao")
	@Override
	public void setDao(IBaseDao<LoginUser> dao) {
	super.setDao(dao);
	}
	

	// 检测用户密码是否正确
	@Override
	public int hasMatchUser(String nickName, String passWord, String role) {

		@SuppressWarnings("unused")
		int b = 0;
		LoginUser user = iloginUserDao.get(nickName);
		if (user == null) {
			return b = 2; // 用户不存在返回2
		} else if (iloginUserDao
				.get("nickName", "password", nickName, passWord) == null) {
			return b = 3; // 密码不正确返回3
		}

		else {
			return b = 1; // 正确返回1
		}
	}

	@Override
	public int deletedeleteByUserNickName(LoginUser user) {
		int b = 0;
		this.delete(user);
		if (this.get(user.getNickName()) == null) {
			b = 1;
		}
		return b;
	}

	@Override
	public void deleteUserByBatch(List<LoginUser> list) {
		// TODO Auto-generated method stub
		iloginUserDao.deleteUserByBatch(list);
	}

	@Override
	public void saveUserByBatch(List<LoginUser> list) {
		// TODO Auto-generated method stub
		iloginUserDao.saveUserByBatch(list);
	}

	@Override
	public void passwordRecBynickName(List<String> list) {
		// TODO Auto-generated method stub
		iloginUserDao.passwordRecBynickName(list);
	}

	@Override
	public boolean updatePasswordByNickName(String newPassword, String nickName) {
		// TODO Auto-generated method stub
		return iloginUserDao.updatePasswordByNickName(newPassword, nickName);
	}

	// 使用中的用户登录验证系统
	@Override
	public int hasMatchUser(String nickName, String passWord) {
		@SuppressWarnings("unused")
		int b = 0;
		LoginUser user = iloginUserDao.get(nickName);
		if (user == null) {
			return b = 2; // 用户不存在
		} else if (iloginUserDao
				.get("nickName", "password", nickName, passWord) == null) {
			return b = 3; // 密码错误
		} else {
			if (iloginUserDao.get(nickName).getRoleId() == 1) {
				return b = 4;     //管理员端
			} else if (iloginUserDao.get(nickName).getRoleId() == 3) {
				return b = 5;  //教师端
			}
			System.out.println(iloginUserDao.get(nickName).getRoleId()+ "weijiweiji");
			return b = 1;   //学生端
		}
	}

	@Override
	public String getnickNameByIt(String nickName) {

		return iloginUserDao.getnickNameByIt(nickName);
	}

}
