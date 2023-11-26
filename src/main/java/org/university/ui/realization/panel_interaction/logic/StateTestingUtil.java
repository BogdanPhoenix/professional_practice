package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.StateTesting;

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
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var stateTestings = selectAll();
        addRows(tableModel, stateTestings);
    }

    @Override
    protected Object[] createAttribute(@NotNull StateTesting value) {
        return new Object[]{ value.getTaskTestings() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel(TASK_TESTING);
    }

    @Override
    public ActionListener command() {
        return e -> {};
    }
}
