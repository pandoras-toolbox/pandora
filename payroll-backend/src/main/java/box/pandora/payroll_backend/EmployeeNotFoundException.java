package box.pandora.payroll_backend;

import java.util.UUID;

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(UUID id) {
        super("Could not find employee %s".formatted(id));
    }

}
