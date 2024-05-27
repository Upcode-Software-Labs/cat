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
    @Mapping(target = "status", source = "status")
    @Mapping(target = "assignedAt", source = "assignedAt")
    @Mapping(target = "deadline", source = "deadline")
    UserAssessmentDTO toDtoUserAssessmentId(UserAssessment userAssessment);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    @Named("assessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "question", source = "question")
    @Mapping(target = "deadline", source = "deadline")
    @Mapping(target = "type", source = "type")
    AssessmentDTO toDtoAssessmentId(Assessment assessment);
}
