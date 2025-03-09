# Pandora

**Status:** _5% complete_

This is a end-to-end test automation framework. Its approach is deliberately very programmatic in nature.

In this project there is an example application, one with REST APIs, but we will also create a frontend for it.

The command-line commands will be explained for Linux and macOS. If you are a Windows user we recommend that you
install [WSL](https://learn.microsoft.com/en-us/windows/wsl/install) and use it for this project.

## How to Use

Please take a look at these sub-readmes:

* [payroll-test-gatling](payroll-test-gatling/README.md)
* [payroll-test-okhttp](payroll-test-okhttp/README.md)
* [payroll-test-playwright](payroll-test-playwright/README.md)

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
* load tests

## Out of Scope

It might change, but this is currently not planned:

* building project by a CI/CD system
* integration with Elasticsearch.
* test automation for mobile apps

## Suggested 3rd Party Plugins IntelliJ

If you are using this project from IntelliJ, the suggested 3rd party plugins are:

* SonarLint
* GitToolbox
* Grep Console
* Rainbow Brackets
* JSON Sorter

## Versions Checking

Check which versions of the dependencies can be updated from time to time with the
[gradle-versions-plugin](https://github.com/ben-manes/gradle-versions-plugin):

```shell
./gradlew dependencyUpdates -Drevision=release
```

## Links

* [Architecture](doc/architecture.md)
* [How to write a commit message that will make your mom proud](https://robertcooper.me/post/git-commit-messages/)
