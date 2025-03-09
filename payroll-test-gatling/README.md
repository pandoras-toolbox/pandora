# Payroll Load Tests

## Start Simulation(s)

```shell
./gradlew --project-dir payroll-test-gatling gatlingRun
```

A Gatling simulation can also be run with the class
[Engine](src/gatling/java/box/pandora/payroll_test_gatling/Engine.java)

## Reports

By default, the Gatling reports are in the folder: [build/reports](build/reports)

## Start Recorder

The recorder is *not* very useful in practice. It can be started with:

```shell
./gradlew --project-dir payroll-test-gatling gatlingRecorder
```

## Configure Logging

By default, logging of requests and responses is not enabled.

The log output of Gatling can be configured in: [logback.xml](src/gatling/resources/logback.xml)

## Don't Mix Gatling & OkHttp

Do **_not_** use OkHttp (or similar libraries) from within Gatling simulations!

It is bad to mix them together because of several reasons:

* OkHttp is *not* made for load testing.
* Gatling does not know anything of requests executed with OkHttp.
* The requests in OkHttp may slow down and distort load test executions.

Instead, implement the requests which you need *only* with Gatling.

Also, you should not use Gatling from requests which are implemented with OkHttp.

## Combining Gatling Reports

This is only relevant when the load test is distributed across multiple computers.

How to generate a report from all Gatling log files is described in the
sections "Gathering Results" and "Generating a Report":
[Distributed Performance Testing (Gatling)](https://www.baeldung.com/gatling-distributed-perf-testing#4-gathering-results)

## Default Configuration Files

The default configurations of Gatling were copied from the locations below and put into the
folder [resources](src/gatling/resources):

* [logback.dummy](https://github.com/gatling/gatling/blob/main/gatling-core/src/main/resources/logback.dummy)
* [gatling-defaults.conf](https://github.com/gatling/gatling/blob/main/gatling-core/src/main/resources/gatling-defaults.conf)
* [recorder-defaults.conf](https://github.com/gatling/gatling/blob/main/gatling-recorder/src/main/resources/recorder-defaults.conf)
