package com.hwj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.hwj.util.SendMail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailTest {
	
	@Autowired
	private SendMail sendMail;
	
	@Test
	public void mailSendTest() {
		sendMail.send("961946407@qq.com", "Mail Test","This is the first Mail send.");
		System.out.println("Test success");
	}

}
