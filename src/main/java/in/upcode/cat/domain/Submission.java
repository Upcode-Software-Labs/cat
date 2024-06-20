package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Submission.
 */
@Document(collection = "submission")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Submission extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("github_url")
    private String githubUrl;

    @Field("screenshots")
    private byte[] screenshots;

    @Field("text_description")
    private String textDescription;

    @Field("feedback")
    private String feedback;

    @Field("points_scored")
    private Integer pointsScored;

    @Field("time_taken")
    private Instant timeTaken;

    @DBRef
    @Field
    private User user;

    @DBRef
    @Field
    private Assignment assignment;

    @DBRef
    @Field("forAssignment")
    @JsonIgnoreProperties(value = { "user", "assignment" }, allowSetters = true)
    private UserAssignment forAssignment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
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

    public Instant getTimeTaken() {
        return timeTaken;
    }

    public Submission timeTaken(Instant timeTaken) {
        this.timeTaken(timeTaken);
        return this;
    }

    public void setTimeTaken(Instant timeTaken) {
        this.timeTaken = timeTaken;
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

    public User getUser() {
        return user;
    }

    public Submission user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public Submission assignment(Assignment assignment) {
        this.setAssignment(assignment);
        return this;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public UserAssignment getForAssignment() {
        return this.forAssignment;
    }

    public void setForAssignment(UserAssignment userAssignment) {
        this.forAssignment = userAssignment;
    }

    public Submission forAssignment(UserAssignment userAssignment) {
        this.setForAssignment(userAssignment);
        return this;
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

    @Override
    public String toString() {
        return (
            "Submission{" +
            "id='" +
            id +
            '\'' +
            ", githubUrl='" +
            githubUrl +
            '\'' +
            ", screenshots=" +
            Arrays.toString(screenshots) +
            ", textDescription='" +
            textDescription +
            '\'' +
            ", feedback='" +
            feedback +
            '\'' +
            ", pointsScored=" +
            pointsScored +
            ", timeTaken=" +
            timeTaken +
            ", user=" +
            user +
            ", assignment=" +
            assignment +
            ", forAssignment=" +
            forAssignment +
            '}'
        );
    }
}
