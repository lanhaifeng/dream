package com.feng.baseframework.mail;

import com.mchz.tool.mail.ExchangeMailSender;
import com.mchz.tool.mail.MailSender;
import org.junit.Test;

import javax.mail.MessagingException;

/**
 * baseframework
 * 2019/5/9 10:05
 * 测试mail
 *
 * @author lanhaifeng
 * @since
 **/
public class McMailToolTest {

	@Test
	public void exchangeTest() throws MessagingException {
		MailSender exchangeSender = new ExchangeMailSender(true, "758764630@qq.com", "wwuvhyhxferybfbf").withHost("ex.qq.com").withPort(25).withDomain("qq.com").build();
		exchangeSender.send("758764630@qq.com", "758764630@qq.com", "Test email", "This is a test email, please ignore.");

	}
}
