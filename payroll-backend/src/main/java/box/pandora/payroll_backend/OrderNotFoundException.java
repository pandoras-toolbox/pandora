package box.pandora.payroll_backend;

class OrderNotFoundException extends RuntimeException {

    OrderNotFoundException(Long id) {
        super("Could not find order %s".formatted(id));
    }

}
