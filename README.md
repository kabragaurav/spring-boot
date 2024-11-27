# Spring Boot : A Simple Employee Management App


# Common Errors
1. 
```
{"timestamp":"2024-11-27T15:25:29.003+00:00","status":415,"error":"Unsupported Media Type","path":"/employee"}
```

Happens when the request's Content-Type is either missing or is not supported by the endpoint, especially for POST or PUT requests.

For a JSON payload (which is common for REST APIs), the header should be:

```
Content-Type: application/json
```
2. 
```
Field employeeService in com.example.demo.controller.EmployeeController required a bean of type 'com.example.demo.services.EmployeeService' that could not be found.

The injection point has the following annotations:
        - @org.springframework.beans.factory.annotation.Autowired(required=true)
```

Ensure that EmployeeServiceImpl class is annotated with @Service. Without this annotation, Spring won't recognize it as a bean.