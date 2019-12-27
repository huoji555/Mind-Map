/*
 *@包名：com.b505.tools        
 *@文档名：SSHA.java 
 *@功能：密码加密  
 *@作者：李振强        
 *@创建时间：2014.5.10  
 *@版权：河北北方学院信息技术研究所 
 */
package com.hwj.tools;

import java.security.MessageDigest;
import org.springframework.stereotype.Component;

@Component
public class SSHA {
	/*
	 * @方法名：digest(String str)
	 * 
	 * @功能：密码加密
	 * 
	 * @功能说明：利用SHA算法进行SSHA加密
	 * 
	 * @作者：李振强
	 * 
	 * @创建时间：2014.5.10
	 * 
	 * @修改时间：2014.5.10
	 */
	public String digest(String str) throws Exception {
		final String salt = "doWhile(1){LeavesFly();YangtzeRiverFlows();}";
		String password = str + salt;
		MessageDigest md = MessageDigest.getInstance("SHA");
		byte[] srcByte = password.getBytes();
		md.update(srcByte);
		byte[] resByte = md.digest();
		String returnStr = new String(resByte);
		return returnStr;
	}
}
