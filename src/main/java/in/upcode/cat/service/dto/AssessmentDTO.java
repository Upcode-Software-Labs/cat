package in.upcode.cat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.Assessment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssessmentDTO implements Serializable {

    private String id;

    @NotNull
    private String title;

    private String description;

    private String languageFramework;

    private String difficultyLevel;

    private Integer timeLimit;

    @NotNull
    private String type;

    @NotNull
    private String validationCriteria;

    @NotNull
    private String question;

    @NotNull
    private Integer maxPoints;

    @NotNull
    private ZonedDateTime deadline;

    private UserDTO assignedToUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguageFramework() {
        return languageFramework;
    }

    public void setLanguageFramework(String languageFramework) {
        this.languageFramework = languageFramework;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidationCriteria() {
        return validationCriteria;
    }

    public void setValidationCriteria(String validationCriteria) {
        this.validationCriteria = validationCriteria;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public UserDTO getAssignedToUser() {
        return assignedToUser;
    }

    public void setAssignedToUser(UserDTO assignedToUser) {
        this.assignedToUser = assignedToUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssessmentDTO)) {
            return false;
        }

        AssessmentDTO assessmentDTO = (AssessmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assessmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AssessmentDTO{" +
            "id='" + getId() + "'" +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", languageFramework='" + getLanguageFramework() + "'" +
            ", difficultyLevel='" + getDifficultyLevel() + "'" +
            ", timeLimit=" + getTimeLimit() +
            ", type='" + getType() + "'" +
            ", validationCriteria='" + getValidationCriteria() + "'" +
            ", question='" + getQuestion() + "'" +
            ", maxPoints=" + getMaxPoints() +
            ", deadline='" + getDeadline() + "'" +
            ", assignedToUser=" + getAssignedToUser() +
            "}";
    }
}
