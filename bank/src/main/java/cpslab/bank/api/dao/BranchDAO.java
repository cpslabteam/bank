package cpslab.bank.api.dao;

import java.util.List;

import cpslab.bank.api.entities.Branch;
import cpslab.util.db.Dao;

public interface BranchDAO extends Dao<Branch> {
	
	public List<Branch> findByName(String name);
	
	public List<Branch> findDivisionBranches(Long divisionId);


}
