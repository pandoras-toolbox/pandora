package box.pandora.payroll_server;

import java.util.UUID;

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(UUID id) {
        super("Could not find employee %s".formatted(id));
    }

}
