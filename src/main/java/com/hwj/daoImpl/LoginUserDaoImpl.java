package com.hwj.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.CacheMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hwj.dao.ILoginUserDao;
import com.hwj.entity.LoginUser;
import com.hwj.tools.SSHA;

@Repository(value = "ILoginUserDao")
public class LoginUserDaoImpl extends BaseDaoImpl<LoginUser> implements
		ILoginUserDao {
	
	@Autowired
	private SSHA ssha;
	@Resource
	private SessionFactory sessionFactory;
	
	@Override
	public Session getCurrentSession() {
		Session session = null;
		try {
			session = this.sessionFactory.getCurrentSession();

		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(session);
		return session;
	}
	

	// 批量删除用户，先将要操作的用户放在内存中（没有直接修改放到数据库）,然后每有20个用户，更新一下数据库
	@Override
	public void deleteUserByBatch(List<LoginUser> list) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);// 不需要在执行查询之前把修改清除到数据库
		for (int i = 0; i < list.size(); i++) {
			LoginUser user = list.get(i);
			session.delete(user);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}
	}

	// 批量保存用户，同上
	@Override
	public void saveUserByBatch(List<LoginUser> list) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		// TODO Auto-generated method stub
		for (int i = 0; i < list.size(); i++) {
			LoginUser user = new LoginUser();
			user = list.get(i);
			session.save(user);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}

	}

	// 根据用户信息重置密码（recovery the password by nickname）
	@Override
	public void passwordRecBynickName(List<String> list) {
		Session session = getCurrentSession();
		session.setCacheMode(CacheMode.IGNORE);
		for (int i = 0; i < list.size(); i++) {
			LoginUser user = new LoginUser(); // 新建一个对象，用来更新之后重置后的数据
			user = this.get(list.get(i));
			System.out.println("user" + i + user);
			String password = null;
			try {
				password = ssha.digest("123456");// sha加密
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			user.setPassword(password);
			session.update(user);
			if (i % 20 == 0) {
				session.flush();
				session.clear();
			}
		}

	}

	// 根据nickname修改用户密码
	@Override
	public boolean updatePasswordByNickName(String newPassword, String nickName) {
		// TODO Auto-generated method stub
		String hql = "update LoginUser  user set user.password=:password where user.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("password", newPassword);
		query.setParameter("nickName", nickName);
		int b = query.executeUpdate();
		if (b > 0) {
			return true;
		} else {
			return false;
		}
	}

	// 根据用户名获取所有信息...
	@SuppressWarnings("unchecked")
	@Override
	public String getnickNameByIt(String nickName) {

		// TODO Auto-generated method stub
		String hql = "select l.nickName from LoginUser l where l.nickName=:nickName";
		Query query = getCurrentSession().createQuery(hql);
		query.setParameter("nickName", nickName);
		List<String> list = new ArrayList<String>();
		list = query.list();
		if (list.size() > 0) {
			return list.get(0);
		}

		return null;
	}
}
