package se.svennesson.svennelist.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.auth.TokenFactory;
import se.svennesson.svennelist.model.AccessToken;
import se.svennesson.svennelist.model.User;

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

    public int deleteByUserId(final Long userId) {
        final String hql = "DELETE AccessToken WHERE userId = :userId";
        final Query query = currentSession().createQuery(hql).setParameter("userId", userId);
        return query.executeUpdate();
    }

    public int deleteByToken(final UUID token) {
        final String hql = "DELETE AccessToken WHERE token = :token";
        final Query query = currentSession().createQuery(hql).setParameter("token", token);
        return query.executeUpdate();
    }

    public boolean checkIfAlreadyLoggedIn(final Long userId) {
        final String hql = "FROM AccessToken WHERE userId = :userId";
        final Query query = currentSession().createQuery(hql).setParameter("userId", userId);
        final List list = query.list();

        return !(list == null || list.isEmpty());
    }

    public AccessToken generateToken(final User user) {
        final UUID newUUID = TokenFactory.generateToken();
        final AccessToken newToken = new AccessToken(user.getId(), newUUID);

        return persist(newToken);
    }
}
