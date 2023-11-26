package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class DocumentUtil extends TableModelView<Document> {
    public DocumentUtil(){
        titleColumns = List.of("Проект", "Виконавець", "Розширення файлу", "Назва файлу", "Файл", "Дата завантаження", "Опис");
        nameTable = "Документація";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var documents = selectAll();
        addRows(tableModel, documents);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull Document value){
        Employee performer = value.getPerformer();

        return new Object[]{
                value.getProject().getNameProject(),
                performer.getFirstName() + " " + performer.getNameUser(),
                value.getFileExtension().getNameExtension(),
                value.getNameFile(),
                value.getDateTimeDown(),
                value.getDescription()
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
    public Class<Document> resolveEntityClass() {
        return Document.class;
    }
}
