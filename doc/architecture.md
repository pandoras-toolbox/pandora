# Architecture

## Decisions

**Java**

I am most familiar with [Java](https://www.java.com/), so I have chosen it, and I think it is a great programming
language. But maybe we will try out to use Kotlin for programming.

**Gradle**

In my opinion [Gradle](https://gradle.org/) is better than [Maven](https://maven.apache.org/) and more actively and
continuously improved, although it is still not so much adopted as you would expect.

**JUnit 5**

[JUnit 5](https://junit.org/junit5/docs/current/user-guide/) is a well-designed test framework which supports nearly
everything you need, also for end-to-end test purposes.

**Allure**

The free variant of [Allure](http://allure.qatools.ru/) is nearly the best tool to create very useful test result
reports. Only the paid variant "Allure TestOps" is better.

The integration of Allure with JUnit 5 is great. Compared to other frameworks like [Cucumber](https://cucumber.io/),
with Allure and its flexible way of behavior-driven development the test reports are much more useful.

And there is no layer in a foreign language (Gherkin DSL) when using Allure because all tests can be automated by using
only a single programming language.

**Gatling**

Overall, [Gatling](https://gatling.io/) is a fantastic tool to automate load tests, and in a programmatic way.

**Apache Commons Configuration**

This [library](https://commons.apache.org/proper/commons-configuration/) supports inheritance of property files, so that
default property values can be overridden easily with private property files.

Also, it allows reusing already defined properties, like: `fullName=${lastName}, ${firstName}`

**ArchUnit**

TODO https://www.archunit.org/

**Zed Attack Proxy**

TODO https://www.zaproxy.org/

**Immutables**

We believe [Immutables](https://immutables.github.io/) are far more useful than [Lombok](https://projectlombok.org/).
