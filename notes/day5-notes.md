# Day 5 Notes – InterviewForge Backend (Redis Caching Concepts)

## 1️⃣ What is Cache Aside Pattern?

### Definition

Cache Aside Pattern (also called **Lazy Loading Cache**) is a caching strategy where the application is responsible for loading data into the cache only when needed.

Instead of automatically syncing the cache with the database, the application follows this flow:

```
Request → Check Cache → If Miss → Fetch from DB → Store in Cache → Return Data
```

### Flow Diagram

```
Client Request
      ↓
Check Redis Cache
      ↓
Cache Hit? ─── Yes → Return Cached Data
      │
      No
      ↓
Fetch Data from Database
      ↓
Store Result in Cache
      ↓
Return Response
```

### Example in InterviewForge

User searches problems:

```
GET /api/problems?tag=array
```

Application flow:

1. Check Redis for key

   ```
   problems::null-array-0-10
   ```

2. If key exists → return cached data.

3. If key does not exist → query PostgreSQL.

4. Store result in Redis.

5. Return response to client.

### Advantages

* Reduces database load
* Improves response time
* Scales easily under high traffic

### Example Implementation

```java
@Cacheable(value = "problems",
           key = "#difficulty + '-' + #tag + '-' + #pageable.pageNumber")
public List<ProblemResponse> getProblems(...) { ... }
```

---

## 2️⃣ Why Cache Invalidation is Important?

### Problem

Cache stores **temporary copies of data**.

If database data changes but cache is not updated, users will receive **stale data**.

Example:

```
User requests problem list
↓
Cached result returned
↓
Admin adds new problem
↓
Cache still returns old list
```

This leads to inconsistent data.

### Solution

Whenever data changes, we must remove or update the cached entries.

This process is called **Cache Invalidation**.

### Example in InterviewForge

When creating a new problem:

```java
@CacheEvict(value = "problems", allEntries = true)
public ProblemResponse createProblem(...) { ... }
```

Meaning:

```
Clear all cached problem search results
```

Next request will fetch fresh data from the database.

### Why this is necessary

Without cache invalidation:

* Users see outdated data
* System behavior becomes inconsistent
* Debugging becomes difficult

### Key Concept

```
Cache = Performance optimization
Database = Source of truth
```

---

## 3️⃣ When Should We NOT Cache Data?

Caching is powerful but **not always appropriate**.

There are situations where caching can cause more problems than benefits.

### 1. Frequently changing data

Example:

```
Stock prices
Live analytics
Real-time trading systems
```

Cache would become outdated very quickly.

---

### 2. Highly personalized data

Example:

```
User bank balance
User notifications
Private messages
```

Caching could lead to incorrect data being served to the wrong user.

---

### 3. Sensitive or secure data

Example:

```
Passwords
Authentication tokens
Payment information
```

Storing such data in cache may introduce security risks.

---

### 4. Very small or cheap queries

If database query cost is already minimal, caching adds unnecessary complexity.

Example:

```
SELECT id FROM small_table WHERE id = 1
```

---

### 5. Extremely large datasets

Caching huge datasets can exhaust Redis memory.

Example:

```
Full analytics reports
Massive exports
```

---

## Key Takeaways

| Concept             | Explanation                                        |
| ------------------- | -------------------------------------------------- |
| Cache Aside Pattern | Application loads cache only when needed           |
| Cache Invalidation  | Ensures cache does not serve stale data            |
| When NOT to Cache   | Frequently changing, sensitive, or very large data |

---

## Interview Summary

**What is Cache Aside Pattern?**

> Cache Aside is a lazy loading caching strategy where the application first checks the cache, and if the data is not present, it fetches it from the database and stores it in the cache.

**Why cache invalidation is important?**

> Cache invalidation ensures that stale or outdated data is removed from the cache when the underlying database data changes.

**When should we NOT cache data?**

> We should avoid caching frequently changing data, sensitive information, highly personalized data, or queries that are already very inexpensive.

---

These caching principles are widely used in production backend systems to improve scalability and performance.
