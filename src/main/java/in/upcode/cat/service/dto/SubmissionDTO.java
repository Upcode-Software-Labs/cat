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

    private String feedback;

    private Integer pointsScored;

    private UserAssignmentDTO forAssignment;

    private Instant timeTaken;

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

    public Instant getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Instant timeTaken) {
        this.timeTaken = timeTaken;
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
            "id='" + id + '\'' +
            ", githubUrl='" + githubUrl + '\'' +
            ", screenshots=" + Arrays.toString(screenshots) +
            ", textDescription='" + textDescription + '\'' +
            ", feedback='" + feedback + '\'' +
            ", pointsScored=" + pointsScored +
            ", forAssignment=" + forAssignment +
            ", timeTaken=" + timeTaken +
            ", user=" + user +
            ", assignment=" + assignment +
            '}';
    }
}
