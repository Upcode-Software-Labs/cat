package in.upcode.cat.domain;

import java.io.Serializable;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "category")
public class Category extends AbstractAuditingEntity<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("assignmentType")
    private String assignmentType;

    @Field("image")
    private byte[] image;

    @Override
    public String getId() {
        return id;
    }

    public Category id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssignmentType() {
        return assignmentType;
    }

    public Category assignmentType(String type) {
        this.setAssignmentType(type);
        return this;
    }

    public void setAssignmentType(String assignmentType) {
        this.assignmentType = assignmentType;
    }

    public byte[] getImage() {
        return image;
    }

    public Category image(byte[] image) {
        this.setImage(image);
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

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
        return "Category{" + "id='" + id + '\'' + ", assignmentType=" + assignmentType + ", image=" + Arrays.toString(image) + '}';
    }
}
