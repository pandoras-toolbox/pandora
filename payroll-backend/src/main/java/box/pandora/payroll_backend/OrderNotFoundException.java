package box.pandora.payroll_backend;

import java.util.UUID;

class OrderNotFoundException extends RuntimeException {

    OrderNotFoundException(UUID id) {
        super("Could not find order %s".formatted(id));
    }

}
