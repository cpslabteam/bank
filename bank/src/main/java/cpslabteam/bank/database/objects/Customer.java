package cpslabteam.bank.database.objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Represents the Customer object and defines the mapping of it's table in the
 * database. Contains Name, Street, and City.
 *
 * @see BaseDataObject
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Customer extends BaseDataObject {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "street", nullable = false)
	private String street;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Customer))
			return false;

		final Customer customer = (Customer) obj;

		if (!customer.getName().equals(getName()))
			return false;

		if (!customer.getStreet().equals(getStreet()))
			return false;

		if (!customer.getCity().equals(getCity()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += ((name == null) ? 0 : name.hashCode());
		hashcode += ((street == null) ? 0 : street.hashCode());
		hashcode += ((city == null) ? 0 : city.hashCode());
		return hashcode;
	}

}
