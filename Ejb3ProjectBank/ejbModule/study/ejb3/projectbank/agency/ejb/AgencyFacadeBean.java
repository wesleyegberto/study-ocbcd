package study.ejb3.projectbank.agency.ejb;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import study.ejb3.projectbank.account.Account;
import study.ejb3.projectbank.agency.Agency;
import study.ejb3.projectbank.agency.AgencyException;

@Stateful
public class AgencyFacadeBean implements AgencyFacade {
	@Resource
	private SessionContext ctx;
	@PersistenceContext(unitName = "BANK_DB_UNIT")
	private EntityManager em;
	
	private Agency agency;
	
	private int getNewAccountNumber() {
		return Math.abs((int) System.currentTimeMillis());
	}
	
	public void initiateSession(long agencyNumber) throws AgencyException {
		if(agencyNumber <= 0) {
			throw new AgencyException("Invalid agency.");
		}
		Query query = em.createNamedQuery(Agency.FIND_AGENCY);
		query.setParameter(1, agencyNumber);
		this.agency = (Agency) query.getSingleResult();
		if(agency == null) {
			throw new AgencyException("Agency not found, check the agency number.");
		}
	}

	@PreDestroy @Remove @PrePassivate
	public void remove() {
		this.agency = null;
		this.em = null;
		this.ctx  = null;
	}

	public void createAccount(String name, double balance) throws AgencyException {
		if(name == null || name.length() < 5) {
			throw new AgencyException("Invalid name.");
		} else if(balance < 0) {
			throw new AgencyException("Invalid balance, must be greater or equals 0.");
		}
		
		Account account = new Account(agency.getAgencyNumber(), name, balance);
		account.setAccountNumber(getNewAccountNumber());
		
		// creates the account
		em.persist(account);
		System.out.println("Account created");
	}
	
	public String getAgencyInformation() {
		return agency.getAgencyNumber() + " - " + agency.getAddress();
	}
	

}
