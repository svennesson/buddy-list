package se.svennesson.svennelist.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.google.common.base.MoreObjects;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "item")
public class Item extends AbstractEntity{

    @NotEmpty
    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private boolean checked;

    @Column(nullable = true)
    private String store;

    @Column(nullable = true)
    private Integer quantity;

    @Column(nullable = true)
    private Long price;

    @JsonBackReference("listItems")
    @JoinColumn(name = "buddy_list_id")
    @ManyToOne
    private BuddyList buddyList;

    public Item() {}

    public Item(String text, boolean checked, String store, Integer quantity, Long price) {
        this.text = text;
        this.checked = checked;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }

    public Item(Long id, String text, boolean checked, String store, Integer quantity, Long price) {
        this.id = id;
        this.text = text;
        this.checked = checked;
        this.store = store;
        this.quantity = quantity;
        this.price = price;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public BuddyList getBuddyList() {
        return buddyList;
    }

    public void setBuddyList(BuddyList buddyList) {
        this.buddyList = buddyList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return checked == item.checked &&
                Objects.equals(text, item.text) &&
                Objects.equals(store, item.store) &&
                Objects.equals(quantity, item.quantity) &&
                Objects.equals(price, item.price) &&
                Objects.equals(buddyList, item.buddyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, checked, store, quantity, price, buddyList);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("text", text)
                .add("checked", checked)
                .add("store", store)
                .add("quantity", quantity)
                .add("price", price)
                .add("buddyList", buddyList)
                .toString();
    }
}
