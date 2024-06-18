package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A SubmissionResult.
 */
@Document(collection = "submission_result")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubmissionResult implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("total_points")
    private Integer totalPoints;

    @Field("detailed_results")
    private String detailedResults;

    @Field("feedback")
    private String feedback;

    @DBRef
    @Field("submission")
    @JsonIgnoreProperties(value = { "forAssignment", "user", "assignment" }, allowSetters = true)
    private Submission submission;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public SubmissionResult id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalPoints() {
        return this.totalPoints;
    }

    public SubmissionResult totalPoints(Integer totalPoints) {
        this.setTotalPoints(totalPoints);
        return this;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getDetailedResults() {
        return this.detailedResults;
    }

    public SubmissionResult detailedResults(String detailedResults) {
        this.setDetailedResults(detailedResults);
        return this;
    }

    public void setDetailedResults(String detailedResults) {
        this.detailedResults = detailedResults;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public SubmissionResult feedback(String feedback) {
        this.setFeedback(feedback);
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Submission getSubmission() {
        return this.submission;
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public SubmissionResult submission(Submission submission) {
        this.setSubmission(submission);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubmissionResult)) {
            return false;
        }
        return getId() != null && getId().equals(((SubmissionResult) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubmissionResult{" +
            "id=" + getId() +
            ", totalPoints=" + getTotalPoints() +
            ", detailedResults='" + getDetailedResults() + "'" +
            ", feedback='" + getFeedback() + "'" +
            "}";
    }
}
