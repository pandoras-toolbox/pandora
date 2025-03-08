# Pandora

Project status: *early phase, work in progress*

This is a test automation framework which is a box of tools for a specific purpose essentially.

It has its own example applications, one with REST APIs, but we will also create a frontend for it.

## Purpose

* show how selected open source tools can be chosen to create a useful test automation tool
* be an example for a programmatic approach to automation
* illustrate some newer technologies if they are better than previous ones
* demonstrate helpful techniques regarding test automation and the tooling for it
* serve as an initial template for a new test automation tool
* try out new libraries and approaches
* use it as a reference of craftsmanship

## Features

* test report generation
* behavior-driven development by using annotations
* running only certain groups of tests
* parallel test execution
* configuration via property files
* logging and capturing log output from dependencies
* data-driven testing with partly random input test data

## Test Types

* unit tests
* integration tests
* end-to-end tests, with REST APIs and web application tests (TODO)
* load tests (TODO)

## Out of Scope

It might change, but this is currently not planned:

* building project by a CI/CD system
* integration with Elasticsearch.
* test automation for mobile apps

## Architecture

**Java**

I am most familiar with Java, so I have chosen it, and I think it is a great programming language.

**Gradle**

In my opinion Gradle is better than Maven and more actively and continuously improved, although it is still not so
much adopted as you would expect.

**JUnit 5**

This is a well-designed test framework which supports nearly everything you need, also for end-to-end test purposes.

**Allure**

The free variant of Allure is nearly the best tool to create very useful test result reports. Only the paid variant is
better.

The integration of Allure with JUnit 5 is great. Compared to other frameworks like Cucumber, with Allure and its
flexible way of behavior-driven development the test reports are much more useful.

And there is no layer in a foreign language (Gherkin DSL) when using Allure because all tests can be automated by using
only a single programming language.

**Gatling**

TODO

**Apache Commons Configuration**

This library supports inheritance of property files, so that default property values can be overridden easily with
private property files.

Also, it allows reusing already defined properties, like: `fullName=${lastName}, ${firstName}`

**ArchUnit**

TODO

**Zed Attack Proxy**

TODO

## How to Use

First the [payroll](payroll/README.md) application has to be started.

Then API tests can be run from the [payroll-api-test](payroll-api-test/README.md) module.

The commands in the documentation will be explained only for Linux and MacOS. If you are a Windows user we recommend
that you install [WSL](https://learn.microsoft.com/en-us/windows/wsl/install) and use it for this project.

## Versions Checking

Check which versions of the dependencies can be updated from time to time with the
[gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin):

```shell
./gradlew dependencyUpdates -Drevision=release
```

## Links

* [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
* [Allure Test Report](http://allure.qatools.ru/)
* [Gatling](https://gatling.io/)
* [ArchUnit](https://www.archunit.org/)
* [Zed Attack Proxy](https://www.zaproxy.org/)
* [How to write a commit message that will make your mom proud](https://robertcooper.me/post/git-commit-messages/)
