package box.pandora.payroll_server;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class OrderController {

    private final OrderRepository repository;
    private final OrderModelAssembler assembler;

    OrderController(OrderRepository repository, OrderModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all orders")
    @GetMapping("/orders")
    CollectionModel<EntityModel<Order>> getOrders() {
        var orders = repository.findAll().stream()
                .map(assembler::toModel)
                .toList();
        return CollectionModel.of(orders,
                linkTo(methodOn(OrderController.class).getOrders()).withSelfRel());
    }

    @Operation(summary = "Get order by ID")
    @GetMapping("/orders/{id}")
    EntityModel<Order> getOrder(@PathVariable UUID id) {
        var order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return assembler.toModel(order);
    }

    @Operation(summary = "Create new order")
    @PostMapping("/orders")
    ResponseEntity<EntityModel<Order>> createNewOrder(@RequestBody Order order) {
        if (order.getId() == null) {
            order.setId(UUID.randomUUID());
        }
        order.setStatus(Status.IN_PROGRESS);
        var newOrder = repository.save(order);
        return ResponseEntity
                .created(linkTo(methodOn(OrderController.class).getOrder(newOrder.getId())).toUri())
                .body(assembler.toModel(newOrder));
    }

    @Operation(summary = "Complete order by ID")
    @PutMapping("/orders/{id}/complete")
    ResponseEntity<OrderResponse> completeOrder(@PathVariable UUID id) {
        var order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.COMPLETED);
            var savedOrder = repository.save(order);
            return ResponseEntity.ok(new OrderResponse(assembler.toModel(savedOrder)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(new OrderResponse(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't complete an order that is in the %s status"
                                .formatted(order.getStatus()))));
    }

    @Operation(summary = "Cancel order by ID")
    @DeleteMapping("/orders/{id}/cancel")
    ResponseEntity<OrderResponse> cancelOrder(@PathVariable UUID id) {
        var order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        if (order.getStatus() == Status.IN_PROGRESS) {
            order.setStatus(Status.CANCELLED);
            var savedOrder = repository.save(order);
            return ResponseEntity.ok(new OrderResponse(assembler.toModel(savedOrder)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE)
                .body(new OrderResponse(Problem.create()
                        .withTitle("Method not allowed")
                        .withDetail("You can't cancel an order that is in the %s status"
                                .formatted(order.getStatus()))));
    }

}
