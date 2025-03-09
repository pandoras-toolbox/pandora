package box.pandora.payroll_backend;

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
        rootModel.add(linkTo(methodOn(EmployeeController.class).all()).withRel("employees"));
        rootModel.add(linkTo(methodOn(OrderController.class).all()).withRel("orders"));
        return rootModel;
    }

}
