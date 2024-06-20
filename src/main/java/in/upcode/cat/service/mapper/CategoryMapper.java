package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Category;
import in.upcode.cat.service.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    CategoryDTO toDto(Category category);
}
