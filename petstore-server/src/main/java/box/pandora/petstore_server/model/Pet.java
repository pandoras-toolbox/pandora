package box.pandora.petstore_server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.Immutable;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Immutable
public class Pet {

    private @Id UUID id;

    @ManyToOne
    @JoinColumn
    private Category category;

    private String name;

    @Valid
    private List<String> photoUrls = new ArrayList<>();

    @Valid
    private List<@Valid Tag> tags = new ArrayList<>();

    /**
     * pet status in the store
     */
    public enum StatusEnum {
        AVAILABLE("available"),

        PENDING("pending"),

        SOLD("sold");

        private final String value;

        StatusEnum(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }

        @JsonCreator
        public static StatusEnum fromValue(String value) {
            for (StatusEnum b : StatusEnum.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    private StatusEnum status;

    public Pet() {
        super();
    }

    /**
     * Constructor with only required parameters
     */
    public Pet(String name, List<String> photoUrls) {
        this.name = name;
        this.photoUrls = photoUrls;
    }

    public Pet id(UUID id) {
        this.id = id;
        return this;
    }

    @Schema(name = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Pet category(Category category) {
        this.category = category;
        return this;
    }

    @Valid
    @Schema(name = "category", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("category")
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Pet name(String name) {
        this.name = name;
        return this;
    }

    @NotNull
    @Schema(name = "name", example = "doggie", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pet photoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
        return this;
    }

    public Pet addPhotoUrlsItem(String photoUrlsItem) {
        if (this.photoUrls == null) {
            this.photoUrls = new ArrayList<>();
        }
        this.photoUrls.add(photoUrlsItem);
        return this;
    }

    @NotNull
    @Schema(name = "photoUrls", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("photoUrls")
    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }

    public Pet tags(List<@Valid Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Pet addTagsItem(Tag tagsItem) {
        if (this.tags == null) {
            this.tags = new ArrayList<>();
        }
        this.tags.add(tagsItem);
        return this;
    }

    @Valid
    @Schema(name = "tags", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("tags")
    public List<@Valid Tag> getTags() {
        return tags;
    }

    public void setTags(List<@Valid Tag> tags) {
        this.tags = tags;
    }

    public Pet status(StatusEnum status) {
        this.status = status;
        return this;
    }

    @Schema(name = "status", description = "pet status in the store", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("status")
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pet pet = (Pet) o;
        return Objects.equals(this.id, pet.id) &&
                Objects.equals(this.category, pet.category) &&
                Objects.equals(this.name, pet.name) &&
                Objects.equals(this.photoUrls, pet.photoUrls) &&
                Objects.equals(this.tags, pet.tags) &&
                this.status == pet.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, photoUrls, tags, status);
    }

    @Override
    public String toString() {
        return "Pet {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    category: " + toIndentedString(category) + "\n" +
                "    name: " + toIndentedString(name) + "\n" +
                "    photoUrls: " + toIndentedString(photoUrls) + "\n" +
                "    tags: " + toIndentedString(tags) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}
