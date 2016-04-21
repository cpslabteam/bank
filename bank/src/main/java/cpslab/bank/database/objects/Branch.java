package cpslab.bank.database.objects;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity(name = "Branch")
public class Branch extends BaseDataObject {

	@Column(name = "name", nullable = false)
	@NaturalId(mutable = true)
	private String name;

	@Column(name = "city", nullable = false)
	private String city;

	@Column(name = "assets", nullable = false)
	private BigDecimal assets;
	
	@JsonSerialize(contentAs = BaseDataObject.class)
	@OneToMany(mappedBy = "branch", cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Account> accounts;
	
	@JsonSerialize(contentAs = BaseDataObject.class)
	@OneToMany(mappedBy = "branch", cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Loan> loans;

	public Branch() {
		super();
		accounts = new HashSet<>();
		loans = new HashSet<>();
	}

	public Branch(String name, String city, BigDecimal assets) {
		super();
		this.name = name;
		this.city = city;
		this.assets = assets;
		accounts = new HashSet<>();
		loans = new HashSet<>();
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public BigDecimal getAssets() {
		return assets;
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
	
	public void setAssets(BigDecimal assets) {
		this.assets = assets;
	}
	
	public void setCity(String city) {
		this.city = city;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Branch [id=" + getId() + ", name=" + name + ", city=" + city + ", assets=" + assets + "]";
	}

}
