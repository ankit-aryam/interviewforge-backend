- Why DTO is better than exposing Entity?
<br>
  DTO is used to decouple the API layer from the database layer. It prevents exposing internal entity structure, improves security by hiding sensitive fields, and allows flexible response formatting without affecting the database schema.


- What problem Page.map() solves? <br>
  Page.map() is used to convert entity objects into DTOs while preserving pagination metadata like total elements, total pages, and current page. It allows clean transformation without losing pagination information.



- Why validation is added at DTO layer?<br>
  Validation is added at DTO layer because DTO represents incoming client data. Validating at DTO ensures invalid data is rejected early, prevents invalid data from reaching business logic and database, and keeps validation logic clean and centralized.