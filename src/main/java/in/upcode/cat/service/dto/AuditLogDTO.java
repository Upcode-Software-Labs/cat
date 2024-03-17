package in.upcode.cat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.AuditLog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AuditLogDTO implements Serializable {

    private String id;

    @NotNull
    private String action;

    @NotNull
    private Instant performedAt;

    private String details;

    private UserDTO user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Instant getPerformedAt() {
        return performedAt;
    }

    public void setPerformedAt(Instant performedAt) {
        this.performedAt = performedAt;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AuditLogDTO)) {
            return false;
        }

        AuditLogDTO auditLogDTO = (AuditLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, auditLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AuditLogDTO{" +
            "id='" + getId() + "'" +
            ", action='" + getAction() + "'" +
            ", performedAt='" + getPerformedAt() + "'" +
            ", details='" + getDetails() + "'" +
            ", user=" + getUser() +
            "}";
    }
}
