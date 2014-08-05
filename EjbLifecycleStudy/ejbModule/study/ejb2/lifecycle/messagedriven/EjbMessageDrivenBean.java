package study.ejb2.lifecycle.messagedriven;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Message-driven bean has no client, the only one that can talk to a bean is the
 * Container. Its lifecycle is equals the stateless' lifecycle. 
 * All methods cannot throw any application exceptions.
 * 
 * If the bean fails (throw any runtime exception or sets rollback in the transaction), the Container
 * will send it back to the queue to be proccessed later by other bean.
 * 
 * Reliability mechanisms
 * . We can set a time-to-live or JMS expiration to ensure that the provider will remove them from the
 * destination when they become obsolete;
 * . Specify that messages are persistent in the event of a provider failure. Persistent delivery ensures
 * that a message is delivered only once to a consumer, whereas nonpersistent delivery requires a message
 * be delivered once at most;
 * . Specify various levels of message acknowledgment;
 * . The disadvantage of using the pub-sub model is that a message consumer must be running when the
 * messages are sent to the topic; otherwise it will not receive them. By using durable subscribers, the
 * JMS API provides a way to keep messages in the topic until all subscribed consumers receive them. With
 * durable subscription, the receiver can be offline for some time, but when it reconnects, it receives
 * the messages that arrived during its disconnection;
 * . We can use message priority levels to instruct the JMS provider to deliver urgent messages first.
 * 
 */
public class EjbMessageDrivenBean implements MessageDrivenBean, MessageListener {
	private static final long serialVersionUID = -943557374431021955L;

	/* methods from MessageDriveBean interface */
	public void ejbRemove() throws EJBException {
		System.out.println("ejbRemove");
	}

	public void setMessageDrivenContext(MessageDrivenContext ctx) throws EJBException {
		System.out.println("setMessageDrivenContext");
	}
	
	/* this method doesn't come from any interface */
	/**
	 * Must have a single, no-arg ejbCreate()
	 */
	public void ejbCreate() {
		System.out.println("ejbCreate");
	}

	/* method from MessaListener */
	/**
	 * The unique business method.
	 * The Message received can be either a TextMessage, BytesMessage, MapMessage, ObjectMessage or StremMessage.
	 * 
	 * When the MDB is CMP, to return the message to the Queue to be proccessed by other bean we need roll back the
	 * transation or throw a EJBException. When the MDB is BMP, the only way to return the message to the Queue
	 * is throwing a EJBException.
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		System.out.println("onMessage");
	}

}
