//package in.upcode.cat.service.mapper;
//
//import in.upcode.cat.domain.ChatMessage;
//import in.upcode.cat.domain.ChatRoom;
//import in.upcode.cat.domain.Notification;
//import in.upcode.cat.domain.User;
//import in.upcode.cat.service.dto.ChatMessageDTO;
//import in.upcode.cat.service.dto.NotificationDTO;
//import in.upcode.cat.service.dto.UserDTO;
//import org.mapstruct.BeanMapping;
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Named;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Mapper(componentModel = "spring")
//public interface ChatMessageMapper extends EntityMapper<ChatMessageDTO, ChatMessage> {
//    @Mapping(target = "sender", qualifiedByName = "toUserDto")
//    @Mapping(target = "recipient", qualifiedByName = "toUserDto")
//    ChatMessageDTO toDto(ChatMessage chatMessage);
//
//    @Mapping(target = "sender", qualifiedByName = "toUserEntity")
//    @Mapping(target = "recipient", qualifiedByName = "toUserEntity")
//    ChatMessage toEntity(ChatMessageDTO chatMessageDTO);
//
//    @Named("toUserDto")
//    UserDTO toUserDto(User user);
//
//    @Named("toUserEntity")
//    User toUserEntity(UserDTO userDTO);
//
//    List<UserDTO> toUserDtoList(List<User> users);
//
//    List<User> toUserEntityList(List<UserDTO> userDTOs);
//
//
//}
