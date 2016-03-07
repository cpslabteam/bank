package cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents the Depositor object and defines the mapping of it's table in the
 * database. Contains Account Number.
 *
 * @see BaseDataObject
 */
@Entity(name = "Depositor")
public class Depositor extends Customer {

	@Column(name = "account_number", nullable = false)
	private String accountNumber;

	public Depositor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Depositor))
			return false;

		final Depositor depositor = (Depositor) obj;

		if (!depositor.getAccountNumber().equals(getAccountNumber()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += ((accountNumber == null) ? 0 : accountNumber.hashCode());
		return hashcode;
	}
}
