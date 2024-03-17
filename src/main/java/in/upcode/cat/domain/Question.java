package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Question.
 */
@Document(collection = "question")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("question_text")
    private String questionText;

    @Field("code_snippet")
    private String codeSnippet;

    @Field("resources")
    private String resources;

    @Field("points")
    private Integer points;

    @DBRef
    @Field("assessment")
    @JsonIgnoreProperties(value = { "assignedToUser" }, allowSetters = true)
    private Assessment assessment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Question id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return this.questionText;
    }

    public Question questionText(String questionText) {
        this.setQuestionText(questionText);
        return this;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCodeSnippet() {
        return this.codeSnippet;
    }

    public Question codeSnippet(String codeSnippet) {
        this.setCodeSnippet(codeSnippet);
        return this;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public String getResources() {
        return this.resources;
    }

    public Question resources(String resources) {
        this.setResources(resources);
        return this;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Integer getPoints() {
        return this.points;
    }

    public Question points(Integer points) {
        this.setPoints(points);
        return this;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public Question assessment(Assessment assessment) {
        this.setAssessment(assessment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return getId() != null && getId().equals(((Question) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", questionText='" + getQuestionText() + "'" +
            ", codeSnippet='" + getCodeSnippet() + "'" +
            ", resources='" + getResources() + "'" +
            ", points=" + getPoints() +
            "}";
    }
}
