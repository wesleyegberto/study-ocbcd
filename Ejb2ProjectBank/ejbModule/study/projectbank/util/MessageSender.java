package study.projectbank.util;

import java.io.Serializable;

import javax.jms.JMSException;

public interface MessageSender {
	public void sendText(String text) throws JMSException;

	public void sendObject(Serializable object) throws JMSException;
	
	public void close();
}
