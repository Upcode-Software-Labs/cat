package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.domain.Submission;
import in.upcode.cat.domain.User;
import in.upcode.cat.domain.UserAssessment;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.dto.SubmissionDTO;
import in.upcode.cat.service.dto.UserAssessmentDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Submission} and its DTO {@link SubmissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubmissionMapper extends EntityMapper<SubmissionDTO, Submission> {
    @Mapping(target = "forAssignment", source = "forAssignment", qualifiedByName = "userAssessmentId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "assessment", source = "assessment", qualifiedByName = "assessmentId")
    SubmissionDTO toDto(Submission s);

    @Named("userAssessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserAssessmentDTO toDtoUserAssessmentId(UserAssessment userAssessment);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("assessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssessmentDTO toDtoAssessmentId(Assessment assessment);
}
