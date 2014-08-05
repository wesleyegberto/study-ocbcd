package client.ejbadvice.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import capintro.Advice;
import capintro.AdviceHome;

public class EJB2AdviceInJBossTest {
	public static void main(String[] args) {
		try {
			// These properties are used to initialize the InitialContext object of java naming service from JBoss
			// and we need use the jar from %JBOSS_HOME%/bin/client/jboss-client.jar
			Properties properties = new Properties();
			properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
			properties.put(Context.PROVIDER_URL,"remote://127.0.0.1:4447");
			// this user and password was added with a tool: %JBOSS_HOME%/bin/add-user.sh
			properties.put(Context.SECURITY_PRINCIPAL, "user");
			properties.put(Context.SECURITY_CREDENTIALS, "1234abc@");
			properties.put("jboss.naming.client.ejb.context", true);
			
			// gets the entry point into the JNDI naming service
			Context ctx = new InitialContext(properties);
			AdviceHome home = (AdviceHome) ctx.lookup("java:Ejb2Advice/AdviceBean!capintro.AdviceHome");
			Advice advisor = home.create();
			System.out.println(advisor);
			System.out.println(advisor.getClass());
			System.out.println("What you got from JBoss: " + advisor.getMessage());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
