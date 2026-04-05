# Day 4 Notes – InterviewForge Backend

## 1. API Response Wrapper

### What is ApiResponse?

ApiResponse is a generic wrapper class used to standardize API responses.

Example:

```json
{
  "success": true,
  "data": {...},
  "error": null,
  "timestamp": "2026-02-26T18:30:00"
}
```

### Why it is important

* Provides consistent response structure
* Simplifies frontend integration
* Improves error handling
* Makes API scalable and maintainable

### Benefits

* Uniform success and error responses
* Easy debugging
* Professional API design

---

## 2. Global Exception Handler

### What is GlobalExceptionHandler?

A centralized class that handles exceptions across all controllers.

Annotation used:

```java
@RestControllerAdvice
```

Exception handler method:

```java
@ExceptionHandler(ResourceNotFoundException.class)
```

### Why it is needed

* Avoids try-catch in every controller
* Provides consistent error response
* Improves maintainability
* Centralizes exception handling logic

---

## 3. Custom Exceptions and RuntimeException

Example:

```java
public class ResourceNotFoundException extends RuntimeException
```

### Why extend RuntimeException?

Because RuntimeException is unchecked exception.

Benefits:

* No need for mandatory try-catch
* Cleaner business logic
* Automatically handled by Spring
* Works perfectly with GlobalExceptionHandler

---

## 4. Request-Response Flow in InterviewForge

Client Request
↓
Controller
↓
Service
↓
Exception thrown (if any)
↓
GlobalExceptionHandler handles exception
↓
ApiResponse returned to client

---

## 5. Key Interview Points

Why ApiResponse wrapper is used?

* Consistent API structure
* Better frontend integration

Why Global Exception Handler is used?

* Centralized error handling
* Cleaner code

Why RuntimeException is extended?

* Unchecked exception
* Cleaner architecture
* Works with global handler

---

## 6. Professional Backend Architecture Pattern

Controller
↓
DTO
↓
Service
↓
Repository
↓
Entity
↓
Mapper
↓
DTO Response
↓
ApiResponse Wrapper

---

This architecture is used in production-grade backend systems.

Leetcode: 155 Minstack --> https://leetcode.com/problems/min-stack/submissions/1938945649/

I use two stacks. The main stack stores all elements, while the second stack keeps track of the minimum values. Whenever we push a value that is smaller than or equal to the current minimum, we also push it into the min stack. During pop, if the removed element equals the minimum, we pop from the min stack as well. This ensures getMin() runs in O(1) time.
