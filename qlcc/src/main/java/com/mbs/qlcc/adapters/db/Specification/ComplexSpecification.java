package com.mbs.qlcc.adapters.db.Specification;

import com.mbs.qlcc.adapters.db.Complex.ComplexDataMapper;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComplexSpecification {
    public static Specification<ComplexDataMapper> filter(
            Integer status,
            String keyword,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (keyword != null && !keyword.isBlank()) {
                String like = "%" + keyword.toLowerCase() + "%";

                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("complexName")), like),
                        cb.like(cb.lower(root.get("address")), like),
                        cb.like(cb.lower(root.get("nameContact")), like),
                        cb.like(cb.lower(root.get("phoneContact")), like)
                ));
            }

            if (startDate != null && endDate != null) {
                predicates.add(
                        cb.between(root.get("createdAt"), startDate, endDate)
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
