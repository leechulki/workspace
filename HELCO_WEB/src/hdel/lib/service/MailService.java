package hdel.lib.service;

import tit.base.ServiceManagerFactory;
import tit.biz.BusinessException;
import tit.service.business.config.ConfigUtility;
import tit.service.mail.MailSendService;
import tit.service.mail.MailSender;

public class MailService {

	public void send(String from, String to, String head, String body) {
		MailSendService service = (MailSendService)ServiceManagerFactory.getServiceObject("MailService");
		
		MailSender sendObj = service.createMailSender();
		sendObj.setTo(to);
		if (null == from || "".equals(from)) {
			sendObj.setFrom(ConfigUtility.getString("ADMIN_MAIL"));
		} else {
			sendObj.setFrom(from);
		}
		sendObj.setSubject(head);
		sendObj.setBodyType(MailSender.CONTENT_TYPE_TEXT);
		
		sendObj.setBody(body);
		
		// Àü¼Û 
		try {
			sendObj.sendMessage();
		} catch (Exception e) {
			throw new BusinessException("CE00001");
		}
	}
}
