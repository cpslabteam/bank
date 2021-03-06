package cpslab.bank.api.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslab.util.db.spi.BaseDataEntity;

@Entity(name = "Account")
public class Account extends BaseDataEntity {

	@Column(name = "account_number")
	private String accountNumber;

	@JsonSerialize(as = BaseDataEntity.class)
	@ManyToOne
	@JoinColumn(name = "branch_id")
	private Branch branch;

	@Column(name = "balance")
	private Double balance;

	@JsonSerialize(contentAs = BaseDataEntity.class)
	@ManyToMany(mappedBy = "accounts")
	private Set<Customer> owners;

	public Account() {
		owners = new HashSet<>();
	}

	public Account(String accountNumber, Branch branch, Double balance) {
		this.accountNumber = accountNumber;
		this.branch = branch;
		this.balance = balance;
		owners = new HashSet<>();
	}

	public boolean addOwner(Customer owner) {
		return owners.add(owner) && owner.getAccounts().add(this);
	}

	public boolean removeOwner(Customer owner) {
		return owners.remove(owner) && owner.getAccounts().remove(this);
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

	public Double getBalance() {
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

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	@Override
	public String toString() {
		return "Account [id=" + getId() + ", accountNumber=" + accountNumber + ", branch=" + branch
				+ ", balance=" + balance + "]";
	}

}
