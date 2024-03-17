package in.upcode.cat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.Question} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class QuestionDTO implements Serializable {

    private String id;

    private String questionText;

    private String codeSnippet;

    private String resources;

    private Integer points;

    private AssessmentDTO assessment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCodeSnippet() {
        return codeSnippet;
    }

    public void setCodeSnippet(String codeSnippet) {
        this.codeSnippet = codeSnippet;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public AssessmentDTO getAssessment() {
        return assessment;
    }

    public void setAssessment(AssessmentDTO assessment) {
        this.assessment = assessment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof QuestionDTO)) {
            return false;
        }

        QuestionDTO questionDTO = (QuestionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, questionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "QuestionDTO{" +
            "id='" + getId() + "'" +
            ", questionText='" + getQuestionText() + "'" +
            ", codeSnippet='" + getCodeSnippet() + "'" +
            ", resources='" + getResources() + "'" +
            ", points=" + getPoints() +
            ", assessment=" + getAssessment() +
            "}";
    }
}
