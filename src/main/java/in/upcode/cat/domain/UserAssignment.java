package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.upcode.cat.domain.enumeration.AssignmentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A UserAssessment.
 */
@Document(collection = "user_assignment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAssignment extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("status")
    private AssignmentStatus status;

    @NotNull
    @Field("assigned_at")
    private Instant assignedAt;

    @Field("deadline")
    private Instant deadline;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("assignment")
    private Assignment assignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public String getId() {
        return this.id;
    }

    public UserAssignment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AssignmentStatus getStatus() {
        return this.status;
    }

    public UserAssignment status(AssignmentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public Instant getAssignedAt() {
        return this.assignedAt;
    }

    public UserAssignment assignedAt(Instant assignedAt) {
        this.setAssignedAt(assignedAt);
        return this;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getDeadline() {
        return this.deadline;
    }

    public UserAssignment deadline(Instant deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAssignment user(User user) {
        this.setUser(user);
        return this;
    }

    public Assignment getAssignment() {
        return this.assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public UserAssignment assessment(Assignment assignment) {
        this.setAssignment(assignment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAssignment)) {
            return false;
        }
        return getId() != null && getId().equals(((UserAssignment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "UserAssignment{" +
            "id='" + id + '\'' +
            ", status=" + status +
            ", assignedAt=" + assignedAt +
            ", deadline=" + deadline +
            ", user=" + user +
            ", assignment=" + assignment +
            '}';
    }
}
