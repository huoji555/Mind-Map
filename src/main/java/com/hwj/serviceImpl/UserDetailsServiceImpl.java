package com.hwj.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hwj.entity.LoginUser;
import com.hwj.entityUtil.WebUserDetails;
import com.hwj.tools.TryCatchUserService;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private TryCatchUserService tryCatchUserService;
	
	protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
	//登录验证
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("UserDetailsServiceImpl==username=="+username);
		
		//这里应该可以不用再查了
		LoginUser users = tryCatchUserService.getUserByNickname(username);
		
		//读取当前用户有哪些角色权限
		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		SimpleGrantedAuthority authority = new SimpleGrantedAuthority("Role_User");

		authorities.add(authority);
		
		
		boolean enables = true;
		System.out.println("username="+username+" password="+users.getPassword()+" enables="+enables);//+" authorities"+authorities
		WebUserDetails webUserDetails = new WebUserDetails(username, users.getPassword(), enables, authorities);
		
		return webUserDetails;
		
	}
	

}
