package box.pandora.payroll_server;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.mediatype.problem.Problem;

public class OrderResponse {

    private EntityModel<Order> entityModel;
    private Problem problem;

    public OrderResponse(EntityModel<Order> entityModel) {
        this.entityModel = entityModel;
    }

    public OrderResponse(Problem problem) {
        this.problem = problem;
    }

    public EntityModel<Order> getEntityModel() {
        return entityModel;
    }

    public Problem getProblem() {
        return problem;
    }

}