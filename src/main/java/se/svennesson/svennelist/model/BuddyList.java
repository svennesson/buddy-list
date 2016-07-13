package se.svennesson.svennelist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.common.base.MoreObjects;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "buddy_list")
public class BuddyList extends AbstractEntity{

    @NotEmpty
    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String description;

    @JsonManagedReference("listItems")
    @OneToMany(mappedBy = "buddyList", fetch = FetchType.LAZY)
    List<Item> items = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "users_has_buddy_lists",
            joinColumns = @JoinColumn(name = "buddy_list_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    public BuddyList() {}

    public BuddyList(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public BuddyList(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuddyList buddyList = (BuddyList) o;
        return Objects.equals(title, buddyList.title) &&
                Objects.equals(description, buddyList.description) &&
                Objects.equals(users, buddyList.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("title", title)
                .add("description", description)
                .toString();
    }
}
