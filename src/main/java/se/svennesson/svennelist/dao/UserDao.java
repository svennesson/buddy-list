package se.svennesson.svennelist.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.model.User;

import java.util.List;

public class UserDao extends AbstractEntityDao<User>{

    public UserDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        final String hql = "FROM User";
        final Query query = currentSession().createQuery(hql);
        return query.list();
    }
}
