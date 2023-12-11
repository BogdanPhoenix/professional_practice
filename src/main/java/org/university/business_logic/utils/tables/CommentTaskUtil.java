package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.tables.*;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentTaskUtil extends TableModelView<CommentTask> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName SPRINT = new AttributeNameComboBox(1, "Спринт", "sprint", Sprint.class);
    private static final AttributeName TASK = new AttributeNameComboBox(2, "Завдання", "task", Task.class);
    private static final AttributeName TASK_TESTING = new AttributeNameComboBox(3, "Завдання тестування", "taskTesting", TaskTesting.class);
    private static final AttributeName PERFORMER = new AttributeNameComboBox(4, "Власник", "performer", Employee.class);
    private static final AttributeName COMMENT_TEXT = new AttributeNameSimple(5, "Коментар", "commentText");
    private static final AttributeName DATE_CREATE = new AttributeNameSimple(6, "Дата створення", "dateTimeCreate");

    public CommentTaskUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                SPRINT.getNameForUser(),
                TASK.getNameForUser(),
                TASK_TESTING.getNameForUser(),
                PERFORMER.getNameForUser(),
                COMMENT_TEXT.getNameForUser(),
                DATE_CREATE.getNameForUser()
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
        var commentText = table.getValueAt(indexRow, COMMENT_TEXT.getId());
        return new SearchCriteria[] { new SearchCriteria(COMMENT_TEXT.getNameForSystem(), commentText, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createCoherentComboBoxPanels(PROJECT, SPRINT, TASK, TASK_TESTING));
        components.addAll(createComboBoxPanels(false, PERFORMER));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(COMMENT_TEXT);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull CommentTask entity) {
        return new SearchCriteria[] {
                new SearchCriteria(COMMENT_TEXT.getNameForSystem(), entity.getCommentText(), SearchOperation.EQUAL)
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

        panelComponent.updateComboBox(PROJECT, projectEntity);
        panelComponent.updateComboBox(SPRINT, sprintEntity);
        panelComponent.updateComboBox(TASK, taskEntity);
        panelComponent.updateComboBox(TASK_TESTING, entity.getTaskTesting());
        panelComponent.updateComboBox(PERFORMER, entity.getPerformer());
        panelComponent.updateTextField(COMMENT_TEXT, entity.getCommentText());
    }

    @Override
    public JPanel selectEntryPanel() {
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createIntervalPanels(DATE_CREATE));
        components.addAll(createComboBoxPanels(true, TASK_TESTING, PERFORMER));
        addAllComponentsToPanel(components);

        return panelBody;
    }
    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_CREATE));
        searchCriteria.addAll(criteriaFromComboBox(TASK_TESTING, PERFORMER));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, SPRINT, TASK, TASK_TESTING, PERFORMER);
            createGraphUI(variants);
        };
    }
}
