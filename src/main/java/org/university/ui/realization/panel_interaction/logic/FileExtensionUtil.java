package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.enumuration.SearchOperation;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.business_logic.tables.FileExtension;
import org.university.ui.realization.panel_interaction.SearchCriteria;

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
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        super.createViewModel(tableModel);

        var fileExtensions = selectAll();
        addRows(tableModel, fileExtensions);
    }

    @Override
    protected Object[] createAttribute(@NotNull FileExtension value) {
        return new Object[]{ value.getNameExtension() };
    }

    @Override
    public JPanel panelInsertData() {
        return createTextFieldInputPanel("Розширення файлу", NAME_EXTENSION);
    }

    @Override
    public ActionListener command() {
        return e -> {
            String value = valueFromTextField(NAME_EXTENSION);

            var extension = FileExtension.builder()
                    .nameExtension(value)
                    .currentData(true)
                    .build();

            var search = new SearchCriteria(NAME_EXTENSION, extension.getNameExtension(), SearchOperation.EQUAL);

            if (saveToTable(extension, search).isEmpty()) {
                return;
            }

            ((JTextField)components.get(NAME_EXTENSION)).setText("");
        };
    }
}
