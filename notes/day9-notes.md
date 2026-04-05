# Day 9 Notes – Event-Driven Architecture & Kafka Basics

Modern backend systems often use **event-driven architecture** to build scalable, loosely coupled, and resilient applications.

---

# 1️⃣ What is Event-Driven Architecture?

### Definition

Event-driven architecture (EDA) is a design pattern where components communicate by producing and consuming **events** instead of making direct synchronous calls.

An **event** represents something that happened in the system.

Example:

```id="e1q2a3"
UserRegistered
OrderCreated
PaymentCompleted
ProblemCreated
```

---

## Traditional Approach (Tightly Coupled)

```id="t1w2y3"
Service A → calls → Service B → calls → Service C
```

Problems:

```id="p1q2r3"
Tight coupling
Hard to scale
Failures propagate
High latency
```

---

## Event-Driven Approach

```id="e4r5t6"
Service A → publishes event → Kafka → Service B consumes
                                   → Service C consumes
```

Flow:

```id="f7g8h9"
Event produced → Event broker → Multiple consumers react
```

---

## Example in InterviewForge

When a problem is created:

```id="p0o9i8"
ProblemCreated event
```

Consumers can:

```id="c1v2b3"
Send notification
Update analytics
Trigger AI evaluation
```

All without directly calling each service.

---

## Benefits

| Benefit                 | Explanation                                  |
| ----------------------- | -------------------------------------------- |
| Loose Coupling          | Services don’t depend directly on each other |
| Scalability             | Consumers can scale independently            |
| Fault Isolation         | One service failure doesn’t break others     |
| Asynchronous Processing | Faster response to users                     |

---

# 2️⃣ Why Kafka is Used Instead of Direct API Calls?

### Problem with Direct API Calls

```id="d1f2g3"
Service A → REST call → Service B
```

Issues:

---

### 1. Tight Coupling

If Service B is down:

```id="x1y2z3"
Service A also fails
```

---

### 2. Latency

```id="l1m2n3"
A waits for B → slow response
```

---

### 3. Scaling Issues

More services = more API calls = complexity ↑

---

## Kafka Solution

Apache Kafka is a **distributed event streaming platform** used to decouple services.

---

## How Kafka Works

```id="k1a2f3"
Producer → Kafka Topic → Consumer
```

Example:

```id="k4s5d6"
ProblemService → Kafka → NotificationService
                              → AnalyticsService
```

---

## Advantages of Kafka

### 1. Decoupling

```id="d7f8g9"
Producer doesn't know consumers
```

---

### 2. High Throughput

Kafka handles:

```id="h1j2k3"
Millions of events per second
```

---

### 3. Fault Tolerance

Kafka stores events:

```id="z4x5c6"
If consumer fails → can retry later
```

---

### 4. Asynchronous Processing

```id="a7s8d9"
User request returns fast
Background processing continues
```

---

## Real Example

Instead of:

```id="r1t2y3"
POST /create-problem → call notification service
```

We do:

```id="r4t5y6"
POST /create-problem → publish event
```

Kafka handles the rest.

---

# 3️⃣ What is Producer vs Consumer?

### Producer

A **producer** is a service that sends events/messages to Kafka.

Example:

```id="p9o8i7"
ProblemService → produces "ProblemCreated" event
```

---

### Consumer

A **consumer** is a service that listens to events and processes them.

Example:

```id="c9v8b7"
NotificationService → consumes event → sends email
AnalyticsService → consumes event → updates stats
```

---

## Flow Diagram

```id="flow123"
Producer → Kafka Topic → Consumer
```

---

## Example

### Producer code (conceptual)

```id="prod123"
kafkaTemplate.send("problem-topic", event);
```

---

### Consumer code (conceptual)

```id="cons123"
@KafkaListener(topics = "problem-topic")
public void handleEvent(Event event) {
    // process event
}
```

---

## Key Difference

| Role     | Description                  |
| -------- | ---------------------------- |
| Producer | Sends messages to Kafka      |
| Consumer | Reads and processes messages |

---

# Key Takeaways

| Concept                   | Explanation                          |
| ------------------------- | ------------------------------------ |
| Event-Driven Architecture | Communication using events           |
| Kafka                     | Distributed event streaming platform |
| Producer                  | Sends events                         |
| Consumer                  | Processes events                     |

---

# Interview Summary

### What is event-driven architecture?

Event-driven architecture is a design pattern where services communicate through events instead of direct API calls, enabling loose coupling and scalability.

---

### Why Kafka is used instead of direct API calls?

Kafka decouples services, improves scalability, supports asynchronous processing, and provides fault tolerance compared to direct API calls.

---

### What is producer vs consumer?

A producer sends events to Kafka, while a consumer listens to those events and processes them.

---
