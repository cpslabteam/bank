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

import capslab.util.db.spi.BaseDataEntity;

@Entity(name = "Account")
public class Account extends BaseDataEntity {

	@NaturalId
	@Column(name = "account_number")
	private String accountNumber;

	@JsonSerialize(as = BaseDataEntity.class)
	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false)
	private Branch branch;

	@Column(name = "balance", nullable = false)
	private BigDecimal balance;

	@JsonSerialize(contentAs = BaseDataEntity.class)
	@ManyToMany(mappedBy = "accounts")
	private Set<Customer> owners;

	public Account() {
		owners = new HashSet<>();
	}

	public Account(String accountNumber, Branch branch, BigDecimal balance) {
		this.accountNumber = accountNumber;
		this.branch = branch;
		this.balance = balance;
		owners = new HashSet<>();
	}

	public void addOwner(Customer owner) {
		owners.add(owner);
		owner.getAccounts().add(this);
	}
	
	public void removeOwner(Customer owner){
		owners.remove(owner);
		owner.getAccounts().remove(this);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		final Account account = (Account) obj;

		return Objects.equals(accountNumber, account.getAccountNumber());
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
		return Objects.hash(accountNumber);
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
