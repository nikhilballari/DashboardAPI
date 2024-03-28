Technology Stack: Java17, Spring Boot, Spring Data JPA, Maven, MySQL database and Junit 5 as testing framework

Description :- This project has 2 end points to serve the client requests

                1) Fetch all Dashboard records
                2) Create new Dashboard record

1. Fetch all Dashboard Records
    - URI:GET  ->  http://localhost:8080/dashboards
    - This endpoint returns the list of dashboards by extracting the records from the database
2. Insert a new Dashboard Record
    - URI:POST ->  http://localhost:8080/dashboards
    - This endpoint inserts a new dashboard into the database