package in.upcode.cat.service;

import in.upcode.cat.domain.ChatRoom;
import in.upcode.cat.domain.User;
import in.upcode.cat.repository.ChatRoomRepository;
import in.upcode.cat.repository.UserRepository;
import in.upcode.cat.service.dto.ChatRoomDTO;
import in.upcode.cat.service.mapper.ChatRoomMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final ChatRoomMapper chatRoomMapper;

    public ChatRoomService(ChatRoomRepository chatRoomRepository, ChatRoomMapper chatRoomMapper, UserRepository userRepository) {
        this.chatRoomRepository = chatRoomRepository;
        this.chatRoomMapper = chatRoomMapper;
        this.userRepository = userRepository;
    }

    public ChatRoomDTO createChatRoom(ChatRoomDTO chatRoomDTO) {
        ChatRoom chatRoom = chatRoomMapper.toEntity(chatRoomDTO);
        chatRoom = chatRoomRepository.save(chatRoom);
        return chatRoomMapper.toDto(chatRoom);
    }

    public List<ChatRoomDTO> getAllChatRooms() {
        return chatRoomRepository.findAll().stream().map(chatRoomMapper::toDto).collect(Collectors.toList());
    }

    public ChatRoomDTO getChatRoomById(String roomId) throws EntityNotFoundException {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(roomId);
        if (chatRoomOptional.isPresent()) {
            return chatRoomMapper.toDto(chatRoomOptional.get());
        }
        throw new EntityNotFoundException("Chat room not found with id: " + roomId);
    }

    public ChatRoomDTO updateChatRoom(String roomId, ChatRoomDTO chatRoomDTO) throws EntityNotFoundException {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(roomId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            // Assuming you have a method in ChatRoomMapper to convert List<UserDTO> to List<User>
            List<User> members = chatRoomMapper.toUserEntities(chatRoomDTO.getMembers());

            chatRoom.setName(chatRoomDTO.getName());
            chatRoom.setMembers(members);
            chatRoom = chatRoomRepository.save(chatRoom);
            return chatRoomMapper.toDto(chatRoom);
        }
        throw new EntityNotFoundException("Chat room not found with id: " + roomId);
    }

    public void deleteChatRoom(String roomId) {
        chatRoomRepository.deleteById(roomId);
    }

    public ChatRoomDTO addMembersToChatRoom(String roomId, List<User> members) throws EntityNotFoundException {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(roomId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            List<User> existingMembers = chatRoom.getMembers();
            existingMembers.addAll(members);
            chatRoom.setMembers(existingMembers);
            chatRoom = chatRoomRepository.save(chatRoom);
            return chatRoomMapper.toDto(chatRoom);
        }
        throw new EntityNotFoundException("Chat room not found with id: " + roomId);
    }

    public ChatRoomDTO removeMemberFromChatRoom(String roomId, String userId) throws EntityNotFoundException {
        Optional<ChatRoom> chatRoomOptional = chatRoomRepository.findById(roomId);
        if (chatRoomOptional.isPresent()) {
            ChatRoom chatRoom = chatRoomOptional.get();
            List<User> existingMembers = chatRoom.getMembers();
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                User userToRemove = userOptional.get();
                existingMembers.remove(userToRemove);
                chatRoom.setMembers(existingMembers);
                chatRoom = chatRoomRepository.save(chatRoom);
                return chatRoomMapper.toDto(chatRoom);
            }
        }
        throw new EntityNotFoundException("Chat room not found with id: " + roomId);
    }
}
