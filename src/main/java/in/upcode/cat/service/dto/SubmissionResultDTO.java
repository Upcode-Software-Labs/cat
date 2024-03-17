package in.upcode.cat.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.SubmissionResult} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubmissionResultDTO implements Serializable {

    private String id;

    private Integer totalPoints;

    private String detailedResults;

    private String feedback;

    private SubmissionDTO submission;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getDetailedResults() {
        return detailedResults;
    }

    public void setDetailedResults(String detailedResults) {
        this.detailedResults = detailedResults;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public SubmissionDTO getSubmission() {
        return submission;
    }

    public void setSubmission(SubmissionDTO submission) {
        this.submission = submission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubmissionResultDTO)) {
            return false;
        }

        SubmissionResultDTO submissionResultDTO = (SubmissionResultDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, submissionResultDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubmissionResultDTO{" +
            "id='" + getId() + "'" +
            ", totalPoints=" + getTotalPoints() +
            ", detailedResults='" + getDetailedResults() + "'" +
            ", feedback='" + getFeedback() + "'" +
            ", submission=" + getSubmission() +
            "}";
    }
}
