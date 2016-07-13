package se.svennesson.svennelist.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import se.svennesson.svennelist.model.Item;

import java.util.List;

public class ItemDao extends AbstractEntityDao<Item>{

    public ItemDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @SuppressWarnings("unchecked")
    public List<Item> findAll() {
        final String hql = "FROM Item";
        final Query query = currentSession().createQuery(hql);
        return query.list();
    }
}
