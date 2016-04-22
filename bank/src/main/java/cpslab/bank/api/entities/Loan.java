package cpslab.bank.api.entities;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslab.util.db.hibernate.BaseDataEntity;

@Entity(name = "Loan")
public class Loan extends BaseDataEntity {

	@Column(name = "loan_number")
	@NaturalId
	private String loanNumber;

	@JsonSerialize(as = BaseDataEntity.class)
	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@JsonSerialize(contentAs = BaseDataEntity.class)
	@ManyToMany(mappedBy = "loans")
	private Set<Customer> owners;

	public Loan() {
		super();
		owners = new HashSet<>();
	}

	public Loan(String loanNumber, Branch branch, BigDecimal amount) {
		super();
		this.loanNumber = loanNumber;
		this.branch = branch;
		this.amount = amount;
		owners = new HashSet<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final Loan loan = (Loan) obj;

		return Objects.equals(loanNumber, loan.getLoanNumber());
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

	public Set<Customer> getOwners() {
		return owners;
	}

	@Override
	public int hashCode() {
		return Objects.hash(loanNumber);
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
	
	public void addOwner(Customer owner){
		owners.add(owner);
		owner.getLoans().add(this);
	}
	
	public void removeOwner(Customer owner){
		owners.remove(owner);
		owner.getLoans().remove(this);
	}

	@Override
	public String toString() {
		return "Loan [id=" + getId() + ", loanNumber=" + loanNumber + ", branch=" + branch + ", amount=" + amount + "]";
	}
}
