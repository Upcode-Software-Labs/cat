package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assignment;
import in.upcode.cat.domain.Submission;
import in.upcode.cat.domain.User;
import in.upcode.cat.domain.UserAssignment;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.dto.SubmissionDTO;
import in.upcode.cat.service.dto.UserAssignmentDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Submission} and its DTO {@link SubmissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubmissionMapper extends EntityMapper<SubmissionDTO, Submission> {
    @Mapping(target = "forAssignment", source = "forAssignment", qualifiedByName = "userAssessmentId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "assignment", source = "assignment", qualifiedByName = "assignmentId")
    SubmissionDTO toDto(Submission s);

    @Named("userAssessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "assignedAt", source = "assignedAt")
    @Mapping(target = "deadline", source = "deadline")
    UserAssignmentDTO toDtoUserAssessmentId(UserAssignment userAssignment);

    @Named("userId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);

    @Named("assignmentId")
    @BeanMapping(ignoreByDefault = false)
    @Mapping(target = "id", source = "id")
    AssignmentDTO toDtoAssignmentId(Assignment assignment);
}
