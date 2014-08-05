package study.ejb3.lifecycle.messagedriven;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.NamingException;

/**
 * In JEE 5 we can get the Factory and Topic/Queue through injection.
 * 
 */
public class QueueMessageSender {
	@Resource(mappedName = "jms/TopicConnectionFactory")
	private TopicConnectionFactory connectionFactory;
	@Resource(mappedName = "jms/Topic")
	private Topic topic;

	private TopicConnection connection;
	private TopicSession session;
	private TopicPublisher publisher;

	public QueueMessageSender(String jndiFactory, String jndiQueue)
			throws NamingException, JMSException {
		
		connection = connectionFactory.createTopicConnection();
		session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		publisher = session.createPublisher(topic);
	}

	public void sendText(String text) throws JMSException {
		TextMessage message = session.createTextMessage();
		message.setText(text);
		publisher.publish(message);
	}

	public void close() {
		try {
			publisher.close();
		} catch (JMSException e) {
		}
		try {
			session.close();
		} catch (JMSException e) {
		}
		try {
			connection.close();
		} catch (JMSException e) {
		}
	}

}
