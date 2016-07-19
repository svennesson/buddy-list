package se.svennesson.svennelist.dao;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.model.User;

import java.util.List;
import java.util.Optional;

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

    public Optional<User> findByEmail(String email) {
        final String hql = "FROM User WHERE email = :email";
        final Query query = currentSession().createQuery(hql).setParameter("email", email);
        final List list = query.list();

        if (list == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((User) list.get(0));
    }
}
