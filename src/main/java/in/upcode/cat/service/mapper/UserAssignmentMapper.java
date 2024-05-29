package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assignment;
import in.upcode.cat.domain.User;
import in.upcode.cat.domain.UserAssignment;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.dto.UserAssignmentDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserAssignment} and its DTO {@link UserAssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface UserAssignmentMapper extends EntityMapper<UserAssignmentDTO, UserAssignment> {
    //@Mapping(target = "submittedByUser", source = "submittedByUser", qualifiedByName = "userId")
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    @Mapping(target = "assignment", source = "assignment", qualifiedByName = "assignmentId")
    UserAssignmentDTO toDto(UserAssignment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);

    @Named("assignmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AssignmentDTO toDtoAssignmentId(Assignment assignment);
}
