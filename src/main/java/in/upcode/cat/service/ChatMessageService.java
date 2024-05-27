//package in.upcode.cat.service;
//
//import in.upcode.cat.domain.ChatMessage;
//import in.upcode.cat.repository.ChatMessageRepository;
//import in.upcode.cat.service.dto.ChatMessageDTO;
//import in.upcode.cat.service.mapper.ChatMessageMapper;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class ChatMessageService {
//    private final ChatMessageRepository chatMessageRepository;
//    private final ChatMessageMapper chatMessageMapper;
//
//    public ChatMessageService(ChatMessageRepository chatMessageRepository, ChatMessageMapper chatMessageMapper) {
//        this.chatMessageRepository = chatMessageRepository;
//        this.chatMessageMapper = chatMessageMapper;
//    }
//    // One-to-One Chat Service Methods
//
//    public ChatMessageDTO sendOneToOneChatMessage(ChatMessageDTO chatMessageDTO) {
//        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
//        chatMessage = chatMessageRepository.save(chatMessage);
//        return chatMessageMapper.toDto(chatMessage);
//    }
//
//    public List<ChatMessageDTO> getOneToOneChatMessages(String recipientId) {
//        // Fetch all chat messages where recipientId matches the provided recipientId
//        List<ChatMessage> chatMessages = chatMessageRepository.findByRecipientId(recipientId);
//        // Map the list of entities to DTOs
//        return chatMessages.stream()
//            .map(chatMessageMapper::toDto)
//            .collect(Collectors.toList());
//    }
//
//    // Group Chat Service Methods
//
//    public ChatMessageDTO sendGroupChatMessage(ChatMessageDTO chatMessageDTO) {
//        ChatMessage chatMessage = chatMessageMapper.toEntity(chatMessageDTO);
//        chatMessage = chatMessageRepository.save(chatMessage);
//        return chatMessageMapper.toDto(chatMessage);
//    }
//    public List<ChatMessageDTO> getGroupChatMessages(String roomId) {
//        // Fetch all chat messages where roomId matches the provided roomId
//        List<ChatMessage> chatMessages = chatMessageRepository.findByRoomId(roomId);
//        // Map the list of entities to DTOs
//        return chatMessages.stream()
//            .map(chatMessageMapper::toDto)
//            .collect(Collectors.toList());
//    }
//
//    // Common Service Methods
//
//    public ChatMessageDTO getChatMessageById(String messageId) {
//        // Fetch chat message by its ID
//        Optional<ChatMessage> chatMessageOptional = chatMessageRepository.findById(messageId);
//		// Handle case when chat message is not found
//		// Or throw an exception
//		return chatMessageOptional.map(chatMessageMapper::toDto).orElse(null);
//    }
//
//    public ChatMessageDTO updateChatMessage(String messageId, ChatMessageDTO chatMessageDTO) {
//        // Fetch chat message by its ID
//        Optional<ChatMessage> chatMessageOptional = chatMessageRepository.findById(messageId);
//        if (chatMessageOptional.isPresent()) {
//            ChatMessage chatMessage = chatMessageOptional.get();
//            // Update chat message content
//            chatMessage.setMessage(chatMessageDTO.getMessage());
//            // Save the updated chat message
//            chatMessage = chatMessageRepository.save(chatMessage);
//            return chatMessageMapper.toDto(chatMessage);
//        } else {
//            // Handle case when chat message is not found
//            return null; // Or throw an exception
//        }
//    }
//
//    public void deleteChatMessage(String messageId) {
//        // Delete chat message by its ID
//        chatMessageRepository.deleteById(messageId);
//    }
//}
