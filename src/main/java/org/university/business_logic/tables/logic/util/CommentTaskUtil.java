package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.*;

import java.util.List;

public class CommentTaskUtil extends TableModelView implements Select<CommentTask> {
    public CommentTaskUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Тестове завдання", "Автор", "Текст коментаря", "Дата створення");
        nameTable = "Коментарі до завдань з тестування";
    }

    @Override
    protected void addRows() {
        List<CommentTask> commentTasks = selectAll();
        for (CommentTask commentTask : commentTasks) {
            TaskTesting taskTesting = commentTask.getTaskTesting();
            Task task = taskTesting.getTask();
            Sprint sprint = task.getSprint();
            Project project = sprint.getProject();
            Employee performer = commentTask.getPerformer();

            model.addRow(new Object[]{
                    project.getNameProject(),
                    sprint.getNameSprint(),
                    task.getNameTask(),
                    taskTesting.getNameTaskTesting(),
                    performer.getFirstName() + " " + performer.getNameUser(),
                    commentTask.getCommentText(),
                    commentTask.getDateTimeCreate()
            });
        }
    }

    @Override
    public Class<CommentTask> resolveEntityClass() {
        return CommentTask.class;
    }
}
