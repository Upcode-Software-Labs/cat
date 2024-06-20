package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Assignment;
import in.upcode.cat.domain.Category;
import in.upcode.cat.domain.User;
import in.upcode.cat.service.dto.AssignmentDTO;
import in.upcode.cat.service.dto.CategoryDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Assignment} and its DTO {@link AssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssignmentMapper extends EntityMapper<AssignmentDTO, Assignment> {
    @Mapping(target = "type", source = "type", qualifiedByName = "typeId")
    AssignmentDTO toDto(Assignment s);

    @Named("typeId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "assignmentType", source = "assignmentType")
    @Mapping(target = "image", source = "image")
    CategoryDTO toDtoCategoryId(Category category);
}
