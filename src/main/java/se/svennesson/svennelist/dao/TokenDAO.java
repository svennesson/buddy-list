package se.svennesson.svennelist.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.model.AccessToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TokenDAO extends AbstractEntityDao<AccessToken>{

    public TokenDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Optional<AccessToken> findAccessToken(final UUID token) {
        final String hql = "FROM AccessToken WHERE token = :token";
        final Query query = currentSession().createQuery(hql).setParameter("token", token);
        final List list = query.list();

        if (list == null) {
            return Optional.empty();
        }

        return Optional.ofNullable((AccessToken) list.get(0));
    }
}
