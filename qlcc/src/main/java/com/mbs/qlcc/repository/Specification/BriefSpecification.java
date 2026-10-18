package com.mbs.qlcc.repository.Specification;

import com.mbs.qlcc.domain.Brief;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class BriefSpecification {

    public static Specification<Brief> filter(String complexId, String briefName, String type) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (complexId != null && !complexId.isBlank()) {
                predicates.add(cb.equal(root.get("complexId"), complexId.trim()));
            }

            predicates.add(cb.isFalse(root.get("isDeleted")));

            if (briefName != null && !briefName.isBlank()) {
                String pattern = "%" + briefName.trim().toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("briefName")), pattern));
            }

            if (type != null && !type.isBlank()) {
                predicates.add(cb.equal(cb.lower(root.get("type")), type.trim().toLowerCase()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private BriefSpecification() {}
}
