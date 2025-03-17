# Payroll UI Tests with Playwright

**Status:** _5% complete_

This module contains functional end-to-end UI tests using [Playwright](https://playwright.dev/).

Examples how to start the tests on command-line:

```shell
./gradlew --project-dir payroll-test-playwright clean test
./gradlew --project-dir payroll-test-playwright clean test -PexcludedTags=architecture
./gradlew --project-dir payroll-test-playwright clean test -PincludedTags=fast
./gradlew --project-dir payroll-test-playwright clean test -PincludedTags=main -PexcludedTags=slow,flaky
```

How to create the Allure test report:

```shell
./gradlew --project-dir payroll-test-playwright allureServe
```
