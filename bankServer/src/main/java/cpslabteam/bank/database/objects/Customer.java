package cpslabteam.bank.database.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Represents the Customer object and defines the mapping of it's table in the
 * database. Contains Name, Street, and City.
 *
 * @see BaseDataObject
 */
@Entity(name = "Customer")
public class Customer extends BaseDataObject {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "street", nullable = false)
	private String street;

	@Column(name = "city", nullable = false)
	private String city;

	@JsonSerialize(contentAs = BaseDataObject.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accounts;

	@JsonSerialize(contentAs = BaseDataObject.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Loan> loans;

	public Customer() {
		super();
		this.accounts = new HashSet<>();
		this.loans = new HashSet<>();
	}

	public Customer(String name, String street, String city) {
		super();
		this.name = name;
		this.street = street;
		this.city = city;
		this.accounts = new HashSet<>();
		this.loans = new HashSet<>();
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public String getCity() {
		return city;
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

	public void setCity(String city) {
		this.city = city;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	@Override
	public String toString() {
		return "Customer [id=" + getId() + ", name=" + name + ", street=" + street + ", city=" + city + "]";
	}

}
