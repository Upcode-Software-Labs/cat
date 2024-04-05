package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Submission.
 */
@Document(collection = "submission")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("github_url")
    private String githubUrl;

    @Field("screenshots")
    private byte[] screenshots;

    @Field("screenshots_content_type")
    private String screenshotsContentType;

    @Field("video_explanation")
    private String videoExplanation;

    @Field("text_description")
    private String textDescription;

    @Field("feedback")
    private String feedback;

    @Field("points_scored")
    private Integer pointsScored;

    @Field
    private String results;

    @DBRef
    @Field("forAssignment")
    @JsonIgnoreProperties(value = { "submittedByUser", "user", "assessment" }, allowSetters = true)
    private UserAssessment forAssignment;

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

    public Submission id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGithubUrl() {
        return this.githubUrl;
    }

    public Submission githubUrl(String githubUrl) {
        this.setGithubUrl(githubUrl);
        return this;
    }

    public void setGithubUrl(String githubUrl) {
        this.githubUrl = githubUrl;
    }

    public byte[] getScreenshots() {
        return this.screenshots;
    }

    public Submission screenshots(byte[] screenshots) {
        this.setScreenshots(screenshots);
        return this;
    }

    public void setScreenshots(byte[] screenshots) {
        this.screenshots = screenshots;
    }

    public String getScreenshotsContentType() {
        return this.screenshotsContentType;
    }

    public Submission screenshotsContentType(String screenshotsContentType) {
        this.screenshotsContentType = screenshotsContentType;
        return this;
    }

    public void setScreenshotsContentType(String screenshotsContentType) {
        this.screenshotsContentType = screenshotsContentType;
    }

    public String getVideoExplanation() {
        return this.videoExplanation;
    }

    public Submission videoExplanation(String videoExplanation) {
        this.setVideoExplanation(videoExplanation);
        return this;
    }

    public void setVideoExplanation(String videoExplanation) {
        this.videoExplanation = videoExplanation;
    }

    public String getTextDescription() {
        return this.textDescription;
    }

    public Submission textDescription(String textDescription) {
        this.setTextDescription(textDescription);
        return this;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public String getFeedback() {
        return this.feedback;
    }

    public Submission feedback(String feedback) {
        this.setFeedback(feedback);
        return this;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getPointsScored() {
        return this.pointsScored;
    }

    public Submission pointsScored(Integer pointsScored) {
        this.setPointsScored(pointsScored);
        return this;
    }

    public void setPointsScored(Integer pointsScored) {
        this.pointsScored = pointsScored;
    }

    public UserAssessment getForAssignment() {
        return this.forAssignment;
    }

    public void setForAssignment(UserAssessment userAssessment) {
        this.forAssignment = userAssessment;
    }

    public Submission forAssignment(UserAssessment userAssessment) {
        this.setForAssignment(userAssessment);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Submission user(User user) {
        this.setUser(user);
        return this;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Submission assessment(Assessment assessment) {
        this.setAssessment(assessment);
        return this;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Submission)) {
            return false;
        }
        return getId() != null && getId().equals(((Submission) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Submission{" +
            "id=" + getId() +
            ", githubUrl='" + getGithubUrl() + "'" +
            ", screenshots='" + getScreenshots() + "'" +
            ", screenshotsContentType='" + getScreenshotsContentType() + "'" +
            ", videoExplanation='" + getVideoExplanation() + "'" +
            ", textDescription='" + getTextDescription() + "'" +
            ", feedback='" + getFeedback() + "'" +
            ", pointsScored=" + getPointsScored() +
            "}";
    }
}
