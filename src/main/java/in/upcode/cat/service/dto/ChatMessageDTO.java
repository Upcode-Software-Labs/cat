//package in.upcode.cat.service.dto;
//
//import java.util.Date;
//import java.util.Objects;
//
//public class ChatMessageDTO {
//    private String id;
//    private String message;
//    private UserDTO sender;
//    private UserDTO recipient;
//    private String roomId;
//    private Date timeStamp;
//
//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public UserDTO getSender() {
//        return sender;
//    }
//
//    public void setSender(UserDTO sender) {
//        this.sender = sender;
//    }
//
//    public UserDTO getRecipient() {
//        return recipient;
//    }
//
//    public void setRecipient(UserDTO recipient) {
//        this.recipient = recipient;
//    }
//
//    public String getRoomId() {
//        return roomId;
//    }
//
//    public void setRoomId(String roomId) {
//        this.roomId = roomId;
//    }
//
//    public Date getTimeStamp() {
//        return timeStamp;
//    }
//
//    public void setTimeStamp(Date timeStamp) {
//        this.timeStamp = timeStamp;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        ChatMessageDTO that = (ChatMessageDTO) o;
//        return Objects.equals(id, that.id) && Objects.equals(message, that.message) && Objects.equals(sender, that.sender) && Objects.equals(recipient, that.recipient) && Objects.equals(roomId, that.roomId) && Objects.equals(timeStamp, that.timeStamp);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, message, sender, recipient, roomId, timeStamp);
//    }
//}
