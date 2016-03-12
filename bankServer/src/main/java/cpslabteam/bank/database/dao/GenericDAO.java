package main.java.cpslabteam.bank.database.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

	T findById(ID id);

	List<T> findAll();

	List<T> findByExample(T exampleInstance, String[] excludeProperty);

	T makePersistent(T entity);

	void makeTransient(T entity);
}
