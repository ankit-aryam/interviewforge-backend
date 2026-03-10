1. Why we used JpaSpecificationExecutor instead of writing custom queries? <br>
Answer: We used JpaSpecificationExecutor because our problem search API supports dynamic filters like difficulty and tag. Specification allows building queries dynamically at runtime, avoiding multiple static query methods and making the system more scalable and maintainable.
<br>
2. What happens if both filters are null? <br>
Answer: If both filters are null, Specification returns null predicates, and Spring Data ignores them, resulting in no WHERE clause. This means all records are returned.
3. Why index on difficulty and created_at? <br>
Answer: Indexes on difficulty and created_at improve query performance. Difficulty index speeds up filtering, and created_at index improves sorting performance, reducing database scan time significantly.
4. Why returning Page<Problem> is better than List<Problem>? <br>
Answer: Page is better than List because it supports pagination and provides metadata like total elements and total pages. It improves performance by loading only required records instead of the entire dataset.

