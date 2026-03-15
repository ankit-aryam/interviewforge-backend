# DSA Practice – Day 5

## LeetCode 155 – Min Stack (Logic Explanation)

### Problem Summary

Design a stack that supports the following operations in **constant time O(1)**:

```
push(x)
pop()
top()
getMin()
```

The `getMin()` operation should return the **minimum element in the stack at any time**.

---

# Key Idea

A normal stack allows:

```
push
pop
top
```

But to find the **minimum element**, we would normally need to scan all elements:

```
O(n)
```

However, the problem requires:

```
getMin() → O(1)
```

To achieve this, we use **two stacks**.

---

# Why Two Stacks Are Used

We maintain:

```
1️⃣ mainStack → stores all values
2️⃣ minStack  → stores minimum values
```

### Purpose of the second stack

The `minStack` keeps track of the **current minimum element** at every stage.

Whenever we push a value that is **smaller than or equal to the current minimum**, we also push it into `minStack`.

This ensures that the top of `minStack` always holds the **current minimum value**.

---

# Step-by-Step Example

### Operations

```
push(5)
push(3)
push(7)
push(1)
```

---

### Step 1

```
push(5)
```

```
mainStack: [5]
minStack : [5]
```

Minimum = 5

---

### Step 2

```
push(3)
```

3 < 5 → new minimum

```
mainStack: [5,3]
minStack : [5,3]
```

Minimum = 3

---

### Step 3

```
push(7)
```

7 > 3 → minimum unchanged

```
mainStack: [5,3,7]
minStack : [5,3]
```

Minimum = 3

---

### Step 4

```
push(1)
```

1 < 3 → new minimum

```
mainStack: [5,3,7,1]
minStack : [5,3,1]
```

Minimum = 1

---

# Pop Operation Logic

When removing elements:

```
pop()
```

If the element removed from `mainStack` is equal to `minStack.peek()`, we also pop from `minStack`.

Example:

```
pop() → remove 1
```

```
mainStack: [5,3,7]
minStack : [5,3]
```

Now minimum becomes:

```
3
```

---

# getMin() Operation

Since `minStack` always keeps the current minimum on top:

```
getMin() → minStack.peek()
```

This makes the operation:

```
O(1)
```

---

# Algorithm Logic

### push(x)

```
push x to mainStack

if minStack empty OR x <= minStack.peek()
    push x to minStack
```

---

### pop()

```
removed = mainStack.pop()

if removed == minStack.peek()
    minStack.pop()
```

---

### top()

```
return mainStack.peek()
```

---

### getMin()

```
return minStack.peek()
```

---

# Time Complexity

| Operation | Time |
| --------- | ---- |
| push      | O(1) |
| pop       | O(1) |
| top       | O(1) |
| getMin    | O(1) |

All operations are constant time.

---

# Space Complexity

```
O(n)
```

Because we maintain two stacks:

```
mainStack → n elements
minStack  → up to n elements
```

Worst case:

```
push(5)
push(4)
push(3)
push(2)
push(1)
```

Every push creates a new minimum, so both stacks grow.

---

# Why This Approach Works

Stack follows:

```
LIFO (Last In First Out)
```

When the minimum element leaves the stack, we remove it from `minStack`, revealing the **previous minimum**.

This guarantees constant-time minimum lookup.

---

# Interview Explanation (Important)

If asked in an interview:

> I use two stacks: one for storing all values and another to track the minimum values. Whenever a new value is pushed that is smaller than or equal to the current minimum, it is also pushed into the min stack. During pop, if the removed value equals the minimum, it is popped from the min stack as well. This ensures getMin() can return the minimum element in constant time.

---

# Key Takeaways

* Two stacks are used to track minimum values efficiently.
* Minimum value is always available at the top of `minStack`.
* All operations run in **O(1)** time.
* Space complexity is **O(n)** due to maintaining two stacks.

---
