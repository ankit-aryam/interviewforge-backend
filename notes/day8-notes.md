# Day 8 Notes – Rate Limiting & Redis

Rate limiting is a critical concept in backend systems used to control how many requests a client can make within a given time.

---

# 1️⃣ What is Rate Limiting and Why Needed?

### Definition

Rate limiting is a technique used to **restrict the number of API requests** a client can make in a specific time window.

Example:

```id="c1jv0m"
Max 100 requests per minute per user
```

---

## Why Rate Limiting is Needed

### 1. Prevent System Overload

If too many requests hit the server:

```id="j6o0tq"
CPU ↑
Memory ↑
DB load ↑
```

System can crash.

Rate limiting protects backend from **traffic spikes**.

---

### 2. Prevent Abuse / DDoS

Example:

```id="lqixx6"
Bot sending 10000 requests/sec
```

Rate limiting blocks such malicious behavior.

---

### 3. Fair Usage

Ensures one user does not consume all resources.

```id="a6co4h"
User A → 100 req/min
User B → 100 req/min
```

---

### 4. Protect Expensive Operations

Example:

```id="ubg6as"
AI API calls
Payment APIs
Search APIs
```

These are costly → must be limited.

---

### Example in InterviewForge

```id="brm6yr"
GET /api/problems
```

We can restrict:

```id="w3xxi6"
Max 10 requests per minute per user
```

---

# 2️⃣ What is Fixed Window vs Sliding Window?

These are **rate limiting algorithms**.

---

## Fixed Window Algorithm

### How it works

Time is divided into fixed intervals:

```id="a0xsmv"
0–60 sec → window 1
60–120 sec → window 2
```

Example:

```id="1c7oyt"
Limit = 5 requests per minute
```

User requests:

```id="y0ztp2"
59th sec → 5 requests
60th sec → 5 requests
```

Total:

```id="9a1xkq"
10 requests in 2 seconds 😱
```

---

### Problem

```id="7k1dpm"
Burst traffic allowed at boundary
```

---

## Sliding Window Algorithm

### How it works

Instead of fixed buckets, we track requests in a **moving window**.

Example:

```id="2a0j4q"
Current time → last 60 seconds
```

So requests are counted dynamically.

---

### Example

```id="bd5q4x"
Requests at t=50, 55, 58
Now time = 60
Window = last 60 sec → includes all
```

As time moves:

```id="l7k98m"
Old requests expire automatically
```

---

### Advantage

```id="1k9plv"
No burst issue
Smooth rate limiting
More accurate control
```

---

## Comparison

| Feature          | Fixed Window | Sliding Window |
| ---------------- | ------------ | -------------- |
| Accuracy         | Low          | High           |
| Burst Handling   | Poor         | Good           |
| Implementation   | Simple       | Complex        |
| Real-world usage | Rare         | Preferred      |

---

# 3️⃣ Why Redis is Used for Rate Limiting?

### Requirement of Rate Limiting System

We need:

```id="9zrl3q"
Fast read/write
Shared across servers
Atomic operations
Expiration support
```

---

## Why Redis Fits Perfectly

### 1. Extremely Fast (In-Memory)

```id="u4s6gl"
< 1 ms latency
```

Perfect for real-time rate limiting.

---

### 2. Atomic Operations

Redis supports:

```id="0u4x0v"
INCR
EXPIRE
```

These are atomic → no race conditions.

---

### 3. Built-in Expiry (TTL)

Example:

```id="x68h1x"
Key expires after 60 seconds
```

Perfect for time-based limits.

---

### 4. Distributed System Support

Multiple backend servers can share same Redis.

```id="l5xlj2"
Server A → Redis
Server B → Redis
```

So rate limiting is consistent.

---

### 5. Scalable

Redis can handle:

```id="ljq6ul"
Millions of requests
```

---

## Example Flow

User hits API:

```id="2g8a0y"
GET /api/problems
```

Redis key:

```id="p5n9c3"
rate_limit:user123
```

Flow:

```id="9t9i3b"
INCR key
↓
If count > limit → block request
↓
Else → allow request
```

---

## Example Redis Command

```id="kczp4x"
INCR rate_limit:user123
EXPIRE rate_limit:user123 60
```

---

# Key Takeaways

| Concept        | Explanation                            |
| -------------- | -------------------------------------- |
| Rate Limiting  | Controls number of API requests        |
| Fixed Window   | Simple but allows burst                |
| Sliding Window | Accurate and smooth                    |
| Redis          | Fast, atomic, distributed, TTL support |

---

# Interview Summary

### What is rate limiting and why needed?

Rate limiting restricts the number of requests a client can make within a time period to prevent system overload, abuse, and ensure fair usage.

---

### What is Fixed Window vs Sliding Window?

Fixed Window divides time into fixed intervals but allows burst traffic at boundaries, while Sliding Window tracks requests dynamically over time and provides more accurate rate limiting.

---

### Why Redis is used for rate limiting?

Redis is used because it is extremely fast, supports atomic operations, provides built-in expiration, and works well in distributed systems, making it ideal for real-time rate limiting.

---
