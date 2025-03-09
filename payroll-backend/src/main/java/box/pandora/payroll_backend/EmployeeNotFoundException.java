package box.pandora.payroll_backend;

class EmployeeNotFoundException extends RuntimeException {

    EmployeeNotFoundException(Long id) {
        super("Could not find employee %s".formatted(id));
    }

}
