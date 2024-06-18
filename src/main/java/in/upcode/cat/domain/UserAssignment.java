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
 * A UserAssignment.
 */
@Document(collection = "user_assignment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAssignment implements Serializable {

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

    // @DBRef
    // @Field("submittedByUser")
    // private User submittedByUser;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("assignment")
    @JsonIgnoreProperties(value = { "assignedToUser" }, allowSetters = true)
    private Assignment assignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    // public User getSubmittedByUser() {
    //     return this.submittedByUser;
    // }

    // public void setSubmittedByUser(User user) {
    //     this.submittedByUser = user;
    // }

    // public UserAssignment submittedByUser(User user) {
    //     this.setSubmittedByUser(user);
    //     return this;
    // }

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

    public UserAssignment assignment(Assignment assignment) {
        this.setAssignment(assignment);
        return this;
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((id == null) ? 0 : id.hashCode());
    //     result = prime * result + ((status == null) ? 0 : status.hashCode());
    //     result = prime * result + ((assignedAt == null) ? 0 : assignedAt.hashCode());
    //     result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
    //     result = prime * result + ((user == null) ? 0 : user.hashCode());
    //     result = prime * result + ((assignment == null) ? 0 : assignment.hashCode());
    //     return result;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     UserAssignment other = (UserAssignment) obj;
    //     if (id == null) {
    //         if (other.id != null)
    //             return false;
    //     } else if (!id.equals(other.id))
    //         return false;
    //     if (status != other.status)
    //         return false;
    //     if (assignedAt == null) {
    //         if (other.assignedAt != null)
    //             return false;
    //     } else if (!assignedAt.equals(other.assignedAt))
    //         return false;
    //     if (deadline == null) {
    //         if (other.deadline != null)
    //             return false;
    //     } else if (!deadline.equals(other.deadline))
    //         return false;
    //     if (user == null) {
    //         if (other.user != null)
    //             return false;
    //     } else if (!user.equals(other.user))
    //         return false;
    //     if (assignment == null) {
    //         if (other.assignment != null)
    //             return false;
    //     } else if (!assignment.equals(other.assignment))
    //         return false;
    //     return true;
    // }

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

    @Override
    public String toString() {
        return (
            "UserAssignment [id=" +
            id +
            ", status=" +
            status +
            ", assignedAt=" +
            assignedAt +
            ", deadline=" +
            deadline +
            ", user=" +
            user +
            ", assignment=" +
            assignment +
            "]"
        );
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

}
