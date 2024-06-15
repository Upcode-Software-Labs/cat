package in.upcode.cat.domain;

import io.mongock.utils.field.Field;
import java.io.Serializable;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("assignmentType")
    private String assignmentType;

    @Field("image")
    private byte[] image;

    public String getId() {
        return this.id;
    }

    public Category id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentType() {
        return this.assignmentType;
    }

    public Category assignmentType(String assignmentType) {
        this.setAssignmentType(assignmentType);
        return this;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Category image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    // @Override
    // public int hashCode() {
    //     final int prime = 31;
    //     int result = 1;
    //     result = prime * result + ((id == null) ? 0 : id.hashCode());
    //     result = prime * result + ((assignmentType == null) ? 0 : assignmentType.hashCode());
    //     result = prime * result + Arrays.hashCode(image);
    //     return result;
    // }

    // @Override
    // public boolean equals(Object obj) {
    //     if (this == obj)
    //         return true;
    //     if (obj == null)
    //         return false;
    //     if (getClass() != obj.getClass())
    //         return false;
    //     Category other = (Category) obj;
    //     if (id == null) {
    //         if (other.id != null)
    //             return false;
    //     } else if (!id.equals(other.id))
    //         return false;
    //     if (assignmentType == null) {
    //         if (other.assignmentType != null)
    //             return false;
    //     } else if (!assignmentType.equals(other.assignmentType))
    //         return false;
    //     if (!Arrays.equals(image, other.image))
    //         return false;
    //     return true;
    // }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }
        return getId() != null && getId().equals(((Category) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", assignmentType=" + assignmentType + ", image=" + Arrays.toString(image) + "]";
    }
}
