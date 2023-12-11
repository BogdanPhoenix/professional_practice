package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class ExecutionStatusUtil extends ReferenceBookModelView<ExecutionStatus> {
    private static final AttributeName NAME_EXECUTION = new AttributeNameSimple(0, "Стан виконання", "nameExecution");

    public ExecutionStatusUtil(){
        titleColumns = List.of( NAME_EXECUTION.getNameForUser() );
        nameTable = "Стани виконання";
    }

    @Override
    public Class<ExecutionStatus> resolveEntityClass() {
        return ExecutionStatus.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull ExecutionStatus value) {
        return new Object[]{ value.getNameExecution() };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow){
        var value = table.getValueAt(indexRow, NAME_EXECUTION.getId());
        return new SearchCriteria[]{ new SearchCriteria(NAME_EXECUTION.getNameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = panelComponent.createTextFieldInputPanel(NAME_EXECUTION);
        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(NAME_EXECUTION);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull ExecutionStatus entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_EXECUTION.getNameForSystem(), entity.getNameExecution(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected ExecutionStatus newEntity() {
        try {
            String value = valueFromTextField(NAME_EXECUTION);

            return ExecutionStatus.builder()
                    .nameExecution(value)
                    .currentData(true)
                    .build();
        }  catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        panelComponent.updateTextField(NAME_EXECUTION, entity.getNameExecution());
    }
}
