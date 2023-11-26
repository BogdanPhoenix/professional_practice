package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.FileExtension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class FileExtensionUtil extends TableModelView<FileExtension> {
    private static final String NAME_EXTENSION = "nameExtension";

    public FileExtensionUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Розширення файлів";
    }

    @Override
    public Class<FileExtension> resolveEntityClass() {
        return FileExtension.class;
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var fileExtensions = selectAll();
        addRows(tableModel, fileExtensions);
    }

    @Override
    protected Object[] createAttribute(@NotNull FileExtension value) {
        return new Object[]{ value.getNameExtension() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel(NAME_EXTENSION);
    }

    @Override
    public ActionListener command() {
        return e -> {};
    }
}
