package in.upcode.cat.service.dto;

import in.upcode.cat.domain.UserAssignment;
import in.upcode.cat.domain.enumeration.AssignmentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link UserAssignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAssignmentDTO implements Serializable {

    private String id;

    @NotNull
    private AssignmentStatus status;

    @NotNull
    private Instant assignedAt;

    private Instant deadline;

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

    //    public UserDTO getSubmittedByUser() {
    //        return submittedByUser;
    //    }
    //
    //    public void setSubmittedByUser(UserDTO submittedByUser) {
    //        this.submittedByUser = submittedByUser;
    //    }

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

    // prettier-ignore

    @Override
    public String toString() {
        return "UserAssignmentDTO{" +
            "id='" + id + '\'' +
            ", status=" + status +
            ", assignedAt=" + assignedAt +
            ", deadline=" + deadline +
            ", user=" + user +
            ", assignment=" + assignment +
            '}';
    }
}
