Today:
- Setup Spring Boot + Postgres + Flyway <br> - Created spring project from spring initializer <br> - Added flyway and integrated postgres
- Why we use validate instead of update <br> We use validate instead of update because database schema is managed by Flyway. Hibernate is only used to validate that the entity model matches the database, preventing accidental or implicit schema changes in production.
- Why Flyway is mandatory in real projects <br> Because it keeps the record of every migration and helps to manage database in efficient way.