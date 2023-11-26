package org.university.ui.realization.panel_interaction.logic;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.tables.*;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.List;

public class CommentTaskUtil extends TableModelView<CommentTask> {
    public CommentTaskUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Тестове завдання", "Автор", "Текст коментаря", "Дата створення");
        nameTable = "Коментарі до завдань з тестування";
    }

    @Override
    public void createModel(DefaultTableModel tableModel) {
        super.createModel(tableModel);

        var commentTasks = selectAll();
        addRows(tableModel, commentTasks);
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull CommentTask value){
        TaskTesting taskTesting = value.getTaskTesting();
        Task task = taskTesting.getTask();
        Sprint sprint = task.getSprint();
        Project project = sprint.getProject();
        Employee performer = value.getPerformer();

        return new Object[]{
                project.getNameProject(),
                sprint.getNameSprint(),
                task.getNameTask(),
                taskTesting.getNameTaskTesting(),
                performer.getFirstName() + " " + performer.getNameUser(),
                value.getCommentText(),
                value.getDateTimeCreate()
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
    public Class<CommentTask> resolveEntityClass() {
        return CommentTask.class;
    }
}
