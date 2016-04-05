package cpslabteam.bank.database.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDAO<T, ID extends Serializable> {

	T findById(ID id);
	
	T getById(ID id);
	
	void readIntoObject(T object, ID id);

	List<T> findAll();

	List<T> findByExample(T exampleInstance, String[] excludeProperty);
	
	T update(T entity);
	
	T persist(T entity);
	
	T saveOrUpdate(T entity);

	void makeTransient(T entity);
}
