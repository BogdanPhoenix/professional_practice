package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.SearchOperation;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.Position;
import org.university.ui.realization.panel_interaction.SearchCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class PositionUtil extends TableModelView<Position> {
    private static final String NAME_POSITION = "namePosition";

    public PositionUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Посади";
    }

    @Override
    public Class<Position> resolveEntityClass() {
        return Position.class;
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var executionStatuses = selectAll();
        addRows(tableModel, executionStatuses);
    }

    @Override
    protected Object[] createAttribute(@NotNull Position value) {
        return new Object[]{ value.getNamePosition() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel("Посада", NAME_POSITION);
    }

    @Override
    public ActionListener command() {
        return e -> {
            String value = valueFromTextField(NAME_POSITION);

            var position = Position.builder()
                    .namePosition(value)
                    .currentData(true)
                    .build();

            var search = new SearchCriteria(NAME_POSITION, position.getNamePosition(), SearchOperation.EQUAL);

            if (saveToTable(position, search).isEmpty()) {
                return;
            }

            ((JTextField)components.get(NAME_POSITION)).setText("");
        };
    }
}
