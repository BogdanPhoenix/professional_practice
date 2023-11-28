package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.SearchOperation;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.TypeChange;
import org.university.ui.realization.panel_interaction.SearchCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class TypeChangeUtil extends TableModelView<TypeChange> {
    private static final String NAME_TYPE_CHANGE = "nameTypeChange";

    public TypeChangeUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Типи зміни";
    }

    @Override
    public Class<TypeChange> resolveEntityClass() {
        return TypeChange.class;
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var typeChanges = selectAll();
        addRows(tableModel, typeChanges);
    }

    @Override
    protected Object[] createAttribute(@NotNull TypeChange value) {
        return new Object[]{ value.getNameTypeChange() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel("Тип зміни", NAME_TYPE_CHANGE);
    }

    @Override
    public ActionListener command() {
        return e -> {
            String value = valueFromTextField(NAME_TYPE_CHANGE);

            var change = TypeChange.builder()
                    .nameTypeChange(value)
                    .currentData(true)
                    .build();

            var search = new SearchCriteria(NAME_TYPE_CHANGE, change.getNameTypeChange(), SearchOperation.EQUAL);

            if (saveToTable(change, search).isEmpty()) {
                return;
            }

            ((JTextField)components.get(NAME_TYPE_CHANGE)).setText("");
        };
    }
}
