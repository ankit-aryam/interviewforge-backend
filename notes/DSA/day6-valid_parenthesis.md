# DSA Practice – Day 6

## LeetCode 20 – Valid Parentheses

### Problem Summary

Given a string containing only the characters:

```
()
{}
[]
```

Determine if the input string is **valid**.

A string is valid if:

1. Every opening bracket has a corresponding closing bracket.
2. Brackets are closed in the correct order.
3. Every closing bracket matches the most recent unmatched opening bracket.

Example:

```
Input: "()"
Output: true

Input: "()[]{}"
Output: true

Input: "(]"
Output: false

Input: "([)]"
Output: false
```

---

# 1️⃣ Brute Force Idea

### Basic Thinking

One brute-force idea is to repeatedly remove valid pairs from the string.

Steps:

1. Search for valid pairs like:

   ```
   ()
   {}
   []
   ```
2. Remove them from the string.
3. Repeat until no more pairs exist.
4. If the string becomes empty → valid.
5. If characters remain → invalid.

---

### Example

Input:

```
"({[]})"
```

Step-by-step removal:

```
({[]})
→ ({})
→ ()
→ ""
```

Since the string becomes empty → **valid**.

---

### Why This Is Inefficient

Each removal requires:

```
String scanning
String reconstruction
```

Worst case:

```
O(n) operations repeated n times
```

So complexity becomes:

```
O(n²)
```

---

### Brute Force Complexity

| Metric           | Complexity |
| ---------------- | ---------- |
| Time Complexity  | O(n²)      |
| Space Complexity | O(n)       |

---

# 2️⃣ Optimal Approach – Using Stack

The optimal solution uses a **stack**.

### Key Idea

Brackets must close in **reverse order**.

Example:

```
( { [ ] } )
```

The last opening bracket must close first.

This behavior matches **LIFO (Last In First Out)**, which is exactly how a stack works.

---

### Algorithm

1. Create an empty stack.

2. Traverse the string character by character.

3. If the character is an **opening bracket**:

```
(
{
[
```

Push it into the stack.

4. If the character is a **closing bracket**:

```
)
}
]
```

Then:

* If stack is empty → invalid.
* Check if top of stack matches corresponding opening bracket.
* If yes → pop.
* If not → invalid.

5. After processing the string:

* If stack is empty → valid.
* If stack still contains elements → invalid.

---

### Example Walkthrough

Input:

```
"([{}])"
```

Steps:

```
( → push
[ → push
{ → push
} → pop
] → pop
) → pop
```

Stack becomes empty.

Result:

```
Valid parentheses
```

---

### Example Invalid Case

Input:

```
"([)]"
```

Steps:

```
( → push
[ → push
) → mismatch with '['
```

Result:

```
Invalid
```

---

# Optimal Java Logic

```java
Stack<Character> stack = new Stack<>();

for(char c : s.toCharArray()) {

    if(c == '(' || c == '{' || c == '[') {
        stack.push(c);
    }
    else {
        if(stack.isEmpty()) return false;

        char top = stack.peek();

        if((c == ')' && top == '(') ||
           (c == '}' && top == '{') ||
           (c == ']' && top == '[')) {

            stack.pop();
        } else {
            return false;
        }
    }
}

return stack.isEmpty();
```

---

# 3️⃣ Time Complexity

We traverse the string only once.

```
n = length of string
```

Each character:

```
Push → O(1)
Pop → O(1)
```

Total complexity:

```
O(n)
```

---

# 4️⃣ Space Complexity

Stack can store at most **n characters**.

Example worst case:

```
(((((((((
```

Stack size becomes `n`.

Therefore:

```
O(n)
```

---

# Complexity Summary

| Metric           | Complexity |
| ---------------- | ---------- |
| Time Complexity  | O(n)       |
| Space Complexity | O(n)       |

---

# Why Stack Is Perfect Here

The bracket matching rule requires:

```
Last opened → first closed
```

Which follows:

```
LIFO
```

This is exactly the behavior of a **stack**, making it the ideal data structure for this problem.

---

# Interview Explanation

If asked in an interview:

> The optimal solution uses a stack to track opening brackets. When an opening bracket appears, it is pushed into the stack. When a closing bracket appears, the stack is checked to see if the top element matches the corresponding opening bracket. If it matches, the element is popped; otherwise, the string is invalid. At the end, if the stack is empty, the parentheses are valid.

---
