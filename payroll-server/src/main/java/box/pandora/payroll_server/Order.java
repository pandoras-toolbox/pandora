package box.pandora.payroll_server;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Immutable;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.UUID;

@Immutable
@Entity
// Because "order" is a reserved keyword in SQL:
@Table(name = "CUSTOMER_ORDER")
public class Order {

    @Id
    private UUID id;

    private String description;

    @NotNull(message = "status name is required")
    private Status status;

    public Order() {
    }

    Order(String description, Status status) {
        this(UUID.randomUUID(), description, status);
    }

    Order(UUID id, String description, Status status) {
        this.id = id;
        this.description = description;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Order order)) {
            return false;
        } else {
            return Objects.equals(id, order.id) && Objects.equals(description, order.description)
                    && status == order.status;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, status);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Order.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("description='" + description + "'")
                .add("status=" + status)
                .toString();
    }

}
