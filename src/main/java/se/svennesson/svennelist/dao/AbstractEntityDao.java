package se.svennesson.svennelist.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.model.AbstractEntity;

public class AbstractEntityDao<T extends AbstractEntity> extends AbstractDAO<T> {

    public AbstractEntityDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public T findById(Long id) {
        return get(id);
    }

    public T saveItem(T entity) {
        return persist(entity);
    }

    public int deleteById(Class entityClass, Long id) {
        final String hql = "DELETE " + entityClass.getName() + " WHERE id = :id";
        final Query query = currentSession().createQuery(hql).setParameter("id", id);
        return query.executeUpdate();
    }
}
