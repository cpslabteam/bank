package cpslabteam.bank.database.objects;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "Borrower")
public class Borrower extends Customer {

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Loan> loans;

	public Borrower() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Loan> getLoans() {
		return loans;
	}
}
