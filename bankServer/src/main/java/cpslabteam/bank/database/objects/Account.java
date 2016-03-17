package cpslabteam.bank.database.objects;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslabteam.bank.jsonserialization.InfoSerializer;
import cpslabteam.bank.jsonserialization.JSONViews;

@Entity(name = "Account")
public class Account extends BaseDataObject {

	@JsonView(JSONViews.Info.class)
	@NaturalId
	@Column(name = "account_number")
	private String accountNumber;

	@JsonView(JSONViews.Info.class)
	@JsonSerialize(using = InfoSerializer.class)
	@ManyToOne
	@JoinColumn(name = "branch_id", nullable = false, foreignKey = @ForeignKey(name = "BRANCH_ID_FK") )
	private Branch branch;

	@JsonView(JSONViews.Details.class)
	@Column(name = "balance", precision = 20, scale = 2, columnDefinition = "NUMERIC(20,2)", nullable = false)
	private BigDecimal balance;

	public Account() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Account))
			return false;

		final Account account = (Account) obj;

		if (!account.getAccountNumber().equals(getAccountNumber()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int hashcode = 0;
		hashcode += ((accountNumber == null) ? 0 : accountNumber.hashCode());
		return hashcode;
	}

}
