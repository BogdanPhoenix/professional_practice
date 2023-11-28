package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.SearchOperation;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.StateTesting;
import org.university.ui.realization.panel_interaction.SearchCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class StateTestingUtil extends TableModelView<StateTesting> {
    private static final String TASK_TESTING = "taskTesting";
    public StateTestingUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Стани тестування";
    }

    @Override
    public Class<StateTesting> resolveEntityClass() {
        return StateTesting.class;
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var stateTestings = selectAll();
        addRows(tableModel, stateTestings);
    }

    @Override
    protected Object[] createAttribute(@NotNull StateTesting value) {
        return new Object[]{ value.getTaskTestings() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel("Стан тестування", TASK_TESTING);
    }

    @Override
    public ActionListener command() {
        return e -> {
            String value = valueFromTextField(TASK_TESTING);

            var state = StateTesting.builder()
                    .nameState(value)
                    .currentData(true)
                    .build();

            var search = new SearchCriteria(TASK_TESTING, state.getNameState(), SearchOperation.EQUAL);

            if (saveToTable(state, search).isEmpty()) {
                return;
            }

            ((JTextField)components.get(TASK_TESTING)).setText("");
        };
    }
}
