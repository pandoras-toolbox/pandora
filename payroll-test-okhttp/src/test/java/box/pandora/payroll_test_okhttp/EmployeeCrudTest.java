package box.pandora.payroll_test_okhttp;

import box.pandora.functional_test.JsonPathEvaluator;
import box.pandora.payroll_test_okhttp.client.OrderClient;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static box.pandora.functional_test.allure.DynamicStep.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Payroll")
@Feature("Employee")
@ExtendWith(OkHttpTestCallback.class)
final class EmployeeCrudTest {

    @Story("Add, get and delete employee")
    @Test
    void addEmployee() {
        var randomUuid = UUID.randomUUID();

        var beginEmployeeCount = getEmployeeCount();

        getEmployeeExpectingFailure(randomUuid);

        step("add employee expecting success", () -> {
            var response = OrderClient.addEmployee("Samwise Gamgee", "gardener", randomUuid);
            assertThat(response.isSuccessful())
                    .as("response is successful")
                    .isTrue();
            assertThat(response.body().orElseThrow().toString())
                    .as("response body")
                    .contains(randomUuid.toString());
        });

        step("get employee expecting success", () -> {
            var response = OrderClient.getEmployee(randomUuid);
            assertThat(response.isSuccessful())
                    .as("response is successful")
                    .isTrue();
            assertThat(response.body().orElseThrow().toString())
                    .as("response body")
                    .contains(randomUuid.toString());
        });

        step("delete employee expecting success", () -> {
            var response = OrderClient.deleteEmployee(randomUuid);
            assertThat(response.isSuccessful())
                    .as("response is successful")
                    .isTrue();
        });

        getEmployeeExpectingFailure(randomUuid);

        step("check number of employees is same as before adding and deleting employee", () -> {
            var endEmployeeCount = getEmployeeCount();
            assertThat(endEmployeeCount)
                    .as("number of employees after adding and deleting an employee")
                    .isEqualTo(beginEmployeeCount);
        });
    }

    @Step
    private static int getEmployeeCount() {
        var res = OrderClient.getEmployees();
        return JsonPathEvaluator.parseJson(res.body().orElseThrow().string())
                .asArrayList("$._embedded.employeeList")
                .size();
    }

    @Step("get employee expecting failure")
    private static void getEmployeeExpectingFailure(UUID randomUuid) {
        var response = OrderClient.getEmployee(randomUuid);
        assertThat(response.isSuccessful())
                .as("response is successful")
                .isFalse();
    }

}
