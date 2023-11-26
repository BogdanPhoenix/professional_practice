package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class TypeComplexityUtil extends TableModelView<TypeComplexity> {
    public TypeComplexityUtil(){
        titleColumns = List.of("Назва", "Числовий еквівалент");
        nameTable = "Типи складності";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var typeComplexities = selectAll();
        addRows(tableModel, typeComplexities);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull TypeComplexity value){
        return new Object[]{
                value.getNameComplexity(),
                value.getNumberValue()
        };
    }

    @Override
    public JPanel panelInsertData() {
        return new JPanel();
    }

    @Override
    public ActionListener command() {
        return e -> {};
    }

    @Override
    public Class<TypeComplexity> resolveEntityClass() {
        return TypeComplexity.class;
    }
}
