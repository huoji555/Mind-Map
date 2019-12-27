package com.hwj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hwj.serviceImpl.UserDetailsServiceImpl;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	
	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.headers().frameOptions().disable();
		http.csrf().disable();// 取消CSRF
		http.authorizeRequests().antMatchers(   // 允许对于网站静态资源的无授权访问
				
				"/static/**",
				"/templates/**",
				
				"/*.html",
				"/**/*.woff2",
				"/**/*.jpg",
				"/**/*.png",
				"/**/*.gif",
				"/**/*.css",
				"/**/*.js",
				"/**/*.json",
				 
				"/",
				"/index.do",
				"/iflogin.do",
				"/quitLogin.do",
				"/testLogin.do",
				
				"/loginCheck2.do",
				"/getVerifyCode.do",
				
				"/register.do",
				"/register2.do"
				
			
			).permitAll()
			//其他地址的访问均需验证权限
			.anyRequest().authenticated()
			//指定登录页是"/login"
			.and().formLogin().loginPage("/testLogin.do")
			//登录成功后默认跳转到"/main.do?url=tab.do"
			.defaultSuccessUrl("/index.do").permitAll()
			//退出登录后的默认url是"/home"
			.and().logout().logoutSuccessUrl("/index.do").permitAll();
		
	}
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService());
    }
	
	@Override
	@Bean
    public UserDetailsService userDetailsService() {
        UserDetailsService userDetailsService = username -> null;
        return userDetailsService;
    }

	//自定义UserDetailsService，从数据库中读取用户信息
	@Bean
	public UserDetailsServiceImpl customUserDetailsService() {
		return new UserDetailsServiceImpl();
	}
    //装载BCrypt密码编码器
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
