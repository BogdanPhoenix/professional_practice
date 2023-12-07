package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.entities.tables.*;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class CommentTaskUtil extends TableModelView<CommentTask> {
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName SPRINT = new ObjectName("Спринт", "sprint");
    private static final ObjectName TASK = new ObjectName("Завдання", "task");
    private static final ObjectName TASK_TESTING = new ObjectName("Завдання тестування", "taskTesting");
    private static final ObjectName PERFORMER = new ObjectName("Власник", "performer");
    private static final ObjectName COMMENT_TEXT = new ObjectName("Коментар", "commentText");
    private static final ObjectName DATE_CREATE = new ObjectName("Дата створення", "dateTimeCreate");

    public CommentTaskUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                SPRINT.nameForUser(),
                TASK.nameForUser(),
                TASK_TESTING.nameForUser(),
                PERFORMER.nameForUser(),
                COMMENT_TEXT.nameForUser(),
                DATE_CREATE.nameForUser()
        );
        nameTable = "Коментарі до завдань";
    }

    @Override
    public Class<CommentTask> resolveEntityClass() {
        return CommentTask.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull CommentTask value) {
        TaskTesting taskTesting = value.getTaskTesting();
        Task task = taskTesting.getTask();
        Sprint sprint = task.getSprint();
        Project project = sprint.getProject();

        return new Object[]{
                project,
                sprint,
                task,
                taskTesting,
                value.getPerformer(),
                value.getCommentText(),
                value.getDateTimeCreate()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        String commentText = (String) table.getValueAt(indexRow, 5);
        return new SearchCriteria[] { new SearchCriteria(COMMENT_TEXT.nameForSystem(), commentText, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(6, 1));

        var taskTesting = windowComponent.createComboBoxPanel(TASK_TESTING, new TaskTestingUtil());
        var task = windowComponent.createComboBoxPanel(TASK, new TaskUtil(), TASK_TESTING);
        var sprint = windowComponent.createComboBoxPanel(SPRINT, new SprintUtil(), TASK);
        var project = windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil(), SPRINT);

        panelBody.add(project);
        panelBody.add(sprint);
        panelBody.add(task);
        panelBody.add(taskTesting);
        panelBody.add(windowComponent.createComboBoxPanel(PERFORMER, new EmployeeUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(COMMENT_TEXT));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(COMMENT_TEXT);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull CommentTask entity) {
        return new SearchCriteria[] {
                new SearchCriteria(COMMENT_TEXT.nameForSystem(), entity.getCommentText(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected CommentTask newEntity() {
        try {
            TaskTesting taskTesting = (TaskTesting) valueFromComboBox(TASK_TESTING);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            String commentText = valueFromTextField(COMMENT_TEXT);
            LocalDateTime dateCreate = LocalDateTime.now();

            return CommentTask.builder()
                    .taskTesting(taskTesting)
                    .performer(performer)
                    .commentText(commentText)
                    .dateTimeCreate(Timestamp.valueOf(dateCreate))
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        var taskEntity = entity.getTaskTesting().getTask();
        var sprintEntity = taskEntity.getSprint();
        var projectEntity = sprintEntity.getProject();

        windowComponent.updateComboBox(PROJECT, projectEntity);
        windowComponent.updateComboBox(SPRINT, sprintEntity);
        windowComponent.updateComboBox(TASK, taskEntity);
        windowComponent.updateComboBox(TASK_TESTING, entity.getTaskTesting());
        windowComponent.updateComboBox(PERFORMER, entity.getPerformer());
        windowComponent.updateTextField(COMMENT_TEXT, entity.getCommentText());
    }
}
