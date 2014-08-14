package study.ejb3.projectbank.agency;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

@Entity
@Table(name = "AGENCY")
@Access(AccessType.FIELD)
@NamedNativeQueries({
	@NamedNativeQuery(name = Agency.GET_ALL, query = "select AGENCY_NUMBER, ADDRESS from AGENCY ag", resultClass = Agency.class),
	@NamedNativeQuery(name = Agency.FIND_AGENCY, query = "select AGENCY_NUMBER, ADDRESS from AGENCY ag where ag.AGENCY_NUMBER = ?1", resultClass = Agency.class)
})
public class Agency implements Serializable {
	private static final long serialVersionUID = 6949501675445181129L;
	
	@Id
	@Column(name = "AGENCY_NUMBER", nullable = false)
	private long agencyNumber;
	@Column(name = "ADDRESS")
	private String address;

	public static final String GET_ALL = "Agency.getAll";
	public static final String FIND_AGENCY = "Agency.findAgency";
	
	public Agency() {
	}
	
	public Agency(long agencyNumber, String address) {
		this.agencyNumber = agencyNumber;
		this.address = address;
	}

	public long getAgencyNumber() {
		return agencyNumber;
	}

	public void setAgencyNumber(long agencyNumber) {
		this.agencyNumber = agencyNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
