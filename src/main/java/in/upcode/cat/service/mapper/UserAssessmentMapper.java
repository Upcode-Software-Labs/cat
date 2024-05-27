package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.domain.User;
import in.upcode.cat.domain.UserAssessment;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.dto.UserAssessmentDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAssessment} and its DTO {@link UserAssessmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserAssessmentMapper extends EntityMapper<UserAssessmentDTO, UserAssessment> {
    //@Mapping(target = "submittedByUser", source = "submittedByUser", qualifiedByName = "userId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "assessment", source = "assessment", qualifiedByName = "assessmentId")
    UserAssessmentDTO toDto(UserAssessment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("assessmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssessmentDTO toDtoAssessmentId(Assessment assessment);
}
