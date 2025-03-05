package box.pandora.payroll_api_test;

import box.pandora.functional_test.junit.BaseCallback;
import box.pandora.payroll_api_test.client.OrderHttpClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@Epic("Payroll")
@ExtendWith(BaseCallback.class)
final class EmployeeAddTest {

    @Feature("Employee")
    @Test
    void addEmployee() {
        LogManager.getLogger().info("JUST TO TEST IF LOGGING WORKS IN THIS MODULE");
        var response = OrderHttpClient.addEmployee("Samwise Gamgee", "gardener");
        assertThat(response.isSuccessful())
                .as("response")
                .isTrue();
    }

}
