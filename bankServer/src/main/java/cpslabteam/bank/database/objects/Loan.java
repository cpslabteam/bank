package cpslabteam.bank.database.objects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslabteam.bank.jsonserialization.InfoSerializer;
import cpslabteam.bank.jsonserialization.JsonViews;

@Entity(name = "Loan")
public class Loan extends BaseDataObject {

	@JsonView(JsonViews.Info.class)
	@Column(name = "loan_number")
	@NaturalId
	private String loanNumber;

	@JsonView(JsonViews.Details.class)
	@JsonSerialize(using = InfoSerializer.class)
	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "BRANCH_ID_FK") )
	private Branch branch;

	@JsonView(JsonViews.Details.class)
	@Column(name = "amount", precision = 20, scale = 2, columnDefinition = "NUMERIC(20,2)", nullable = false)
	private BigDecimal amount;

	public Loan() {
		super();
		// TODO Auto-generated constructor stub
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

	public BigDecimal getAmount() {
		return amount;
	}

	public Branch getBranch() {
		return branch;
	}

	public String getLoanNumber() {
		return loanNumber;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += ((loanNumber == null) ? 0 : loanNumber.hashCode());
		return hashcode;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	@Override
	public String toString() {
		return "Loan [id=" + getId() + ", loanNumber=" + loanNumber + ", branch=" + branch + ", amount=" + amount + "]";
	}
}
