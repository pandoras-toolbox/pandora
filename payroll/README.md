# Payroll Application

This is an example application to be used for test automation.

It is copied initially from the module "links" of the Spring Boot example project
[tut-rest](https://github.com/spring-guides/tut-rest).

There is also a [REST tutorial](https://spring.io/guides/tutorials/rest) from Spring Boot about it.

How to run the app:

```shell
./gradlew --project-dir payroll clean bootRun
```

When the app starts, you can immediately interact with it, as follows:

```
curl -v localhost:8080/employees
curl -v localhost:8080/employees/99
curl -v -X POST localhost:8080/employees -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "gardener"}'
curl -v -X PUT localhost:8080/employees/3 -H 'Content-Type:application/json' -d '{"name": "Samwise Gamgee", "role": "ring bearer"}'
curl -v -X DELETE localhost:8080/employees/1
curl -v http://localhost:8080/orders
curl -v -X DELETE http://localhost:8080/orders/4/cancel
```

## Documentation

Access the API documentation with Swagger UI: http://localhost:8080/swagger-ui/index.html
