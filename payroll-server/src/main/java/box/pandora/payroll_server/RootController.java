package box.pandora.payroll_server;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
class RootController {

    @GetMapping
    RepresentationModel<?> index() {
        RepresentationModel<?> rootModel = new RepresentationModel<>();
        rootModel.add(linkTo(methodOn(EmployeeController.class).getEmployees()).withRel("employees"));
        rootModel.add(linkTo(methodOn(OrderController.class).getOrders()).withRel("orders"));
        return rootModel;
    }

}
