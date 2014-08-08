package study.jaxb.test;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import study.business.Person;

public class JAXBTest {
	public static void main(String[] args) throws JAXBException {
		Person p = new Person();
		p.setId(171);
		p.setName("Steve Rogers");
		p.setAge(96);
		
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(Person.class);
		Marshaller m = context.createMarshaller();
		m.marshal(p, writer);
		System.out.println(writer.toString());
	}

}
