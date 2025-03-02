package box.pandora.payroll_api_test;

import box.pandora.payroll_api_test.client.OrderClient;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

final class EmployeeAddTest {

    @Test
    void addEmployee() {
        var response = OrderClient.addEmployee("Samwise Gamgee", "gardener");
        assertThat(response.isSuccessful())
                .as("response")
                .isTrue();
    }

}
