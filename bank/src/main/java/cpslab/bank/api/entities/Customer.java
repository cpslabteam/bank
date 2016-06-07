package cpslab.bank.api.entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslab.util.db.spi.BaseDataEntity;

/**
 * Represents the Customer object and defines the mapping of it's table in the
 * database. Contains Name, Street, and City.
 *
 * @see BaseDataEntity
 */
@Entity(name = "Customer")
public class Customer extends BaseDataEntity {
	
	@Column(name = "customer_number", nullable = false)
	private String customerNumber;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "city", nullable = false)
	private String city;

	@JsonSerialize(contentAs = BaseDataEntity.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accounts;

	@JsonSerialize(contentAs = BaseDataEntity.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Loan> loans;

	public Customer() {
		super();
		this.accounts = new HashSet<>();
		this.loans = new HashSet<>();
	}

	public Customer(String customerNumber, String name, String street, String city) {
		super();
		this.customerNumber = customerNumber;
		this.name = name;
		this.street = street;
		this.city = city;
		this.accounts = new HashSet<>();
		this.loans = new HashSet<>();
	}

	public boolean addAccount(Account account) {
		return accounts.add(account);
	}

	public boolean addLoan(Loan loan) {
		return loans.add(loan);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Customer customer = (Customer) obj;
		return Objects.equals(customerNumber, customer.getCustomerNumber());
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public String getCity() {
		return city;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public Set<Loan> getLoans() {
		return loans;
	}

	public String getName() {
		return name;
	}

	public String getStreet() {
		return street;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerNumber);
	}

	public boolean removeAccount(Account account) {
		return accounts.remove(account);
	}

	public boolean removeLoan(Loan loan) {
		return loans.remove(loan);
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return "Customer [id=" + getId() + ", customerNumber=" + customerNumber + ", name=" + name + ", street=" + street + ", city="
				+ city + "]";
	}

}
