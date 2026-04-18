package com.mbs.qlcc.adapters.db.Specification;

import com.mbs.qlcc.adapters.db.Apartment.ApartmentDataMapper;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

/**
 * ApartmentSpecification - Specification Pattern
 * Tạo dynamic WHERE clauses cho Apartment queries
 */
public class ApartmentSpecification {

    public static Specification<ApartmentDataMapper> filter(int status, String keyword,
                                                            LocalDateTime dateStart,
                                                            LocalDateTime dateEnd) {
        return Specification.where(withStatus(status))
                .and(withDeletedAtNull())
                .and(withKeyword(keyword))
                .and(withDateRange(dateStart, dateEnd));
    }

    private static Specification<ApartmentDataMapper> withStatus(int status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    private static Specification<ApartmentDataMapper> withDeletedAtNull() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    private static Specification<ApartmentDataMapper> withKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.isEmpty()) {
                return criteriaBuilder.conjunction(); // No filter
            }

            return criteriaBuilder.or(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("aptNumber")),
                            "%" + keyword.toLowerCase() + "%"
                    ),
                    criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("aptType")),
                            "%" + keyword.toLowerCase() + "%"
                    )
            );
        };
    }

    private static Specification<ApartmentDataMapper> withDateRange(LocalDateTime dateStart,
                                                                    LocalDateTime dateEnd) {
        return (root, query, criteriaBuilder) -> {
            if (dateStart == null || dateEnd == null) {
                return criteriaBuilder.conjunction(); // No filter
            }

            return criteriaBuilder.between(root.get("createdAt"), dateStart, dateEnd);
        };
    }
}
