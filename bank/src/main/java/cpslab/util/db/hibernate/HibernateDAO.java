package cpslab.util.db.hibernate;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;

import cpslab.util.db.GenericDAO;

public abstract class HibernateDAO<T> implements GenericDAO<T> {

	private Class<T> persistentClass;
	private Session session;

	@SuppressWarnings("unchecked")
	public HibernateDAO() {
		this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void setSession(Session s) {
		this.session = s;
	}

	protected Session getSession() {
		if (session == null)
			throw new IllegalStateException("Session has not been set on DAO before usage");
		return session;
	}

	public Class<T> getPersistentClass() {
		return persistentClass;
	}

	public T findById(Long id) {
		T object = (T) getSession().get(getPersistentClass(), id);
		if (object != null)
			return object;
		else
			throw new ObjectNotFoundException(id, getPersistentClass().getName());
	}

	public List<T> findAll() {
		return findByCriteria();
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T exampleInstance, String[] excludeProperty) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		Example example = Example.create(exampleInstance);
		for (String exclude : excludeProperty) {
			example.excludeProperty(exclude);
		}
		crit.add(example);
		return crit.list();
	}

	public T update(T entity) {
		getSession().update(entity);
		return entity;
	}

	public T persist(T entity) {
		getSession().persist(entity);
		return entity;
	}

	public void delete(T entity) {
		getSession().delete(entity);
	}

	protected void flush() {
		getSession().flush();
	}

	protected void clear() {
		getSession().clear();
	}

	@SuppressWarnings("unchecked")
	protected List<T> findByCriteria(Criterion... criterion) {
		Criteria crit = getSession().createCriteria(getPersistentClass());
		for (Criterion c : criterion) {
			crit.add(c);
		}
		return crit.list();
	}
}