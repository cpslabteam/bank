package main.java.cpslabteam.bank.database.objects;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity(name = "Depositor")
public class Depositor extends Customer {

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Account> accounts;

	public Depositor() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Account> getAccounts() {
		return accounts;
	}
}
