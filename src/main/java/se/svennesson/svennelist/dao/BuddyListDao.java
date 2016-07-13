package se.svennesson.svennelist.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.model.BuddyList;

import java.util.List;

public class BuddyListDao extends AbstractEntityDao<BuddyList>{

    public BuddyListDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<BuddyList> getAllLists() {
        final String hql = "FROM BuddyList";
        final Query query = currentSession().createQuery(hql);
        return query.list();
    }
}
