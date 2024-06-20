package in.upcode.cat.service.dto;

import in.upcode.cat.domain.enumeration.NotificationStatus;
import in.upcode.cat.domain.enumeration.NotificationType;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

public class NotificationDTO implements Serializable {

    private String id;
    private UserDTO sender;
    private UserDTO recipient;
    private String message;
    private Instant timestamp;
    private NotificationStatus status;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDTO getSender() {
        return sender;
    }

    public void setSender(UserDTO sender) {
        this.sender = sender;
    }

    public UserDTO getRecipient() {
        return recipient;
    }

    public void setRecipient(UserDTO recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public void setStatus(NotificationStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificationDTO)) {
            return false;
        }

        NotificationDTO notificationDTO = (NotificationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "NotificationDTO{" +
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
