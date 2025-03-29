# Payroll Server

**Status:** _10% complete_

This is an example application with REST APIs to be used for test automation.

It is copied initially from the module "links" of the Spring Boot example project
[tut-rest](https://github.com/spring-guides/tut-rest).

There is also a [REST tutorial](https://spring.io/guides/tutorials/rest) from Spring Boot about it.

What was changed:

* add ability to set the ID (UUID) when creating new employees and orders, useful for testing

How to run the app:

```shell
./gradlew --project-dir payroll-server clean bootRun
```

When the app starts, you can immediately interact with it, as follows:

```shell
curl -v localhost:8080/employees
curl -v localhost:8080/employees/708d8087-9260-4034-949b-123fc6c2dd16
curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"id": "43284f58-4b59-4c5f-8075-dc7d550e06de", "name": "Samwise Gamgee", "role": "gardener"}'
curl -v -X PUT localhost:8080/employees/43284f58-4b59-4c5f-8075-dc7d550e06de -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
curl -v -X DELETE localhost:8080/employees/708d8087-9260-4034-949b-123fc6c2dd16
curl -v localhost:8080/orders
curl -v -X DELETE http://localhost:8080/orders/f9f4b357-4611-44b8-9608-2e4b06ac2982/cancel
```

## Documentation

Access the API documentation with Swagger UI: http://localhost:8080/swagger-ui/index.html

Download the OpenAPI YAML with: http://localhost:8080/v3/open-api.yaml

View Swagger JSON: http://localhost:8080/v3/api-docs
