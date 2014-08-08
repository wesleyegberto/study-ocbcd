package study.ws.restful;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import study.business.Person;

/**
 * We can also create our own entity provider to supply mapping services
 * between representations and their associated java types.
 * We can also have our reader which will consume.
 */
@Provider
@Produces("application/serializable")
public class MyWriter implements MessageBodyWriter<Person> {

	@Override
	public long getSize(Person arg0, Class<?> arg1, Type arg2,
			Annotation[] arg3, MediaType arg4) {
		return 0;
	}

	@Override
	public boolean isWriteable(Class<?> arg0, Type arg1, Annotation[] arg2,
			MediaType arg3) {
		return true;
	}

	@Override
	public void writeTo(Person person, Class<?> arg1, Type arg2,
			Annotation[] arg3, MediaType arg4,
			MultivaluedMap<String, Object> arg5, OutputStream entityStream)
			throws IOException, WebApplicationException {
		ObjectOutputStream oos = new ObjectOutputStream(entityStream);
		oos.writeObject(person); // the Person must be serializable
		// we can use other type to optimize the performance or security like hashing compress, encrypt
	}

}
