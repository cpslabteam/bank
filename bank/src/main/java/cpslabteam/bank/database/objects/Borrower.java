package cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Represents the Borrower object and defines the mapping of it's table in the
 * database. Contains Loan Number.
 *
 * @see BaseDataObject
 */
@Entity(name = "Borrower")
public class Borrower extends Customer {

	@Column(name = "loan_number", nullable = false)
	private String loanNumber;

	public Borrower() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Borrower))
			return false;

		final Borrower borrower = (Borrower) obj;

		if (!borrower.getLoanNumber().equals(getLoanNumber()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += ((loanNumber == null) ? 0 : loanNumber.hashCode());
		return hashcode;
	}
}
