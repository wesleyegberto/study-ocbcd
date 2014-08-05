package study.ejb3.lifecycle.messagedriven;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

/**
 * A Asynchronous JMS Consumer.
 */
public class AsynchronousJMSConsumerListener implements MessageListener {
	@Resource(mappedName = "jms/TopicConnectionFactory")
	private static TopicConnectionFactory connectionFactory;
	@Resource(mappedName = "jms/Topic")
	private static Topic topic;

	public static void main(String[] args) {
		try {
			TopicConnection connection = connectionFactory .createTopicConnection();
			TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			// the syntax of the selector expression is based on a subset of the SQL92 conditional expression syntax
			MessageConsumer consumer = session.createSubscriber(topic, "PRIORITY BETWEEN 0 AND 4", true);
			consumer.setMessageListener(new AsynchronousJMSConsumerListener());
			connection.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onMessage(Message message) {
		try {
			System.out.println("Message received: " + ((TextMessage) message).getText());
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
