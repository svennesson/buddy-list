package se.svennesson.svennelist.model;

import com.google.common.base.MoreObjects;
import org.joda.time.DateTime;

import javax.persistence.Column;
import java.util.Objects;
import java.util.UUID;

public class AccessToken extends AbstractEntity{

    @Column(nullable = false, name = "user_id")
    private final Long userId;

    @Column(nullable = false)
    private final UUID token;

    @Column(nullable = false, name = "date_issued")
    private final DateTime dateIssued;

    public AccessToken(Long userId, UUID token) {
        this.userId = userId;
        this.token = token;
        this.dateIssued = DateTime.now();
    }

    public Long getUserId() {
        return userId;
    }

    public UUID getToken() {
        return token;
    }

    public DateTime getDateIssued() {
        return dateIssued;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessToken that = (AccessToken) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(token, that.token) &&
                Objects.equals(dateIssued, that.dateIssued);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, token, dateIssued);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("token", token)
                .add("expires", dateIssued)
                .toString();
    }
}
