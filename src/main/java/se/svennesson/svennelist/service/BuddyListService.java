package se.svennesson.svennelist.service;

import org.hibernate.Hibernate;
import se.svennesson.svennelist.dao.BuddyListDao;
import se.svennesson.svennelist.dao.ItemDao;
import se.svennesson.svennelist.exception.EntityNotFoundException;
import se.svennesson.svennelist.exception.NotValidEntityException;
import se.svennesson.svennelist.model.BuddyList;
import se.svennesson.svennelist.model.Item;

import java.util.List;
import java.util.Objects;

import static se.svennesson.svennelist.util.EntityUtil.isValidEntity;

public class BuddyListService {

    private final BuddyListDao buddyListDao;
    private final ItemDao itemDao;

    public BuddyListService(BuddyListDao buddyListDao, ItemDao itemDao) {
        this.buddyListDao = buddyListDao;
        this.itemDao = itemDao;
    }

    public List<BuddyList> getAllLists() {
        return buddyListDao.getAllLists();
    }

    public BuddyList getListById(Long listId) {
        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        return buddyListDao.findById(listId);
    }

    public BuddyList getListWithItemsById(Long listId) {
        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        Hibernate.initialize(list.getItems());
        return list;
    }

    public BuddyList createList(BuddyList buddyList) {
        return buddyListDao.saveItem(buddyList);
    }

    public Item addItemToList(Long listId, Item item) {
        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        item.setBuddyList(list);
        return itemDao.saveItem(item);
    }

    public Item updateItem(Long listId, Long itemId, Item item) {
        if (!Objects.equals(item.getId(), itemId)) {
            throw new NotValidEntityException("Url item id and id in item does not match");
        }

        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        Item itemInDB = itemDao.findById(itemId);
        isValidEntity(itemInDB, itemId);
        itemInDB = cloneItem(itemInDB, item);

        return itemDao.saveItem(itemInDB);
    }

    public void deleteListById(Long listId) {
        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        list.getItems().stream().forEach(item -> itemDao.deleteById(item.getClass(), item.getId()));
        final int rowsDeleted = buddyListDao.deleteById(BuddyList.class, listId);

        if (rowsDeleted == 0) {
            throw new EntityNotFoundException("Entity not found with id " + listId);
        }
    }

    public void clearItemsFromList(Long listId) {
        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        list.getItems().stream().forEach(item -> itemDao.deleteById(item.getClass(), item.getId()));
    }

    public Object toggleItemChecked(Long listId, Long itemId) {
        final BuddyList list = buddyListDao.findById(listId);
        isValidEntity(list, listId);

        final Item item = itemDao.findById(itemId);
        isValidEntity(item, itemId);
        item.setChecked(!item.isChecked());

        return itemDao.saveItem(item);
    }

    private Item cloneItem(final Item persistedItem, final Item newItem) {
        persistedItem.setChecked(newItem.isChecked());
        persistedItem.setPrice(newItem.getPrice());
        persistedItem.setQuantity(newItem.getQuantity());
        persistedItem.setStore(newItem.getStore());
        persistedItem.setText(newItem.getText());

        return persistedItem;
    }
}
