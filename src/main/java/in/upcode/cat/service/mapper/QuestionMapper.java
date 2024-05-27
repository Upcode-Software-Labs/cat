package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.domain.Question;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.dto.QuestionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Question} and its DTO {@link QuestionDTO}.
 */
@Mapper(componentModel = "spring")
public interface QuestionMapper extends EntityMapper<QuestionDTO, Question> {
    @Mapping(target = "assessment", source = "assessment", qualifiedByName = "assessmentId")
    QuestionDTO toDto(Question s);

    @Named("assessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "deadline", source = "deadline")
    AssessmentDTO toDtoAssessmentId(Assessment assessment);
}
