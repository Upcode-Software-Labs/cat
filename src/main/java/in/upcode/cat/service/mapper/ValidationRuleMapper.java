package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.domain.ValidationRule;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.dto.ValidationRuleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ValidationRule} and its DTO {@link ValidationRuleDTO}.
 */
@Mapper(componentModel = "spring")
public interface ValidationRuleMapper extends EntityMapper<ValidationRuleDTO, ValidationRule> {
    @Mapping(target = "assessment", source = "assessment", qualifiedByName = "assessmentId")
    ValidationRuleDTO toDto(ValidationRule s);

    @Named("assessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssessmentDTO toDtoAssessmentId(Assessment assessment);
}
