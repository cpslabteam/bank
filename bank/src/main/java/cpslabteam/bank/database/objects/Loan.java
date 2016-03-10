package cpslabteam.bank.database.objects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

@Entity(name = "Loan")
public class Loan extends BaseDataObject {

	@Column(name = "loan_number", nullable = false)
	@NaturalId
	private String loanNumber;

	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "BRANCH_ID_FK"))
	private Branch branch;

	@Column(name = "amount", precision = 20, scale = 2, columnDefinition = "NUMERIC(20,2)", nullable = false)
	private BigDecimal amount;

	public Loan() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Loan))
			return false;

		final Loan loan = (Loan) obj;

		if (!loan.getLoanNumber().equals(getLoanNumber()))
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
