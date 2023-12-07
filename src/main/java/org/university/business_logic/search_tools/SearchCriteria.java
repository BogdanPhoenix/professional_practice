package org.university.business_logic.search_tools;

public record SearchCriteria(String key, Object value, SearchOperation operation) {
}
