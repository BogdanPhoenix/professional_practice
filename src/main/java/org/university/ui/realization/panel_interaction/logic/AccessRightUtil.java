package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.SearchOperation;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.AccessRight;
import org.university.ui.realization.panel_interaction.SearchCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class AccessRightUtil extends TableModelView<AccessRight> {
    private static final String NAME_RIGHT = "nameRight";
    public AccessRightUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Права доступу";
    }

    @Override
    public Class<AccessRight> resolveEntityClass() {
        return AccessRight.class;
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var accessRights = selectAll();
        addRows(tableModel, accessRights);
    }

    @Override
    protected Object[] createAttribute(@NotNull AccessRight value) {
        return new Object[]{ value.getNameRight() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel("Право доступу", NAME_RIGHT);
    }

    @Override
    public ActionListener command() {
        return e -> {
            String value = valueFromTextField(NAME_RIGHT);

            var right = AccessRight.builder()
                    .nameRight(value)
                    .currentData(true)
                    .build();

            var search = new SearchCriteria(NAME_RIGHT, right.getNameRight(), SearchOperation.EQUAL);

            if(saveToTable(right, search).isEmpty()){
                return;
            }

            ((JTextField)components.get(NAME_RIGHT)).setText("");
        };
    }
}
