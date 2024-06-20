package in.upcode.cat.service.dto;

import in.upcode.cat.domain.Assignment;
import in.upcode.cat.domain.Category;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A DTO for the {@link Assignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssignmentDTO implements Serializable {

    private String id;

    private String description;

    private String technology;

    private String difficultyLevel;

    private LocalTime timeLimit;

    private byte[] image;

    private String url;

    @NotNull
    private CategoryDTO type;

    @NotNull
    private String question;

    @NotNull
    private Integer maxPoints;

    @NotNull
    private String evaluationType;

    private Instant deletedAt;

    private boolean isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnology() {
        return technology;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public LocalTime getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(LocalTime timeLimit) {
        this.timeLimit = timeLimit;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public CategoryDTO getType() {
        return type;
    }

    public void setType(CategoryDTO type) {
        this.type = type;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssignmentDTO)) {
            return false;
        }

        AssignmentDTO assignmentDTO = (AssignmentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, assignmentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "AssignmentDTO{" +
            "id='" + id + '\'' +
            ", description='" + description + '\'' +
            ", technology='" + technology + '\'' +
            ", difficultyLevel='" + difficultyLevel + '\'' +
            ", timeLimit=" + timeLimit +
            ", url='" + url + '\'' +
            ", image='" + image + '\'' +
            ", type=" + type +
            ", question='" + question + '\'' +
            ", maxPoints=" + maxPoints +
            ", evaluationType='" + evaluationType + '\'' +
            ", deletedAt=" + deletedAt +
            ", isDeleted=" + isDeleted +
            '}';
    }

    public Assignment toEntity() {
        Assignment assignment = new Assignment();
        assignment.setId(id);
        assignment.setDescription(description);
        assignment.setTechnology(technology);
        assignment.setDifficultyLevel(difficultyLevel);
        assignment.setTimeLimit(timeLimit);
        assignment.setImage(image);
        assignment.setType(type.toEntity());
        assignment.setQuestion(question);
        assignment.setMaxPoints(maxPoints);
        assignment.setEvaluationType(evaluationType);
        assignment.deletedAt(deletedAt);
        assignment.isDeleted(isDeleted);
        assignment.url(url);
        return assignment;
    }
}
