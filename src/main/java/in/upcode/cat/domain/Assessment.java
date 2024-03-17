package in.upcode.cat.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Assessment.
 */
@Document(collection = "assessment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Assessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("language_framework")
    private String languageFramework;

    @Field("difficulty_level")
    private String difficultyLevel;

    @Field("time_limit")
    private Integer timeLimit;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("validation_criteria")
    private String validationCriteria;

    @NotNull
    @Field("question")
    private String question;

    @NotNull
    @Field("max_points")
    private Integer maxPoints;

    @NotNull
    @Field("deadline")
    private ZonedDateTime deadline;

    @DBRef
    @Field("assignedToUser")
    private User assignedToUser;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Assessment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public Assessment title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public Assessment description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguageFramework() {
        return this.languageFramework;
    }

    public Assessment languageFramework(String languageFramework) {
        this.setLanguageFramework(languageFramework);
        return this;
    }

    public void setLanguageFramework(String languageFramework) {
        this.languageFramework = languageFramework;
    }

    public String getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public Assessment difficultyLevel(String difficultyLevel) {
        this.setDifficultyLevel(difficultyLevel);
        return this;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public Integer getTimeLimit() {
        return this.timeLimit;
    }

    public Assessment timeLimit(Integer timeLimit) {
        this.setTimeLimit(timeLimit);
        return this;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }

    public String getType() {
        return this.type;
    }

    public Assessment type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValidationCriteria() {
        return this.validationCriteria;
    }

    public Assessment validationCriteria(String validationCriteria) {
        this.setValidationCriteria(validationCriteria);
        return this;
    }

    public void setValidationCriteria(String validationCriteria) {
        this.validationCriteria = validationCriteria;
    }

    public String getQuestion() {
        return this.question;
    }

    public Assessment question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getMaxPoints() {
        return this.maxPoints;
    }

    public Assessment maxPoints(Integer maxPoints) {
        this.setMaxPoints(maxPoints);
        return this;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public ZonedDateTime getDeadline() {
        return this.deadline;
    }

    public Assessment deadline(ZonedDateTime deadline) {
        this.setDeadline(deadline);
        return this;
    }

    public void setDeadline(ZonedDateTime deadline) {
        this.deadline = deadline;
    }

    public User getAssignedToUser() {
        return this.assignedToUser;
    }

    public void setAssignedToUser(User user) {
        this.assignedToUser = user;
    }

    public Assessment assignedToUser(User user) {
        this.setAssignedToUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assessment)) {
            return false;
        }
        return getId() != null && getId().equals(((Assessment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Assessment{" +
            "id=" + getId() +
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
            "}";
    }
}
