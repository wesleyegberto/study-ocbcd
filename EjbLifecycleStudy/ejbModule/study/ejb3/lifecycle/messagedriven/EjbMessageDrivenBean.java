package study.ejb3.lifecycle.messagedriven;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJBException;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * In EJB 3.x, MDB only changes the way we declare it. Now we can use annotations.
 * In transaction, with CMT we still using only either REQUIRED or NOT_SUPPORTED.
 * 
 * We can use @ActivationConfigProperty to define certain properties such as message selectors,
 * acknowledgement mode, durable subscribers, and so on. The activationConfig property allows you
 * to provide messaging system–specific configuration, meaning that properties are not portable
 * across JMS providers.
 * 
 * And is important to be aware that MDBs are not part of the new EJB Lite model, meaning they
 * cannot be deployed in a simple web application (in a war file), but still need an enterprise
 * packaging (ear archive)!
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
@MessageDriven(
	mappedName = "jms/MyMDB",
	activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType ", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "/queue/QueueDev"),
		@ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
		@ActivationConfigProperty(propertyName = "messageSelector", propertyValue = "PRIORITY < 4")
	}
)
public class EjbMessageDrivenBean implements MessageListener {

	@Resource
	private MessageDrivenContext ctx;
	
	/** Like ejbRemove() from MessageDrivenBean interface. */
	@PreDestroy
	public void remove() throws EJBException {
		System.out.println("remove");
	}
	
	/**
	 * Like the required ejbCreate() from MDB 2.x.
	 */
	@PostConstruct
	public void postConstruct() {
		System.out.println("postConstruct");
	}

	/* method from MessaListener */
	/**
	 * But we still need implement the MessageListener to register in JMS consumer list.
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	public void onMessage(Message message) {
		System.out.println("onMessage");
	}

}
