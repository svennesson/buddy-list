package se.svennesson.svennelist.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import io.dropwizard.validation.ValidationMethod;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "users")
@Entity
public class User extends AbstractEntity{

    @NotEmpty
    @Column(nullable = false)
    private String name;

    @NotEmpty
    @Column(nullable = false)
    private String email;

    @NotEmpty
    @Column(nullable = false)
    private String password;

    @Max(value = 200)
    @Column(nullable = true)
    private int age;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Role role;

    @ManyToMany(mappedBy = "users")
    List<BuddyList> lists = new ArrayList<>();

    @ValidationMethod(message = "Only 'USER' role is valid")
    @JsonIgnore
    public boolean isRoleValid() {
        return role.equals(Role.USER);
    }

    public User() {}

    public User(String name, String email, String password, int age, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
        this.role = role;
    }

    public User(Long id, String name, String email, String password, int age, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return age == user.age &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, password, age, role);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .add("password", password)
                .add("age", age)
                .add("role", role)
                .toString();
    }
}
