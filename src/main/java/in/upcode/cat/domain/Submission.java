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
public class Submission implements Serializable {

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

    @Field("timeTaken")
    private Instant timeTaken;

    @Field("feedback")
    private String feedback;

    @Field("points_scored")
    private Integer pointsScored;

    @DBRef
    @Field("forAssignment")
    private UserAssignment forAssignment;

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

    public Instant getTimeTaken() {
        return this.timeTaken;
    }

    public Submission timeTaken(Instant timeTaken) {
        this.setTimeTaken(timeTaken);
        return this;
    }

    public void setTimeTaken(Instant timeTaken) {
        this.timeTaken = timeTaken;
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

    public Assignment getAssignment() {
        return this.assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public Submission assignment(Assignment assignment) {
        this.setAssignment(assignment);
        return this;
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((id == null) ? 0 : id.hashCode());
    //     result = prime * result + ((githubUrl == null) ? 0 : githubUrl.hashCode());
    //     result = prime * result + Arrays.hashCode(screenshots);
    //     result = prime * result + ((textDescription == null) ? 0 : textDescription.hashCode());
    //     result = prime * result + ((timeTaken == null) ? 0 : timeTaken.hashCode());
    //     result = prime * result + ((feedback == null) ? 0 : feedback.hashCode());
    //     result = prime * result + ((pointsScored == null) ? 0 : pointsScored.hashCode());
    //     result = prime * result + ((forAssignment == null) ? 0 : forAssignment.hashCode());
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
    //     Submission other = (Submission) obj;
    //     if (id == null) {
    //         if (other.id != null)
    //             return false;
    //     } else if (!id.equals(other.id))
    //         return false;
    //     if (githubUrl == null) {
    //         if (other.githubUrl != null)
    //             return false;
    //     } else if (!githubUrl.equals(other.githubUrl))
    //         return false;
    //     if (!Arrays.equals(screenshots, other.screenshots))
    //         return false;
    //     if (textDescription == null) {
    //         if (other.textDescription != null)
    //             return false;
    //     } else if (!textDescription.equals(other.textDescription))
    //         return false;
    //     if (timeTaken == null) {
    //         if (other.timeTaken != null)
    //             return false;
    //     } else if (!timeTaken.equals(other.timeTaken))
    //         return false;
    //     if (feedback == null) {
    //         if (other.feedback != null)
    //             return false;
    //     } else if (!feedback.equals(other.feedback))
    //         return false;
    //     if (pointsScored == null) {
    //         if (other.pointsScored != null)
    //             return false;
    //     } else if (!pointsScored.equals(other.pointsScored))
    //         return false;
    //     if (forAssignment == null) {
    //         if (other.forAssignment != null)
    //             return false;
    //     } else if (!forAssignment.equals(other.forAssignment))
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
            "Submission [id=" +
            id +
            ", githubUrl=" +
            githubUrl +
            ", screenshots=" +
            Arrays.toString(screenshots) +
            ", textDescription=" +
            textDescription +
            ", timeTaken=" +
            timeTaken +
            ", feedback=" +
            feedback +
            ", pointsScored=" +
            pointsScored +
            ", forAssignment=" +
            forAssignment +
            ", user=" +
            user +
            ", assignment=" +
            assignment +
            "]"
        );
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

}
