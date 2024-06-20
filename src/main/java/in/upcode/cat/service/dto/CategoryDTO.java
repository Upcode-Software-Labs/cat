package in.upcode.cat.service.dto;

import in.upcode.cat.domain.Category;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class CategoryDTO implements Serializable {

    private String id;
    private String assignmentType;
    private byte[] image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserAssignmentDTO)) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, categoryDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" + "id=" + id + ", assignmentType='" + assignmentType + '\'' + ", image=" + Arrays.toString(image) + '}';
    }

    public Category toEntity() {
        Category category = new Category();
        category.setId(id);
        category.setImage(image);
        category.setAssignmentType(assignmentType);
        return category;
    }
}
