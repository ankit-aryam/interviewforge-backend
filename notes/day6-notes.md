# Day 6 Notes – Logging in Production Systems

Logging is a critical part of backend systems. It helps developers understand system behavior, debug issues, monitor performance, and investigate failures in production environments.

---

# 1️⃣ Why Logging is Critical in Production Systems

### Definition

Logging is the process of recording important events, system activities, errors, and operational details while the application is running.

In production systems, logs act as the **primary source of truth** for understanding what happened inside the system.

---

## Why Logging is Important

### 1. Debugging Production Issues

In production environments, developers usually cannot attach a debugger or inspect the system directly.

Logs help identify:

```
Which API was called
What parameters were passed
Where the error occurred
```

Example:

```
Fetching problems difficulty=EASY tag=array
```

---

### 2. Monitoring System Behavior

Logs help track how the application behaves in real-time.

Example:

```
User login events
API requests
Background job execution
Cache hits/misses
```

These logs help detect unusual patterns or performance problems.

---

### 3. Performance Analysis

Logs can reveal slow operations.

Example:

```
Database query execution
External API calls
Cache lookups
```

Performance bottlenecks can be identified using timestamps in logs.

---

### 4. Security Auditing

Logs help track suspicious activities.

Example:

```
Multiple failed login attempts
Unauthorized API access
Admin actions
```

These logs are essential for security monitoring.

---

### 5. Production Incident Investigation

When something breaks in production, logs are the **first place engineers look**.

Example:

```
500 Internal Server Error
Database connection failure
Redis timeout
```

Without logs, debugging production issues becomes extremely difficult.

---

# 2️⃣ Difference Between log.info(), log.debug(), log.error()

Logging frameworks such as **SLF4J with Logback** provide multiple log levels.

Each level represents a different importance level.

---

## log.info()

Used for **important application events**.

These logs describe normal system operations that are useful to track in production.

Example:

```java
log.info("Creating new problem title={}", request.getTitle());
```

Typical use cases:

```
Application startup
User login
Resource creation
Business events
```

These logs are usually enabled in production.

---

## log.debug()

Used for **detailed debugging information**.

These logs help developers understand internal application behavior during development or troubleshooting.

Example:

```java
log.debug("Specification generated for difficulty={} tag={}", difficulty, tag);
```

Typical use cases:

```
Internal logic flow
Variable values
Intermediate calculations
Detailed query information
```

Debug logs are usually **disabled in production** because they generate too much data.

---

## log.error()

Used for **unexpected failures or critical errors**.

These logs indicate that something went wrong in the application.

Example:

```java
log.error("Failed to create problem", ex);
```

Typical use cases:

```
Database failures
Unhandled exceptions
External API failures
System crashes
```

Error logs are always enabled in production.

---

## Log Level Hierarchy

```
ERROR
WARN
INFO
DEBUG
TRACE
```

Higher levels include the lower levels.

Example:

If log level is set to **INFO**, then:

```
INFO logs → visible
ERROR logs → visible
DEBUG logs → hidden
```

---

# 3️⃣ Why Logging Exception Object (ex) is Important

When logging errors, developers should always include the **exception object**.

Example:

```java
log.error("Failed to fetch problems", ex);
```

---

## What Happens If We Do NOT Log the Exception

Example:

```java
log.error("Database error occurred");
```

This only prints a message.

But we lose critical information such as:

```
Stack trace
Root cause
Class and method where error occurred
```

This makes debugging extremely difficult.

---

## What Happens When We Log the Exception Object

Example:

```java
log.error("Database error occurred", ex);
```

Now the log includes:

```
Exception message
Stack trace
Exact line where error occurred
Full execution path
```

Example log output:

```
ERROR Failed to fetch problems
java.sql.SQLException: Connection refused
    at ProblemRepository.findAll(...)
    at ProblemService.getProblems(...)
```

This information is essential for debugging.

---

## Best Practice

Always log exceptions like this:

```java
log.error("Error while creating problem", ex);
```

This ensures complete debugging information is recorded.

---

# Key Takeaways

| Concept           | Explanation                              |
| ----------------- | ---------------------------------------- |
| Logging           | Recording application events and errors  |
| log.info          | Normal system events                     |
| log.debug         | Detailed debugging information           |
| log.error         | Critical failures and exceptions         |
| Exception Logging | Helps capture stack trace and root cause |

---

# Interview Summary

**Why logging is critical in production systems?**

Logging helps monitor application behavior, debug production issues, analyze performance, and investigate system failures.

**Difference between log.info(), log.debug(), and log.error()?**

* `log.info()` is used for important application events.
* `log.debug()` is used for detailed debugging information.
* `log.error()` is used for system failures and unexpected errors.

**Why logging exception object (ex) is important?**

Logging the exception object captures the full stack trace and root cause of the error, making debugging much easier.

---
