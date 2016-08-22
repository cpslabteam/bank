package cpslab.bank.api.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import cpslab.util.db.spi.BaseDataEntity;

@Entity(name = "Division")
public class Division extends BaseDataEntity {

	@Column(name = "name")
	private String name;
	
	@JsonSerialize(contentAs = BaseDataEntity.class)
	@OneToMany(mappedBy = "division", cascade={ CascadeType.PERSIST, CascadeType.MERGE})
	private Set<Branch> branches;
	
	public Division() {
		super();
		branches = new HashSet<>();
	}

	public Division(String name) {
		super();
		this.name = name;
		branches = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Branch> getBranches() {
		return branches;
	}

	public void setBranches(Set<Branch> branches) {
		this.branches = branches;
	}
}
