package in.upcode.cat.web.rest;

import in.upcode.cat.domain.User;
import in.upcode.cat.service.ChatRoomService;
import in.upcode.cat.service.EntityNotFoundException;
import in.upcode.cat.service.dto.ChatRoomDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat-rooms")
public class ChatRoomResource {

    private final ChatRoomService chatRoomService;

    public ChatRoomResource(ChatRoomService chatRoomService) {
        this.chatRoomService = chatRoomService;
    }

    @PostMapping
    public ResponseEntity<ChatRoomDTO> createChatRoom(@RequestBody ChatRoomDTO chatRoomDTO) {
        ChatRoomDTO createdChatRoom = chatRoomService.createChatRoom(chatRoomDTO);
        return ResponseEntity.ok(createdChatRoom);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomDTO>> getAllChatRooms() {
        List<ChatRoomDTO> chatRooms = chatRoomService.getAllChatRooms();
        return ResponseEntity.ok(chatRooms);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ChatRoomDTO> getChatRoomById(@PathVariable String roomId) throws EntityNotFoundException {
        ChatRoomDTO chatRoom = chatRoomService.getChatRoomById(roomId);
        return ResponseEntity.ok(chatRoom);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ChatRoomDTO> updateChatRoom(@PathVariable String roomId, @RequestBody ChatRoomDTO chatRoomDTO)
        throws EntityNotFoundException {
        ChatRoomDTO updatedRoom = chatRoomService.updateChatRoom(roomId, chatRoomDTO);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> deleteChatRoom(@PathVariable String roomId) {
        chatRoomService.deleteChatRoom(roomId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{roomId}/members")
    public ResponseEntity<ChatRoomDTO> addMembersToChatRoom(@PathVariable String roomId, @RequestBody List<User> members)
        throws EntityNotFoundException {
        ChatRoomDTO updatedRoom = chatRoomService.addMembersToChatRoom(roomId, members);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomId}/members/{userId}")
    public ResponseEntity<ChatRoomDTO> removeMemberFromChatRoom(@PathVariable String roomId, @PathVariable String userId)
        throws EntityNotFoundException {
        ChatRoomDTO updatedRoom = chatRoomService.removeMemberFromChatRoom(roomId, userId);
        return ResponseEntity.ok(updatedRoom);
    }
}
