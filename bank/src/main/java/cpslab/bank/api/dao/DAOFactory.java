package cpslab.bank.api.dao;

import java.util.HashMap;
import java.util.Map;

import cpslab.bank.api.entities.BaseDataObject;
import cpslab.util.db.GenericDAO;

public abstract class DAOFactory {
    private static Map<Class<?>, Class<?>> daoByDataObject = new HashMap<>();

    @SuppressWarnings("unchecked")
	public synchronized static <T extends BaseDataObject> GenericDAO<T> createDao(Class<T> dataClass) {
        final Class<? extends GenericDAO<T>> daoClass =
            (Class<? extends GenericDAO<T>>) daoByDataObject.get(dataClass);
        try {
            GenericDAO<T> dao = daoClass.newInstance();
            return dao;
        } catch (Exception e) {
            throw new RuntimeException("Can not instantiate DAO: " + daoClass, e);
        }
    }

    public synchronized static <T extends BaseDataObject> void addDaoFactory(Class<T> dataClass,
                                                                             Class<? extends GenericDAO<T>> daoClass) {
        if (daoByDataObject.containsKey(dataClass)) {
            throw new IllegalArgumentException(
                    "Class " + dataClass.getSimpleName() + " already registerered to a DAO");
        }
        daoByDataObject.put(dataClass, daoClass);
    }
}
