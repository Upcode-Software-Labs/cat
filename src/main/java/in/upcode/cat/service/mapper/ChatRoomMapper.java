package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.ChatRoom;
import in.upcode.cat.domain.User;
import in.upcode.cat.service.dto.ChatRoomDTO;
import in.upcode.cat.service.dto.UserDTO;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper extends EntityMapper<ChatRoomDTO, ChatRoom> {
    @Named("toUserEntities")
    default List<User> toUserEntities(List<UserDTO> userDTOs) {
        return userDTOs.stream().map(this::toUserEntity).collect(Collectors.toList());
    }

    @BeanMapping(ignoreByDefault = true)
    User toUserEntity(UserDTO userDTO);

    @Override
    @Mapping(target = "members", qualifiedByName = "toUserEntities")
    ChatRoom toEntity(ChatRoomDTO chatRoomDTO);

    @Override
    ChatRoomDTO toDto(ChatRoom chatRoom);

    @Named("toUserDTOs")
    default List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(this::toDtoUserId).collect(Collectors.toList());
    }

    UserDTO toDtoUserId(User user);
}
