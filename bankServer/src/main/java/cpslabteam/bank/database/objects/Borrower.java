package cpslabteam.bank.database.objects;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(name = "Borrower")
public class Borrower extends Customer {

	@JsonSerialize(contentAs = BaseDataObject.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Loan> loans;

	public Borrower() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Loan> getLoans() {
		return loans;
	}

	@Override
	public String toString() {
		return "Borrower [id=" + getId() + ", loans=" + loans + "]";
	}
}
