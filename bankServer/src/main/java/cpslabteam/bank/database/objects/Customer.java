package main.java.cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonView;

import main.java.cpslabteam.bank.jsonserialization.JSONViews;

/**
 * Represents the Customer object and defines the mapping of it's table in the
 * database. Contains Name, Street, and City.
 *
 * @see BaseDataObject
 */
@Entity(name = "Customer")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends BaseDataObject {

	@JsonView(JSONViews.Info.class)
	@Column(name = "name", nullable = false)
	private String name;

	@JsonView(JSONViews.Info.class)
	@Column(name = "street", nullable = false)
	private String street;

	@JsonView(JSONViews.Info.class)
	@Column(name = "city", nullable = false)
	private String city;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
