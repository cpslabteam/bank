package cpslabteam.bank.database.objects;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(name = "Account")
public class Account extends BaseDataObject {

	@NaturalId
	@Column(name = "account_number")
	private String accountNumber;

	@JsonSerialize(as = BaseDataObject.class)
	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

	@Column(name = "balance", nullable = false)
	private BigDecimal balance;

	@JsonSerialize(contentAs = BaseDataObject.class)
	@ManyToMany(mappedBy = "accounts")
	private Set<Customer> owners;

	public Account() {
		super();
		owners = new HashSet<>();
	}

	public Account(String accountNumber, Branch branch, BigDecimal balance) {
		super();
		this.accountNumber = accountNumber;
		this.branch = branch;
		this.balance = balance;
		owners = new HashSet<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Account))
			return false;

		final Account account = (Account) obj;

		if (!account.getAccountNumber().equals(getAccountNumber()))
			return false;

		return true;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public Branch getBranch() {
		return branch;
	}

	public Set<Customer> getOwners() {
		return owners;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += ((accountNumber == null) ? 0 : accountNumber.hashCode());
		return hashcode;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "Account [id=" + getId() + ", accountNumber=" + accountNumber + ", branch=" + branch + ", balance="
				+ balance + "]";
	}

}
