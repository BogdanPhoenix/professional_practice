package org.university.business_logic.tables.logic.interfaces;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public interface Condition<T> {
    Predicate buildPredicate(CriteriaBuilder criteriaBuilder, Root<T> root);
}
