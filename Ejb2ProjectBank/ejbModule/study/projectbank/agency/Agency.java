package study.projectbank.agency;

public class Agency {
	private long agencyNumber;
	private String address;

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
