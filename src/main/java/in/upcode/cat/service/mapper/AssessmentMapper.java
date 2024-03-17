package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assessment;
import in.upcode.cat.domain.User;
import in.upcode.cat.service.dto.AssessmentDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assessment} and its DTO {@link AssessmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssessmentMapper extends EntityMapper<AssessmentDTO, Assessment> {
    @Mapping(target = "assignedToUser", source = "assignedToUser", qualifiedByName = "userId")
    AssessmentDTO toDto(Assessment s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
