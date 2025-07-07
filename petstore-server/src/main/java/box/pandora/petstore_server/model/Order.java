package box.pandora.petstore_server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Immutable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Immutable
@Entity
// Because "order" is a reserved keyword in SQL:
@Table(name = "CUSTOMER_ORDER")
public class Order {

    private @Id UUID id;

    private UUID petId;

    private Integer quantity;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private OffsetDateTime shipDate;

    private StatusEnum status;

    public enum StatusEnum {

        PLACED("placed"),
        APPROVED("approved"),
        DELIVERED("delivered");

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

    private Boolean complete;

    public Order id(UUID id) {
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

    public Order petId(UUID petId) {
        this.petId = petId;
        return this;
    }

    @Schema(name = "petId", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("petId")
    public UUID getPetId() {
        return petId;
    }

    public void setPetId(UUID petId) {
        this.petId = petId;
    }

    public Order quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    @Schema(name = "quantity", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("quantity")
    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order shipDate(@Nullable OffsetDateTime shipDate) {
        this.shipDate = shipDate;
        return this;
    }

    @Valid
    @Schema(name = "shipDate", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("shipDate")
    public @Nullable OffsetDateTime getShipDate() {
        return shipDate;
    }

    public void setShipDate(@Nullable OffsetDateTime shipDate) {
        this.shipDate = shipDate;
    }

    public Order status(StatusEnum status) {
        this.status = status;
        return this;
    }

    @Schema(name = "status", description = "Order Status", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("status")
    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Order complete(Boolean complete) {
        this.complete = complete;
        return this;
    }

    @Schema(name = "complete", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonProperty("complete")
    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(this.id, order.id) &&
                Objects.equals(this.petId, order.petId) &&
                Objects.equals(this.quantity, order.quantity) &&
                Objects.equals(this.shipDate, order.shipDate) &&
                this.status == order.status &&
                Objects.equals(this.complete, order.complete);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, petId, quantity, shipDate, status, complete);
    }

    @Override
    public String toString() {
        return "Order {\n" +
                "    id: " + toIndentedString(id) + "\n" +
                "    petId: " + toIndentedString(petId) + "\n" +
                "    quantity: " + toIndentedString(quantity) + "\n" +
                "    shipDate: " + toIndentedString(shipDate) + "\n" +
                "    status: " + toIndentedString(status) + "\n" +
                "    complete: " + toIndentedString(complete) + "\n" +
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
