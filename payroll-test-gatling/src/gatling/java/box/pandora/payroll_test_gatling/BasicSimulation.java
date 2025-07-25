package box.pandora.payroll_test_gatling;

import io.gatling.javaapi.core.Assertion;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

// Copied from: https://github.com/gatling/gatling-gradle-plugin-demo-java/blob/main/src/gatling/java/example/BasicSimulation.java
// Prevent SonarQube warning: Only static class initializers should be used
@SuppressWarnings("java:S1171")
public class BasicSimulation extends Simulation {

    // Load VU count from system properties
    // Reference: https://docs.gatling.io/guides/passing-parameters/
    private static final int VU = Integer.getInteger("vu", 1);

    // Define HTTP configuration
    // Reference: https://docs.gatling.io/reference/script/protocols/http/protocol/
    private static final HttpProtocolBuilder httpProtocol = http.baseUrl("https://api-ecomm.gatling.io")
            .acceptHeader("application/json")
            .userAgentHeader(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/134.0.0.0 Safari/537.36");

    // Define scenario
    // Reference: https://docs.gatling.io/reference/script/core/scenario/
    private static final ScenarioBuilder scenario = scenario("Scenario")
            .exec(http("Session")
                    .get("/session"));

    // Define assertions
    // Reference: https://docs.gatling.io/reference/script/core/assertions/
    private static final Assertion assertion = global().failedRequests().count().lt(1L);

    // Define injection profile and execute the test
    // Reference: https://docs.gatling.io/reference/script/core/injection/
    {
        setUp(scenario
                .injectOpen(atOnceUsers(VU)))
                .assertions(assertion)
                .protocols(httpProtocol);
    }

}
