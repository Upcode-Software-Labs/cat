package in.upcode.cat.service.dto;

import java.util.List;
import java.util.Objects;

public class ChatRoomDTO {

    private String id;
    private String name;
    private List<UserDTO> members;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserDTO> getMembers() {
        return members;
    }

    public void setMembers(List<UserDTO> members) {
        this.members = members;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoomDTO that = (ChatRoomDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(members, that.members);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, members);
    }
}
