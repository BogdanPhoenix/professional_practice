package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.ui.interfaces.panel_interaction.logic.action_with_database.Insert;
import org.university.business_logic.tables.ExecutionStatus;
import org.university.ui.realization.panel_interaction.SearchCriteria;
import org.university.ui.realization.panel_interaction.SearchOperation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class ExecutionStatusUtil extends TableModelView<ExecutionStatus> implements Insert<ExecutionStatus> {
    private static final String NAME_EXECUTION = "nameExecution";

    public ExecutionStatusUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Стани виконання";
    }

    @Override
    public Class<ExecutionStatus> resolveEntityClass() {
        return ExecutionStatus.class;
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var executionStatuses = selectAll();
        addRows(tableModel, executionStatuses);
    }

    @Override
    protected Object[] createAttribute(@NotNull ExecutionStatus value) {
        return new Object[]{ value.getNameExecution() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel(NAME_EXECUTION);
    }

    @Override
    public ActionListener command() {
        return e -> {
            String value = valueFromTextField(NAME_EXECUTION);

            var execution = ExecutionStatus.builder()
                    .nameExecution(value)
                    .currentData(true)
                    .build();

            var search = new SearchCriteria(NAME_EXECUTION, execution.getNameExecution(), SearchOperation.EQUAL);

            if(isDuplicate(this, search)){
                System.out.println("Duplicate execution");
                return;
            }

            save(execution);
        };
    }
}
