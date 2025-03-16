package box.pandora.payroll_test_okhttp;

import box.pandora.payroll_test_okhttp.client.OrderClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Payroll")
@Feature("Employee")
@ExtendWith(PayrollApiTestCallback.class)
final class EmployeeTest {

    private static final UUID SAMWISE_ID = UUID.fromString("43284f58-4b59-4c5f-8075-dc7d550e06de");

    @Story("Add employee")
    @Test
    void addEmployee() {
        var response = OrderClient.getEmployee(SAMWISE_ID);
        assertThat(response.isSuccessful())
                .as("response is successful")
                .isFalse();

        response = OrderClient.addEmployee("Samwise Gamgee", "gardener", SAMWISE_ID);
        assertThat(response.isSuccessful())
                .as("response is successful")
                .isTrue();
        assertThat(response.body().orElseThrow().toString())
                .as("response body")
                .contains(SAMWISE_ID.toString());

        response = OrderClient.getEmployee(SAMWISE_ID);
        assertThat(response.isSuccessful())
                .as("response is successful")
                .isTrue();
        assertThat(response.body().orElseThrow().toString())
                .as("response body")
                .contains(SAMWISE_ID.toString());
    }

}
