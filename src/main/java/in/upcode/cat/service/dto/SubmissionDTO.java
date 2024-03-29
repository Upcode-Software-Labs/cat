package in.upcode.cat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.Submission} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubmissionDTO implements Serializable {

    private String id;

    @NotNull
    private String githubUrl;

    private byte[] screenshots;

    private String screenshotsContentType;
    private String videoExplanation;

    private String textDescription;

    private String feedback;

    private Integer pointsScored;

    private UserAssessmentDTO forAssignment;

    private UserDTO user;

    private AssessmentDTO assessment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public byte[] getScreenshots() {
        return screenshots;
    }

    public void setScreenshots(byte[] screenshots) {
        this.screenshots = screenshots;
    }

    public String getScreenshotsContentType() {
        return screenshotsContentType;
    }

    public void setScreenshotsContentType(String screenshotsContentType) {
        this.screenshotsContentType = screenshotsContentType;
    }

    public String getVideoExplanation() {
        return videoExplanation;
    }

    public void setVideoExplanation(String videoExplanation) {
        this.videoExplanation = videoExplanation;
    }

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getPointsScored() {
        return pointsScored;
    }

    public void setPointsScored(Integer pointsScored) {
        this.pointsScored = pointsScored;
    }

    public UserAssessmentDTO getForAssignment() {
        return forAssignment;
    }

    public void setForAssignment(UserAssessmentDTO forAssignment) {
        this.forAssignment = forAssignment;
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
        if (!(o instanceof SubmissionDTO)) {
            return false;
        }

        SubmissionDTO submissionDTO = (SubmissionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, submissionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubmissionDTO{" +
            "id='" + getId() + "'" +
            ", githubUrl='" + getGithubUrl() + "'" +
            ", screenshots='" + getScreenshots() + "'" +
            ", videoExplanation='" + getVideoExplanation() + "'" +
            ", textDescription='" + getTextDescription() + "'" +
            ", feedback='" + getFeedback() + "'" +
            ", pointsScored=" + getPointsScored() +
            ", forAssignment=" + getForAssignment() +
            ", user=" + getUser() +
            ", assessment=" + getAssessment() +
            "}";
    }
}
