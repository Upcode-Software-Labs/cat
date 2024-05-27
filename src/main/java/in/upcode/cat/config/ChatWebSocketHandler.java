package in.upcode.cat.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final SimpMessagingTemplate messagingTemplate;
    private final Map<String, WebSocketSession> sessions = new HashMap<>();

    public ChatWebSocketHandler(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        // Handle incoming WebSocket messages here
        // You can parse the message content and send it to the appropriate recipient
        String payload = message.getPayload();
        String recipientId = parseRecipientId(payload);
        WebSocketSession recipientSession = sessions.get(recipientId);
        if (recipientSession != null && recipientSession.isOpen()) {
            messagingTemplate.convertAndSendToUser(recipientId, "/topic/chat", payload); // Send message to recipient
        } else {
            // Handle recipient not found or session closed
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // Handle WebSocket connection establishment here
        // You can store the session information or perform any necessary initialization
        String userId = extractUserId(session);
        sessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        // Handle WebSocket connection closure here
        // You can remove the session information or perform any necessary cleanup
        String userId = extractUserId(session);
        sessions.remove(userId);
    }

    private String extractUserId(WebSocketSession session) {
        // Extract user ID from WebSocket session
        // You may implement your own logic to extract user ID based on session attributes or authentication
        return session.getId();
    }

    private String parseRecipientId(String messagePayload) {
        // Parse recipient ID from message payload
        // You may implement your own logic to extract recipient ID from message content
        return ""; // Implement your logic here
    }
}
