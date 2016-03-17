package main.java.cpslabteam.bank.database.objects;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import main.java.cpslabteam.bank.jsonserialization.InfoSerializer;
import main.java.cpslabteam.bank.jsonserialization.JSONViews;

@Entity(name = "Borrower")
public class Borrower extends Customer {

	@JsonView(JSONViews.Details.class)
	@JsonSerialize(using = InfoSerializer.class)
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<Loan> loans;

	public Borrower() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Set<Loan> getLoans() {
		return loans;
	}
}
