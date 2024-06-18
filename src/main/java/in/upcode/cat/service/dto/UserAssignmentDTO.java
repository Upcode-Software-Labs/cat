package in.upcode.cat.service.dto;

import in.upcode.cat.domain.enumeration.AssignmentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.UserAssignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAssignmentDTO implements Serializable {

    private String id;

    @NotNull
    private AssignmentStatus status;

    @NotNull
    private Instant assignedAt;

    private Instant deadline;

    // private UserDTO submittedByUser;

    private UserDTO user;

    private AssignmentDTO assignment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AssignmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }

    public Instant getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getDeadline() {
        return deadline;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    // public UserDTO getSubmittedByUser() {
    //     return submittedByUser;
    // }

    // public void setSubmittedByUser(UserDTO submittedByUser) {
    //     this.submittedByUser = submittedByUser;
    // }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public AssignmentDTO getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentDTO assignment) {
        this.assignment = assignment;
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
    //     UserAssignmentDTO other = (UserAssignmentDTO) obj;
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
        if (!(o instanceof UserAssignmentDTO)) {
            return false;
        }

        UserAssignmentDTO userAssignmentDTO = (UserAssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAssignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "UserAssignmentDTO [id=" +
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
}
