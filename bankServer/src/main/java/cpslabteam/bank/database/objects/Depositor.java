package cpslabteam.bank.database.objects;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslabteam.bank.jsonserialization.InfoSerializer;
import cpslabteam.bank.jsonserialization.JsonViews;

@Entity(name = "Depositor")
public class Depositor extends Customer {

	@JsonView(JsonViews.Details.class)
	@JsonSerialize(using = InfoSerializer.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accounts = new HashSet<>();

	public Depositor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	@Override
	public String toString() {
		return "Depositor [id=" + getId() + ", accounts=" + accounts + "]";
	}
}
