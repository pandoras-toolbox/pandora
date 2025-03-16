package box.pandora.payroll_backend;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface EmployeeRepository extends JpaRepository<Employee, UUID> {
}
