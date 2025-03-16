package box.pandora.payroll_backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
class LoadDatabase {

    private static final Logger LOGGER = LogManager.getLogger();

    @Bean
    CommandLineRunner initDatabase(EmployeeRepository employeeRepository, OrderRepository orderRepository) {
        return args -> {
            employeeRepository.save(new Employee(
                    UUID.fromString("381b11f2-1481-4023-8291-9c6ab8462eb4"),
                    "Bilbo",
                    "Baggins",
                    "burglar"));
            employeeRepository.save(new Employee(
                    UUID.fromString("708d8087-9260-4034-949b-123fc6c2dd16"),
                    "Frodo",
                    "Baggins",
                    "thief"));

            employeeRepository.findAll()
                    .forEach(employee -> LOGGER.info("Preloaded {}", employee));

            orderRepository.save(new Order(
                    UUID.fromString("b902797f-8656-44f6-9f99-92cb359cfc43"),
                    "MacBook Pro",
                    Status.COMPLETED));
            orderRepository.save(new Order(
                    UUID.fromString("f9f4b357-4611-44b8-9608-2e4b06ac2982"),
                    "iPhone",
                    Status.IN_PROGRESS));

            orderRepository.findAll()
                    .forEach(order -> LOGGER.info("Preloaded {}", order));
        };
    }

}
