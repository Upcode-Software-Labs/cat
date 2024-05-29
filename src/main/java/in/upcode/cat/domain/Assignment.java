package in.upcode.cat.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Assessment.
 */
@Document(collection = "assignment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Assignment extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("question")
    private String question;

    @Field("description")
    private String description;

    @Field
    private byte[] image;

    @Field
    private String url;

    @Field("technology")
    private String technology;

    @Field("difficulty_level")
    private String difficultyLevel;

    @Field("time_limit")
    private LocalTime timeLimit;

    @NotNull
    @DBRef
    @Field("type")
    private Category type;

    @NotNull
    @Field("max_points")
    private Integer maxPoints;

    @NotNull
    @Field("evaluation_type")
    private String evaluationType;

    @Field("deleted_at")
    private Instant deletedAt;

    @Field
    private boolean isDeleted;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    @Override
    public String getId() {
        return this.id;
    }

    public Assignment id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public Assignment description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Assignment technology(String technology) {
        this.setTechnology(technology);
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getTechnology() {
        return technology;
    }

    public byte[] getImage() {
        return image;
    }

    public Assignment image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public Assignment url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public Assignment evaluationType(String evaluationType) {
        this.setEvaluationType(evaluationType);
        return this;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public String getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public Assignment difficultyLevel(String difficultyLevel) {
        this.setDifficultyLevel(difficultyLevel);
        return this;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public LocalTime getTimeLimit() {
        return this.timeLimit;
    }

    public Assignment timeLimit(LocalTime timeLimit) {
        this.setTimeLimit(timeLimit);
        return this;
    }

    public void setTimeLimit(LocalTime timeLimit) {
        this.timeLimit = timeLimit;
    }

    public Category getType() {
        return this.type;
    }

    public Assignment type(Category type) {
        this.setType(type);
        return this;
    }

    public void setType(Category type) {
        this.type = type;
    }

    public String getQuestion() {
        return this.question;
    }

    public Assignment question(String question) {
        this.setQuestion(question);
        return this;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getMaxPoints() {
        return this.maxPoints;
    }

    public Assignment maxPoints(Integer maxPoints) {
        this.setMaxPoints(maxPoints);
        return this;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Assignment deletedAt(Instant deletedDate) {
        this.setDeletedAt(deletedDate);
        return this;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public Assignment isDeleted(Boolean isDeleted) {
        this.setIsDeleted(isDeleted);
        return this;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Assignment)) {
            return false;
        }
        return getId() != null && getId().equals(((Assignment) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Assignment{" +
            "id='" + id + '\'' +
            ", question='" + question + '\'' +
            ", description='" + description + '\'' +
            ", image=" + Arrays.toString(image) +
            ", url='" + url + '\'' +
            ", technology='" + technology + '\'' +
            ", difficultyLevel='" + difficultyLevel + '\'' +
            ", timeLimit=" + timeLimit +
            ", type=" + type +
            ", maxPoints=" + maxPoints +
            ", evaluationType='" + evaluationType + '\'' +
            ", deletedAt=" + deletedAt +
            ", isDeleted=" + isDeleted +
            '}';
    }
}
