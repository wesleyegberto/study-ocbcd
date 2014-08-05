package study.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ResourceFactory {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("DB_PERSON");
	
	public static EntityManager getEM() {
		EntityManager em = emf.createEntityManager();
		return em;
	}
}
