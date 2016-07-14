package se.svennesson.svennelist.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.util.Objects;

public class BasicCredentials {

    private final String email;
    private final String password;

    @JsonCreator
    public BasicCredentials(@JsonProperty("password") String password, @JsonProperty("email") String email) {
        this.password = Preconditions.checkNotNull(password);
        this.email = Preconditions.checkNotNull(email);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BasicCredentials that = (BasicCredentials) o;
        return Objects.equals(email, that.email) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("email", email)
                .add("password", password)
                .toString();
    }
}
