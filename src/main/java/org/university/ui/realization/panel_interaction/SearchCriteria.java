package org.university.ui.realization.panel_interaction;

import org.university.business_logic.enumuration.SearchOperation;

public record SearchCriteria(String key, Object value, SearchOperation operation) {
}
