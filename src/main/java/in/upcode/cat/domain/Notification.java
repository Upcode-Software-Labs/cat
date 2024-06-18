package in.upcode.cat.domain;

import in.upcode.cat.domain.enumeration.NotificationStatus;
import in.upcode.cat.domain.enumeration.NotificationType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notification")
public class Notification extends AbstractAuditingEntity<String> implements Serializable {

    @Id
    private String id;

    @DBRef
    private User sender;

    @DBRef
    private User recipient;

    private String message;

    private Instant timestamp;

    private NotificationStatus status;

    // Constructors
    public Notification() {
        // Default constructor
    }

    @Override
    public String getId() {
        return id;
    }

    public Notification id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public Notification sender(User user) {
        this.setSender(user);
        return this;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public Notification recipient(User user) {
        this.setRecipient(user);
        return this;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public Notification message(String message) {
        this.setMessage(message);
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Notification timestamp(Instant timestamp) {
        this.setTimestamp(timestamp);
        return this;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public Notification status(NotificationStatus notificationStatus) {
        this.setStatus(notificationStatus);
        return this;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notification)) {
            return false;
        }
        return getId() != null && getId().equals(((Notification) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // toString method
    @Override
    public String toString() {
        return (
            "Notification{" +
            "id='" +
            id +
            '\'' +
            ", sender=" +
            sender +
            ", recipient=" +
            recipient +
            ", message='" +
            message +
            '\'' +
            ", timestamp=" +
            timestamp +
            ", status=" +
            status +
            '}'
        );
    }
}
