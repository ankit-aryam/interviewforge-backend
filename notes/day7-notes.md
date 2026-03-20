# Day 7 Notes – Testcontainers, Integration Testing, and Flyway in Tests

Testing is a critical part of backend development. Modern backend systems use **integration testing** with real dependencies like databases to ensure that application components work correctly together.

---

# 1️⃣ What Problem Does Testcontainers Solve?

### Definition

**Testcontainers** is a testing library that allows developers to run real services (like PostgreSQL, Redis, Kafka, etc.) inside **Docker containers during tests**.

This means tests run against **real infrastructure instead of mocks**.

---

## Problem Without Testcontainers

Traditionally, developers used:

```id="3i3nx9"
H2 in-memory database
Mock repositories
Fake services
```

But this creates problems.

Example:

```
Application → PostgreSQL
Tests → H2 Database
```

H2 behaves differently from PostgreSQL.

Possible issues:

```id="z0sb7t"
SQL syntax differences
Index behavior differences
Transaction handling differences
JSON column support differences
```

Tests may pass locally but fail in production.

---

## How Testcontainers Solves This

Testcontainers starts a **real database inside Docker** during tests.

Example flow:

```id="71o60e"
JUnit Test
     ↓
Testcontainers starts PostgreSQL container
     ↓
Spring connects to this container
     ↓
Tests run using real database
     ↓
Container automatically stops after tests
```

---

## Example Scenario

Repository test:

```id="lj2t33"
@Test
void shouldFindProblemsByTag() { ... }
```

Instead of mocking the database:

```id="95qny4"
Testcontainers → starts PostgreSQL
```

Now tests run exactly like production.

---

## Benefits

| Benefit                     | Explanation                             |
| --------------------------- | --------------------------------------- |
| Real Infrastructure         | Uses actual database                    |
| Production-like Environment | Tests behave like real system           |
| Automatic Setup             | Containers start and stop automatically |
| Isolation                   | Each test run has a fresh environment   |

---

# 2️⃣ Why Integration Tests Are Better Than Unit Tests for Repositories

### Unit Tests

Unit tests isolate a single class and often use mocks.

Example:

```id="3anvzg"
Mock ProblemRepository
Test service logic only
```

But this does **not test the real database behavior**.

Example issue:

```id="3nhp6c"
Incorrect JPQL query
Wrong table column name
Invalid SQL syntax
```

Unit tests will still pass because repository is mocked.

---

### Integration Tests

Integration tests use:

```id="zpe9z5"
Real database
Real repository
Real entity mappings
```

Example flow:

```id="fng6ki"
Test
 ↓
Repository
 ↓
JPA
 ↓
Database
```

Now we verify:

```id="63q67n"
Entity mappings
SQL queries
Indexes
Transactions
Specifications
```

This ensures the repository actually works with the database.

---

## Example

Testing repository:

```id="7mymrf"
problemRepository.findAll(specification)
```

Integration test verifies:

```id="7p1huh"
Correct SQL query
Correct filtering
Correct pagination
Correct sorting
```

Unit tests cannot guarantee this.

---

## Key Difference

| Type             | Tests                                |
| ---------------- | ------------------------------------ |
| Unit Test        | Individual class logic               |
| Integration Test | Multiple components working together |

For repositories, **integration testing is much more valuable**.

---

# 3️⃣ What Happens If Flyway Migration Fails During Tests?

### Role of Flyway

**Flyway** manages database schema migrations.

Example:

```id="a3b9gq"
V1__create_problem_table.sql
V2__add_index.sql
```

Before application startup, Flyway runs these migrations.

---

## What Happens During Tests

When integration tests start:

```id="5rrlza"
Testcontainer PostgreSQL starts
↓
Spring Boot initializes
↓
Flyway runs migrations
↓
Database schema is created
↓
Tests execute
```

---

## If Migration Fails

Example problems:

```id="9ypniz"
SQL syntax error
Duplicate column
Invalid index creation
Missing table reference
```

Then Flyway throws an exception.

Example error:

```id="0bfrk5"
FlywayException: Migration V2 failed
```

What happens next:

```id="ff4a0m"
Spring application context fails to start
Tests fail immediately
```

This is **actually beneficial** because it prevents running tests against an incorrect schema.

---

## Why This Is Good

If migrations fail early:

```id="h9p3b0"
Schema issues detected immediately
```

Developers fix migrations before deployment.

This prevents:

```id="9b8rkh"
Production database failures
Broken deployments
Runtime SQL errors
```

---

# Key Takeaways

| Concept             | Explanation                                               |
| ------------------- | --------------------------------------------------------- |
| Testcontainers      | Runs real services like PostgreSQL in Docker during tests |
| Integration Testing | Tests real database interaction                           |
| Flyway in Tests     | Automatically applies schema migrations                   |
| Migration Failure   | Stops application startup and fails tests                 |

---

# Interview Summary

### What problem does Testcontainers solve?

Testcontainers allows tests to run against real infrastructure such as PostgreSQL or Redis using Docker containers, ensuring tests behave similarly to production environments.

---

### Why integration tests are better than unit tests for repositories?

Integration tests verify real database interactions including entity mappings, SQL queries, and transactions, while unit tests with mocks cannot detect database-related issues.

---

### What happens if Flyway migration fails during tests?

If a Flyway migration fails, the Spring application context fails to start and tests immediately stop. This prevents tests from running with an incorrect or incomplete database schema.

---
