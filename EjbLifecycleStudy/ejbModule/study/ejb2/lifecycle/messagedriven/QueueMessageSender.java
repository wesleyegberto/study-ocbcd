package study.ejb2.lifecycle.messagedriven;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Using the JMS.
 * 
 * Connection factories are administered objects that allow an application to
 * connect to a provider by creating a Connection object programmatically.
 * 
 * A Session provides a transactional context in which a set of messages to be
 * sent or received is grouped in an atomic unit of work, meaning that if you
 * send several messages during the same session, JMS will ensure that they
 * either all arrive in the order they’ve been sent at the destination or none
 * at all.
 * 
 * We can use some headers from message to specify the priority and so on.
 */
public class QueueMessageSender {
	// Connection to JMS
	private QueueConnection queueConnection;
	// Session to JMS
	private QueueSession queueSession;
	// queue to our JMS linked to MBD
	private Queue queue;
	// sender to our JMS linked to MDB
	private QueueSender queueSender;

	public QueueMessageSender(String jndiFactory, String jndiQueue)
			throws NamingException, JMSException {
		// gets the JNDI
		Context context = new InitialContext();
		// gets a ConnectionFactory
		QueueConnectionFactory queueFactory = (QueueConnectionFactory) context
				.lookup(jndiFactory);
		// create a JMS connection from the ConnectionFactory
		queueConnection = queueFactory.createQueueConnection();
		// creates the session with JMS, the first param specifies whether or not the session is transactional
		// if true the messages only will be send after a commit()
		queueSession = queueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		// gets the JMS destination
		queue = (Queue) context.lookup(jndiQueue);
		// gets the sender to JMS destination
		queueSender = queueSession.createSender(queue);
	}

	public void sendText(String text) throws JMSException {
		TextMessage message = queueSession.createTextMessage();
		message.setText(text);
		queueSender.send(message);
	}

	public void sendObject(Serializable object) throws JMSException {
		ObjectMessage message = queueSession.createObjectMessage();
		message.setObject(object);
		queueSender.send(message);
	}

	public void close() {
		try {
			queueSender.close();
		} catch (JMSException e) {
		}
		try {
			queueSession.close();
		} catch (JMSException e) {
		}
		try {
			queueConnection.close();
		} catch (JMSException e) {
		}
	}

}
