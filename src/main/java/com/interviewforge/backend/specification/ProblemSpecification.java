package com.interviewforge.backend.specification;

import com.interviewforge.backend.entity.Problem;
import org.springframework.data.jpa.domain.Specification;

public class ProblemSpecification {

    public static Specification<Problem> hasDifficulty(String difficulty) {
        return (root, query, cb) ->
                difficulty == null ? null :
                        cb.equal(root.get("difficulty"), difficulty);
    }

    public static Specification<Problem> hasTag(String tag) {
        return (root, query, cb) ->
                tag == null ? null :
                        cb.like(
                                cb.lower(root.get("tags")),
                                "%" + tag.toLowerCase() + "%"
                        );
    }
}
