# Payroll API Tests with OkHttp

**Status:** _5% complete_

This module contains functional end-to-end API tests for the [payroll-backend](../payroll-backend/README.md)
application.

The payroll backend application must be started before the tests in this module can be run.

Examples how to start the tests on command-line:

```shell
./gradlew --project-dir payroll-test-okhttp clean test
./gradlew --project-dir payroll-test-okhttp clean test -PexcludedTags=architecture
./gradlew --project-dir payroll-test-okhttp clean test -PincludedTags=fast
./gradlew --project-dir payroll-test-okhttp clean test -PincludedTags=main -PexcludedTags=slow,flaky
```

How to create the Allure test report:

```shell
./gradlew --project-dir payroll-test-okhttp allureServe
```
