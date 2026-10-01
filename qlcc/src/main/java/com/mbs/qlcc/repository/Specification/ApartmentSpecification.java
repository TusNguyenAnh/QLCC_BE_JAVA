package com.mbs.qlcc.repository.Specification;

import com.mbs.qlcc.domain.Apartment;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ApartmentSpecification - Specification Pattern
 * Tạo dynamic WHERE clauses cho Apartment queries
 */
public class ApartmentSpecification {

    public static Specification<Apartment> filterApartment(Integer status, String keyword,
                                                           LocalDateTime dateStart,
                                                           LocalDateTime dateEnd) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.isNull(root.get("deletedAt")));

            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }

            if (keyword != null && !keyword.isBlank()) {
                String pattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("aptNumber")), pattern),
                        cb.like(cb.lower(root.get("aptType")), pattern)
                ));
            }

            if (dateStart != null && dateEnd != null) {
                predicates.add(cb.between(root.get("createdAt"), dateStart, dateEnd));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
