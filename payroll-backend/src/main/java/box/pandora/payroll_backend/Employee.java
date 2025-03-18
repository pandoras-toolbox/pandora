package box.pandora.payroll_backend;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Immutable;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@Entity
@Immutable
class Employee {

    @Id
    private UUID id;

    @NotEmpty(message = "first name is required")
    private String firstName;

    @NotEmpty(message = "last name is required")
    private String lastName;

    private String role;

    public Employee() {
    }

    Employee(String firstName, String lastName, String role) {
        this(UUID.randomUUID(), firstName, lastName, role);
    }

    Employee(UUID id, String firstName, String lastName, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public String getName() {
        return "%s %s".formatted(firstName, lastName);
    }

    public void setName(String name) {
        var parts = name.split(" ");
        firstName = parts[0];
        lastName = parts[1];
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID uuid) {
        id = uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Employee employee)) {
            return false;
        } else {
            return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName)
                    && Objects.equals(lastName, employee.lastName) && Objects.equals(role, employee.role);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, role);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("firstName='" + firstName + "'")
                .add("lastName='" + lastName + "'")
                .add("role='" + role + "'")
                .toString();
    }

}
