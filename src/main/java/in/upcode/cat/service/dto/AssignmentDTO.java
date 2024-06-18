package in.upcode.cat.service.dto;

import in.upcode.cat.domain.Category;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.Assignment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class AssignmentDTO implements Serializable {

    private String id;

    @NotNull
    private String question;

    private String description;

    private byte[] image;

    private String url;

    private Integer urlType;

    @NotNull
    private String technology;

    @NotNull
    private String difficultyLevel;

    @NotNull
    private LocalTime timeLimit;

    @NotNull
    private CategoryDTO type;

    @NotNull
    private String evaluationType;

    private Integer maxPoints;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUrlType() {
        return urlType;
    }

    public void setUrlType(Integer urlType) {
        this.urlType = urlType;
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

    public CategoryDTO getType() {
        return type;
    }

    public void setType(CategoryDTO type) {
        this.type = type;
    }

    public String getEvaluationType() {
        return evaluationType;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
    }

    public Integer getMaxPoints() {
        return maxPoints;
    }

    public void setMaxPoints(Integer maxPoints) {
        this.maxPoints = maxPoints;
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((id == null) ? 0 : id.hashCode());
    //     result = prime * result + ((question == null) ? 0 : question.hashCode());
    //     result = prime * result + ((description == null) ? 0 : description.hashCode());
    //     result = prime * result + Arrays.hashCode(image);
    //     result = prime * result + ((url == null) ? 0 : url.hashCode());
    //     result = prime * result + ((urlType == null) ? 0 : urlType.hashCode());
    //     result = prime * result + ((technology == null) ? 0 : technology.hashCode());
    //     result = prime * result + ((difficultyLevel == null) ? 0 : difficultyLevel.hashCode());
    //     result = prime * result + ((timeLimit == null) ? 0 : timeLimit.hashCode());
    //     result = prime * result + ((type == null) ? 0 : type.hashCode());
    //     result = prime * result + ((evaluationType == null) ? 0 : evaluationType.hashCode());
    //     result = prime * result + ((maxPoints == null) ? 0 : maxPoints.hashCode());
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
    //     AssignmentDTO other = (AssignmentDTO) obj;
    //     if (id == null) {
    //         if (other.id != null)
    //             return false;
    //     } else if (!id.equals(other.id))
    //         return false;
    //     if (question == null) {
    //         if (other.question != null)
    //             return false;
    //     } else if (!question.equals(other.question))
    //         return false;
    //     if (description == null) {
    //         if (other.description != null)
    //             return false;
    //     } else if (!description.equals(other.description))
    //         return false;
    //     if (!Arrays.equals(image, other.image))
    //         return false;
    //     if (url == null) {
    //         if (other.url != null)
    //             return false;
    //     } else if (!url.equals(other.url))
    //         return false;
    //     if (urlType == null) {
    //         if (other.urlType != null)
    //             return false;
    //     } else if (!urlType.equals(other.urlType))
    //         return false;
    //     if (technology == null) {
    //         if (other.technology != null)
    //             return false;
    //     } else if (!technology.equals(other.technology))
    //         return false;
    //     if (difficultyLevel == null) {
    //         if (other.difficultyLevel != null)
    //             return false;
    //     } else if (!difficultyLevel.equals(other.difficultyLevel))
    //         return false;
    //     if (timeLimit == null) {
    //         if (other.timeLimit != null)
    //             return false;
    //     } else if (!timeLimit.equals(other.timeLimit))
    //         return false;
    //     if (type == null) {
    //         if (other.type != null)
    //             return false;
    //     } else if (!type.equals(other.type))
    //         return false;
    //     if (evaluationType == null) {
    //         if (other.evaluationType != null)
    //             return false;
    //     } else if (!evaluationType.equals(other.evaluationType))
    //         return false;
    //     if (maxPoints == null) {
    //         if (other.maxPoints != null)
    //             return false;
    //     } else if (!maxPoints.equals(other.maxPoints))
    //         return false;
    //     return true;
    // }

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

    @Override
    public String toString() {
        return (
            "AssignmentDTO [id=" +
            id +
            ", question=" +
            question +
            ", description=" +
            description +
            ", image=" +
            Arrays.toString(image) +
            ", url=" +
            url +
            ", urlType=" +
            urlType +
            ", technology=" +
            technology +
            ", difficultyLevel=" +
            difficultyLevel +
            ", timeLimit=" +
            timeLimit +
            ", type=" +
            type +
            ", evaluationType=" +
            evaluationType +
            ", maxPoints=" +
            maxPoints +
            "]"
        );
    }
}
