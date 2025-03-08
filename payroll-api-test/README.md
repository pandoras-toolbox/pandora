# Payroll API Tests

In this module there are functional end-to-end API tests for the [Payroll](../payroll/README.md) application.

The Payroll application must be started before the tests in this module can be run.

Examples how to start the tests on command-line:

```shell
./gradlew --project-dir payroll-api-test clean test
./gradlew --project-dir payroll-api-test clean test -PincludedTags=fast
./gradlew --project-dir payroll-api-test clean test -PexcludedTags=smoke
./gradlew --project-dir payroll-api-test clean test -PincludedTags=main -PexcludedTags=slow,flaky
```

How to create the Allure test report:

```shell
./gradlew --project-dir payroll-api-test allureServe
```
