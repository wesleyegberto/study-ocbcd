package study.ejb3.advice;

import javax.ejb.Remote;

@Remote
public interface Advice {
	public String getMessage();
}
