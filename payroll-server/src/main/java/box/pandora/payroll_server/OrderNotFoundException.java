package box.pandora.payroll_server;

import java.util.UUID;

class OrderNotFoundException extends RuntimeException {

    OrderNotFoundException(UUID id) {
        super("Could not find order %s".formatted(id));
    }

}
