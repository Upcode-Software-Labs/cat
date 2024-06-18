package in.upcode.cat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Arrays;
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

    private String textDescription;

    private Instant timeTaken;

    private String feedback;

    private Integer pointsScored;

    private UserAssignmentDTO forAssignment;

    private UserDTO user;

    private AssignmentDTO assignment;

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

    public String getTextDescription() {
        return textDescription;
    }

    public void setTextDescription(String textDescription) {
        this.textDescription = textDescription;
    }

    public Instant getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Instant timeTaken) {
        this.timeTaken = timeTaken;
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

    public UserAssignmentDTO getForAssignment() {
        return forAssignment;
    }

    public void setForAssignment(UserAssignmentDTO forAssignment) {
        this.forAssignment = forAssignment;
    }

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
    //     SubmissionDTO other = (SubmissionDTO) obj;
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

    @Override
    public String toString() {
        return (
            "SubmissionDTO [id=" +
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
}
