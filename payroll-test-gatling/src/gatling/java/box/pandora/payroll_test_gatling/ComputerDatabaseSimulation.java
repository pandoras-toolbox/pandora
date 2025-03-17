package box.pandora.payroll_test_gatling;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.util.concurrent.ThreadLocalRandom;

import static io.gatling.javaapi.core.CoreDsl.css;
import static io.gatling.javaapi.core.CoreDsl.csv;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.pause;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.repeat;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.tryMax;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://docs.gatling.io/tutorials/recorder/">Gatling quickstart tutorial</a>
 *   <li><a href="https://docs.gatling.io/tutorials/advanced/">Gatling advanced tutorial</a>
 * </ul>
 */
// Prevent SonarQube warning: Only static class initializers should be used
@SuppressWarnings("java:S1171")
public class ComputerDatabaseSimulation extends Simulation {

    FeederBuilder<String> feeder = csv("csv/search.csv").random();

    ChainBuilder search = exec(
            http("Home").get("/"),
            pause(1),
            feed(feeder),
            http("Search")
                    .get("/computers?f=#{searchCriterion}")
                    .check(
                            css("a:contains('#{searchComputerName}')", "href").saveAs("computerUrl")
                    ),
            pause(1),
            http("Select")
                    .get("#{computerUrl}")
                    .check(status().is(200)),
            pause(1)
    );

    // repeat is a loop resolved at RUNTIME
    ChainBuilder browse =
            // Note how we force the counter name, so we can reuse it
            repeat(4, "i").on(
                    http("Page #{i}").get("/computers?p=#{i}"),
                    pause(1)
            );

    // Note we should be using a feeder here
    // let's demonstrate how we can retry: let's make the request fail randomly and retry a given
    // number of times

    ChainBuilder edit =
            // let's try at max 2 times
            tryMax(2)
                    .on(
                            http("Form").get("/computers/new"),
                            pause(1),
                            http("Post")
                                    .post("/computers")
                                    .formParam("name", "Beautiful Computer")
                                    .formParam("introduced", "2012-05-30")
                                    .formParam("discontinued", "")
                                    .formParam("company", "37")
                                    .check(
                                            status().is(
                                                    // we do a check on a condition that's been customized with
                                                    // a lambda. It will be evaluated every time a user executes
                                                    // the request
                                                    session -> 200 + ThreadLocalRandom.current().nextInt(2)
                                            )
                                    )
                    )
                    // if the chain didn't finally succeed, have the user exit the whole scenario
                    .exitHereIfFailed();

    HttpProtocolBuilder httpProtocol =
            http.baseUrl("https://computer-database.gatling.io")
                    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .acceptLanguageHeader("en-US,en;q=0.5")
                    .acceptEncodingHeader("gzip, deflate")
                    .userAgentHeader(
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0"
                    );

    ScenarioBuilder users = scenario("Users").exec(search, browse);
    ScenarioBuilder admins = scenario("Admins").exec(search, browse, edit);

    {
        setUp(
                users.injectOpen(rampUsers(10).during(10)),
                admins.injectOpen(rampUsers(2).during(10))
        ).protocols(httpProtocol);
    }
}
