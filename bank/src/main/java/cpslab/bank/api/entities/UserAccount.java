package cpslab.bank.api.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

import cpslab.util.db.spi.BaseDataEntity;

@Entity(name = "UserAccount")
public class UserAccount extends BaseDataEntity {

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	public UserAccount() {

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
