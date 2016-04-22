package cpslab.util.db;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import cpslab.util.db.hibernate.BaseDataEntity;

/**
 * A factory for creating DAO objects.
 * <p>
 * explicar
 */
public final class DAOFactory {

    /**
     * Maps data entity classes to DAO classes
     */
    private static Map<Class<?>, Class<?>> daoByDataEntity = new HashMap<>();

    /**
     * Utility class.
     */
    private DAOFactory() {
    }

    /**
     * Checks for default constructor.
     *
     * @param daoClass the dao class
     * @return true, if successful
     */
    private static boolean hasDefaultConstructor(Class<?> daoClass) {
        Constructor<?>[] constructors = daoClass.getConstructors();
        for (Constructor<?> c : constructors) {
            if (c.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a DAO object for a given the class of an entity object.
     *
     * @param <T> the generic type
     * @param entityClass the data class of the entity for which the corresponding DAO
     *            object has to be created
     * @return a new DAO for the entity class, if the entity class has previously been
     *         registered
     * @throws IllegalArgumentException if the entity class has not been previously
     *             associated to any DAO class; or if the class does not have a declared
     *             default constructor.
     */
    @SuppressWarnings("unchecked")
    public synchronized static <T extends BaseDataEntity> GenericDAO<T> create(Class<T> entityClass) {
        final Class<? extends GenericDAO<T>> daoClass =
            (Class<? extends GenericDAO<T>>) daoByDataEntity.get(entityClass);
        try {
            GenericDAO<T> dao = daoClass.newInstance();
            return dao;
        } catch (Exception e) {
            throw new IllegalArgumentException("Can not instantiate DAO for: " + entityClass, e);
        }
    }


    /**
     * Registers a DAO implementation for a given entity class.
     * <p>
     * <b>NOTE:</b> The DAO class must have default constructor declared.
     *
     * @param <T> the generic type
     * @param entityClass the class of the entity
     * @param daoClass the class of the entity to be created; must implement GenericDAO<>
     *            of the entity class
     * @throws IllegalArgumentException if a DAO is already registered for the given
     *             entity class
     */
    public synchronized static <T extends BaseDataEntity> void registerDao(Class<T> entityClass,
                                                                           Class<? extends GenericDAO<T>> daoClass) {
        if (daoByDataEntity.containsKey(entityClass)) {
            throw new IllegalArgumentException(
                    "Class " + entityClass.getSimpleName() + " already registerered to a DAO");
        }

        if (!hasDefaultConstructor(daoClass)) {
            throw new IllegalArgumentException("Class " + entityClass.getSimpleName()
                    + " has no declared default constructorO");
        }

        daoByDataEntity.put(entityClass, daoClass);
    }

}
