package com.hwj.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.security.MessageDigest;

/**
 * @author lierlei@xingyoucai.com
 */
@Component
public class MD5Util {
	
    public  String digest16(String inStr) {
    	return digest(inStr, 16);
	}
    public  String digest(String inStr) {
    	return digest(inStr, 32);
	}
    
    private String digest(String inStr, int rang) {
	    MessageDigest md5 = null;  
	    if (StringUtils.isBlank(inStr)) {
	    	return "";
	    }
	  
	    try {
	        md5 = MessageDigest.getInstance("MD5");  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return "";  
	    }
	    
	    char[] charArray = inStr.toCharArray();  
	    byte[] byteArray = new byte[charArray.length];  
	  
	    for (int i = 0; i < charArray.length; i++) {
	    	byteArray[i] = (byte) charArray[i];	
	    }
	    
	    byte[] md5Bytes = md5.digest(byteArray);  
	    
	    StringBuilder hexValue = new StringBuilder();  
	  
	    for (int i = 0; i < md5Bytes.length; i++) {  
	        int val = ((int) md5Bytes[i]) & 0xff;  
	        if (val < 16)  
	            hexValue.append("0");  
	        hexValue.append(Integer.toHexString(val));  
	    }  
	    if ( rang == 32 ) {
	    	return hexValue.toString();	
	    } else {
	    	return hexValue.toString().substring(8, 24);
	    }
	}
}
