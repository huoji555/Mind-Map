package com.hwj.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hwj.entity.LoginUser;
import com.hwj.entity.RoleName;
import com.hwj.service.ILoginUserService;
import com.hwj.service.IRoleNameService;

@Component
public class TryCatchUserService {

	@Autowired
	private ILoginUserService userService;
    @Autowired
    private IRoleNameService iRoleNameService;
	
	
    /**
     * @author Ragty
     * @param 根据昵称获取user
     * @serialData 2018.2.7
     * @return
     */
    public LoginUser getUserByNickname(String nickName) {
    	LoginUser user;
    	try {
			user=userService.get(nickName);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
    	return user;
    }
    
    
    /**
     * @author Ragty
     * @param 保存user
     * @serialData 2018.2.7
     * @return
     */
    public boolean saveUser(LoginUser user) {
    	
    	try {
			userService.save(user);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    	
    }
    
    
    /**
     * @author Ragty
     * @param 批量保存user
     * @serialData 2018.2.7
     * @return
     */
    public boolean saveUserByBatch(List<LoginUser> list) {
    	
    	try {
			userService.saveUserByBatch(list);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    }
    
    
    /**
     * @author Ragty
     * @param 删除user
     * @serialData 2018.2.7
     * @return
     */
    public boolean deletedUser(LoginUser user) {
    	
    	try {
			userService.delete(user);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    }
    
    
    /**
     * @author Ragty
     * @param 批量删除user
     * @serialData 2018.2.7
     * @return
     */
    public boolean deletedUserByBatch(List<LoginUser> list) {
    	
    	try {
			userService.deleteUserByBatch(list);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    }
    
    
    /**
     * @author Ragty
     * @param 修改user
     * @serialData 2018.2.7
     * @return
     */
    public boolean update(LoginUser user) {
    	
    	try {
			userService.update(user);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    }
    
    
    /**
     * @author Ragty
     * @param 根据用户名修改密码
     * @param newPassword
     * @param nickName
     * @serialData 2018.2.7
     * @return
     */
    public boolean updatePasswordByNickName(String newPassword, String nickName) {
    	
    	try {
			userService.updatePasswordByNickName(newPassword, nickName);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    	return true;
    }
    
    
    /**
     * @author Ragty
     * @param 登录时判断能否登陆，以及登陆后相应的权限分配
     * @param nickName
     * @param passWord
     * @return
     */
    public int hasMatchUser(String nickName, String passWord) {
    	
    	int isValidUser;
    	try {
			isValidUser=userService.hasMatchUser(nickName, passWord);
		} catch (Exception e) {
			// TODO: handle exception
			return isValidUser=0;
		}
    	return isValidUser;
    }
    
	
    /**
     * @author Ragty
     * @param 获取所有user对象
     * @serialData 2018.2.7
     * @return
     */
    public List<Map<String, String>> getLoginUserList() {
		List<LoginUser> list;
		List<Map<String, String>> listmap = new ArrayList<Map<String, String>>();
		try {
			list = userService.getAll();

			for (int i = 0; i < list.size(); i++) {
				Map<String, String> map = new HashMap<String, String>();
				LoginUser loginUser = new LoginUser();
				loginUser = list.get(i);
				map.put("nickName", loginUser.getNickName());
				map.put("password", loginUser.getPassword());
				map.put("phoneNumber", loginUser.getPhoneNumber());
				map.put("answer", loginUser.getEmail());
				String roleId=Integer.toString(loginUser.getRoleId());
				map.put("remark", roleId);
				listmap.add(map);

			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			return null;
		}
		return listmap;
	}
    
    
    
    /**
     * @author Ragty
     * @param 增加一个用户
     * @serialData 2018.2.7
     * @return
     */
    public boolean addUser(LoginUser loginUser) {
		try {
			userService.save(loginUser);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
    
    
    /**
     * @author Ragty
     * @param 删除一个用户
     * @serialData 2018.2.7
     * @return
     */
    public boolean deleteUser(LoginUser loginUser) {
		try {
			userService.delete(loginUser);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
    
    
    /**
     * @author Ragty
     * @param 修改一个用户
     * @param loginUser
     * @return
     */
    public boolean updateUser(LoginUser loginUser) {
		try {
			userService.update(loginUser);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
    
    
    /**
     * @author Ragty
     * @param 根据一个参数获取登录用户user
     * @param propertyName
     * @param value
     * @serialData 2018.2.7
     * @return
     */
    public LoginUser getOneLoginUser(String propertyName, Object value) {
		LoginUser loginUser = new LoginUser();
		try {
			loginUser = userService.get(propertyName, value);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return loginUser;
	}
    
    
    /**
     * @author Ragty
     * @param 获取角色的信息，以增加一个选角色的下拉框
     * @serialData 2018.2.7
     * @return
     */
    public List<RoleName> getRoleName() {
		List<RoleName> list;
		try {
			list = iRoleNameService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		return list;
	}
    
    
    /**
     * @author Ragty
     * @param  获取所有用户数据
     * @serialData 2018.6.13
     * @return
     */
    public List<LoginUser> getAllUser(){
    	
    	List<LoginUser> list = null;
    	try {
			list = userService.getAll();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
    	return list;
    }
    
    
}
