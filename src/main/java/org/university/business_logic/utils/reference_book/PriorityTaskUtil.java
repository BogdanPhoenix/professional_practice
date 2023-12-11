package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.PriorityTask;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class PriorityTaskUtil extends ReferenceBookModelView<PriorityTask> {
    private static final AttributeName NAME_PRIORITY = new AttributeNameSimple(0, "Пріоритет завдання", "namePriority");

    public PriorityTaskUtil(){
        titleColumns = List.of(NAME_PRIORITY.getNameForUser());
        nameTable = "Пріоритети завдання";
    }

    @Override
    public Class<PriorityTask> resolveEntityClass() {
        return PriorityTask.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull PriorityTask value) {
        return new Object[]{ value.getNamePriority() };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow){
        var value = table.getValueAt(indexRow, NAME_PRIORITY.getId());
        return new SearchCriteria[]{ new SearchCriteria(NAME_PRIORITY.getNameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = panelComponent.createTextFieldInputPanel(NAME_PRIORITY);
        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(NAME_PRIORITY);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull PriorityTask entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_PRIORITY.getNameForSystem(), entity.getNamePriority(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected PriorityTask newEntity() {
        try {
            String value = valueFromTextField(NAME_PRIORITY);

            return PriorityTask.builder()
                    .namePriority(value)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        panelComponent.updateTextField(NAME_PRIORITY, entity.getNamePriority());
    }
}
