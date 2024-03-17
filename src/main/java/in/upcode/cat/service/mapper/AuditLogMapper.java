package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.AuditLog;
import in.upcode.cat.domain.User;
import in.upcode.cat.service.dto.AuditLogDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AuditLog} and its DTO {@link AuditLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface AuditLogMapper extends EntityMapper<AuditLogDTO, AuditLog> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userId")
    AuditLogDTO toDto(AuditLog s);

    @Named("userId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    UserDTO toDtoUserId(User user);
}
