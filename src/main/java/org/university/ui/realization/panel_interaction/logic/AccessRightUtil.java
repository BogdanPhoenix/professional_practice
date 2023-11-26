package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.AccessRight;

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
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var accessRights = selectAll();
        addRows(tableModel, accessRights);
    }

    @Override
    protected Object[] createAttribute(@NotNull AccessRight value) {
        return new Object[]{ value.getNameRight() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel(NAME_RIGHT);
    }

    @Override
    public ActionListener command() {
        return e -> {};
    }
}
