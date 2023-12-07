package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.abstracts.TableModelView;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class ExecutionStatusUtil extends TableModelView<ExecutionStatus> {
    private static final ObjectName NAME_EXECUTION = new ObjectName("Стан виконання", "nameExecution");

    public ExecutionStatusUtil(){
        titleColumns = List.of( NAME_EXECUTION.nameForUser() );
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
        String value = (String)table.getValueAt(indexRow, 0);
        return new SearchCriteria[]{ new SearchCriteria(NAME_EXECUTION.nameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = windowComponent.createTextFieldInputPanel(NAME_EXECUTION);
        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(NAME_EXECUTION);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull ExecutionStatus entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_EXECUTION.nameForSystem(), entity.getNameExecution(), SearchOperation.EQUAL)
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
        windowComponent.updateTextField(NAME_EXECUTION, entity.getNameExecution());
    }
}
