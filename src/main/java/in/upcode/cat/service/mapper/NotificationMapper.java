package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Notification;
import in.upcode.cat.domain.User;
import in.upcode.cat.service.dto.NotificationDTO;
import in.upcode.cat.service.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NotificationMapper extends EntityMapper<NotificationDTO, Notification> {
    @Mapping(target = "sender", source = "sender", qualifiedByName = "id")
    @Mapping(target = "recipient", source = "recipient", qualifiedByName = "id")
    NotificationDTO toDto(Notification notification);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserId(User user);
}
