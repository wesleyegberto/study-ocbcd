package study.ejb3.lifecycle.stateless.asynchronous;

import java.util.concurrent.Future;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;

@Stateless
// @Asynchronous here will mark all methods
public class AsyncBean {
	
	@Asynchronous
	public void doSomeLongTask() {
		System.out.println("I'll take a long time...");
	}
	
	public void doSomeShortTask() {
		System.out.println("Done!");
	}
	
	/**
	 * To return a value we just need return a Future, and the client
	 * can treat the result like the normal is trated in Future API in SE.
	 * @param encryptedMessage
	 * @return
	 */
	@Asynchronous
	public Future<String> decryptMessage(String encryptedMessage) {
		// some dark things
		return new AsyncResult<String>("asjdasid32jd32");
	}
}
