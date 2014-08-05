package test.ejb.datetime;

/**
 * Interface to allow errors in compilation time when building
 * the EJB and its local component interface and the bean.
 * 
 * It's extends DateTimeBusinessRemote and override the methods
 * declaration to remote the exceptions throwed.
 */
public interface DateTimeBusiness extends DateTimeBusinessRemote {
	public String getDate();
	
	public String getTime();
	
	public String getTimestamp();
}
