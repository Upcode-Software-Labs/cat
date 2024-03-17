package in.upcode.cat.service.dto;

import in.upcode.cat.domain.enumeration.AssessmentStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.UserAssessment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAssessmentDTO implements Serializable {

    private String id;

    @NotNull
    private AssessmentStatus status;

    @NotNull
    private Instant assignedAt;

    private Instant deadline;

    private UserDTO submittedByUser;

    private UserDTO user;

    private AssessmentDTO assessment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AssessmentStatus getStatus() {
        return status;
    }

    public void setStatus(AssessmentStatus status) {
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

    public UserDTO getSubmittedByUser() {
        return submittedByUser;
    }

    public void setSubmittedByUser(UserDTO submittedByUser) {
        this.submittedByUser = submittedByUser;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public AssessmentDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentDTO assessment) {
        this.assessment = assessment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAssessmentDTO)) {
            return false;
        }

        UserAssessmentDTO userAssessmentDTO = (UserAssessmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, userAssessmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAssessmentDTO{" +
            "id='" + getId() + "'" +
            ", status='" + getStatus() + "'" +
            ", assignedAt='" + getAssignedAt() + "'" +
            ", deadline='" + getDeadline() + "'" +
            ", submittedByUser=" + getSubmittedByUser() +
            ", user=" + getUser() +
            ", assessment=" + getAssessment() +
            "}";
    }
}
