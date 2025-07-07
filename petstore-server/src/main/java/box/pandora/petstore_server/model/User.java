package box.pandora.petstore_server.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.data.annotation.Immutable;
import org.springframework.lang.Nullable;

import java.util.Objects;
import java.util.UUID;

@Entity
@Immutable
public class User {

    private @Id UUID id;

    private String username;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private @Nullable String phone;

    private @Nullable Integer userStatus;

    public User id(UUID id) {
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

    public User username(String username) {
        this.username = username;
        return this;
    }

    @Schema(name = "username", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Schema(name = "firstName", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public User lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Schema(name = "lastName", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public User email(String email) {
        this.email = email;
        return this;
    }

    @Schema(name = "email", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User password(String password) {
        this.password = password;
        return this;
    }

    @Schema(name = "password", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User phone(@Nullable String phone) {
        this.phone = phone;
        return this;
    }

    @Schema(name = "phone", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("phone")
    public @Nullable String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    public User userStatus(@Nullable Integer userStatus) {
        this.userStatus = userStatus;
        return this;
    }

    @Schema(name = "userStatus", description = "User Status", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("userStatus")
    public @Nullable Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(@Nullable Integer userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(this.id, user.id) &&
                Objects.equals(this.username, user.username) &&
                Objects.equals(this.firstName, user.firstName) &&
                Objects.equals(this.lastName, user.lastName) &&
                Objects.equals(this.email, user.email) &&
                Objects.equals(this.password, user.password) &&
                Objects.equals(this.phone, user.phone) &&
                Objects.equals(this.userStatus, user.userStatus);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, firstName, lastName, email, password, phone, userStatus);
    }

    @Override
    public String toString() {
        return "User {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    username: " + toIndentedString(username) + "\n" +
                "    firstName: " + toIndentedString(firstName) + "\n" +
                "    lastName: " + toIndentedString(lastName) + "\n" +
                "    email: " + toIndentedString(email) + "\n" +
                "    password: " + toIndentedString(password) + "\n" +
                "    phone: " + toIndentedString(phone) + "\n" +
                "    userStatus: " + toIndentedString(userStatus) + "\n" +
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
