package in.upcode.cat.domain;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Assignment.
 */
@Document(collection = "assignment")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Assignment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question")
    private String question;

    @Field("description")
    private String description;

    @Field("image")
    private byte[] image;

    @Field("url")
    private String url;

    @Field("urlType")
    private String urlType;

    @NotNull
    @Field("technology")
    private String technology;

    @Field("difficultyLevel")
    private String difficultyLevel;

    @Field("timeLimit")
    private LocalTime timeLimit;

    @NotNull
    @Field("type")
    private Category type;

    @NotNull
    @Field("evaluationType")
    private String evaluationType;

    @NotNull
    @Field("maxPoints")
    private Integer maxPoints;

    // jhipster-needle-entity-add-field - JHipster will add fields here

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

    public byte[] getImage() {
        return this.image;
    }

    public Assignment image(byte[] string) {
        this.setImage(string);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUrl() {
        return this.url;
    }

    public Assignment url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlType() {
        return this.urlType;
    }

    public Assignment urlType(String urlType) {
        this.setUrlType(urlType);
        return this;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getTechnology() {
        return this.technology;
    }

    public Assignment technology(String technology) {
        this.setTechnology(technology);
        return this;
    }

    public void setTechnology(String technology) {
        this.technology = technology;
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

    public String getEvaluationType() {
        return this.evaluationType;
    }

    public Assignment evaluationType(String evaluationType) {
        this.setEvaluationType(evaluationType);
        return this;
    }

    public void setEvaluationType(String evaluationType) {
        this.evaluationType = evaluationType;
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

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     Assignment other = (Assignment) obj;
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
    public String toString() {
        return (
            "Assignment [id=" +
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
