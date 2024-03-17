package in.upcode.cat.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link in.upcode.cat.domain.ValidationRule} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ValidationRuleDTO implements Serializable {

    private String id;

    private String description;

    private String validationScript;

    @NotNull
    private String ruleType;

    private AssessmentDTO assessment;

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

    public String getValidationScript() {
        return validationScript;
    }

    public void setValidationScript(String validationScript) {
        this.validationScript = validationScript;
    }

    public String getRuleType() {
        return ruleType;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
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
        if (!(o instanceof ValidationRuleDTO)) {
            return false;
        }

        ValidationRuleDTO validationRuleDTO = (ValidationRuleDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, validationRuleDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValidationRuleDTO{" +
            "id='" + getId() + "'" +
            ", description='" + getDescription() + "'" +
            ", validationScript='" + getValidationScript() + "'" +
            ", ruleType='" + getRuleType() + "'" +
            ", assessment=" + getAssessment() +
            "}";
    }
}
