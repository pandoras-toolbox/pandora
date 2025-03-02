package box.pandora.payroll;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "CUSTOMER_ORDER")
class Order {

    private @Id
    @GeneratedValue Long id;

    private String description;
    private Status status;

    Order() {
    }

    Order(String description, Status status) {
        this.description = description;
        this.status = status;
    }

    public Long getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Status getStatus() {
        return this.status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Order order)) {
            return false;
        } else {
            return Objects.equals(this.id, order.id) && Objects.equals(this.description, order.description)
                    && this.status == order.status;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.description, this.status);
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
