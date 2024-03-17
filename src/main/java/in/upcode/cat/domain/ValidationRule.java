package in.upcode.cat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A ValidationRule.
 */
@Document(collection = "validation_rule")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ValidationRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("description")
    private String description;

    @Field("validation_script")
    private String validationScript;

    @NotNull
    @Field("rule_type")
    private String ruleType;

    @DBRef
    @Field("assessment")
    @JsonIgnoreProperties(value = { "assignedToUser" }, allowSetters = true)
    private Assessment assessment;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public ValidationRule id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return this.description;
    }

    public ValidationRule description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValidationScript() {
        return this.validationScript;
    }

    public ValidationRule validationScript(String validationScript) {
        this.setValidationScript(validationScript);
        return this;
    }

    public void setValidationScript(String validationScript) {
        this.validationScript = validationScript;
    }

    public String getRuleType() {
        return this.ruleType;
    }

    public ValidationRule ruleType(String ruleType) {
        this.setRuleType(ruleType);
        return this;
    }

    public void setRuleType(String ruleType) {
        this.ruleType = ruleType;
    }

    public Assessment getAssessment() {
        return this.assessment;
    }

    public void setAssessment(Assessment assessment) {
        this.assessment = assessment;
    }

    public ValidationRule assessment(Assessment assessment) {
        this.setAssessment(assessment);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ValidationRule)) {
            return false;
        }
        return getId() != null && getId().equals(((ValidationRule) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ValidationRule{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", validationScript='" + getValidationScript() + "'" +
            ", ruleType='" + getRuleType() + "'" +
            "}";
    }
}
