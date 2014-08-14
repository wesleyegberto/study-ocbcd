package study.ejb3.projectbank.logger;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
public class AsyncLogger {

	@Asynchronous
	public void logInfo(String message) {
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
		}
		System.out.println("INFO: " + message);
	}
}
