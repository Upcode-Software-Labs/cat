package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.upcode.cat.domain.enumeration.AssessmentStatus;
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
@Document(collection = "user_assessment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class UserAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("status")
    private AssessmentStatus status;

    @NotNull
    @Field("assigned_at")
    private Instant assignedAt;

    @Field("deadline")
    private Instant deadline;

    @DBRef
    @Field("submittedByUser")
    private User submittedByUser;

    @DBRef
    @Field("user")
    private User user;

    @DBRef
    @Field("assessment")
    @JsonIgnoreProperties(value = { "assignedToUser" }, allowSetters = true)
    private Assessment assessment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public UserAssessment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AssessmentStatus getStatus() {
        return this.status;
    }

    public UserAssessment status(AssessmentStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(AssessmentStatus status) {
        this.status = status;
    }

    public Instant getAssignedAt() {
        return this.assignedAt;
    }

    public UserAssessment assignedAt(Instant assignedAt) {
        this.setAssignedAt(assignedAt);
        return this;
    }

    public void setAssignedAt(Instant assignedAt) {
        this.assignedAt = assignedAt;
    }

    public Instant getDeadline() {
        return this.deadline;
    }

    public UserAssessment deadline(Instant deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(Instant deadline) {
        this.deadline = deadline;
    }

    public User getSubmittedByUser() {
        return this.submittedByUser;
    }

    public void setSubmittedByUser(User user) {
        this.submittedByUser = user;
    }

    public UserAssessment submittedByUser(User user) {
        this.setSubmittedByUser(user);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAssessment user(User user) {
        this.setUser(user);
        return this;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public UserAssessment assessment(Assessment assessment) {
        this.setAssessment(assessment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAssessment)) {
            return false;
        }
        return getId() != null && getId().equals(((UserAssessment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserAssessment{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", assignedAt='" + getAssignedAt() + "'" +
            ", deadline='" + getDeadline() + "'" +
            "}";
    }
}
