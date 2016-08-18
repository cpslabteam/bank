package cpslab.bank.api.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslab.util.db.spi.BaseDataEntity;

@Entity(name = "Branch")
public class Branch extends BaseDataEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "city")
	private String city;

	@Column(name = "assets")
	private Double assets;
	
	@JsonSerialize(contentAs = BaseDataEntity.class)
	@OneToMany(mappedBy = "branch", cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Account> accounts;
	
	@JsonSerialize(contentAs = BaseDataEntity.class)
	@OneToMany(mappedBy = "branch", cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Loan> loans;

	public Branch() {
		super();
		accounts = new HashSet<>();
		loans = new HashSet<>();
	}

	public Branch(String name, String city, Double assets) {
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

	public Double getAssets() {
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
	
	public void setAssets(Double assets) {
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
