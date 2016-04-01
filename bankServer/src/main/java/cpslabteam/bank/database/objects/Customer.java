package cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.fasterxml.jackson.annotation.JsonView;

import cpslabteam.bank.jsonserialization.JsonViews;

/**
 * Represents the Customer object and defines the mapping of it's table in the
 * database. Contains Name, Street, and City.
 *
 * @see BaseDataObject
 */
@Entity(name = "Customer")
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends BaseDataObject {

	@JsonView(JsonViews.Info.class)
	@Column(name = "name", nullable = false)
	private String name;

	@JsonView(JsonViews.Info.class)
	@Column(name = "street", nullable = false)
	private String street;

	@JsonView(JsonViews.Info.class)
	@Column(name = "city", nullable = false)
	private String city;

	public Customer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCity() {
		return city;
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
