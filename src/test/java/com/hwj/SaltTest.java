package com.hwj;

import com.hwj.util.MD5Util;
import com.hwj.util.PBKDF2Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.security.util.Password;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaltTest {

    @Autowired
    private PBKDF2Util pbkdf2Util;
    @Autowired
    private MD5Util md5Util;


    @Test
    public void mailSendTest() throws Exception{

        String password = "123456";

        String salt = pbkdf2Util.generateSalt();
        String pbkdf2 = pbkdf2Util.getEncryptedPassword(password,salt);
        String md5 = md5Util.digest(password);

        System.out.println("原始密码:"+password);
        System.out.println("MD5加密后的密码:"+md5);
        System.out.println("盐值:"+salt);
        System.out.println("PBKDF2加盐后的密码:"+pbkdf2);
        System.out.println("Test success");

    }


}
