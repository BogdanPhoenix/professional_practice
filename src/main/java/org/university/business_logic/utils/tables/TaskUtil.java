package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.ExecutionStatusUtil;
import org.university.business_logic.utils.reference_book.PriorityTaskUtil;
import org.university.business_logic.utils.reference_book.TypeComplexityUtil;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.reference_book.TypeComplexity;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.entities.tables.Sprint;
import org.university.entities.tables.Task;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class TaskUtil extends TableModelView<Task> {
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName SPRINT = new ObjectName("Спринт", "sprint");
    private static final ObjectName TASK = new ObjectName("Завдання", "nameTask");
    private static final ObjectName PERFORMER = new ObjectName("Відповідальний", "performer");
    private static final ObjectName EXECUTION = new ObjectName("Виконання", "executionStatus");
    private static final ObjectName PRIORITY = new ObjectName("Пріоритет", "priority");
    private static final ObjectName COMPLEXITY = new ObjectName("Складність", "complexity");
    private static final ObjectName DATE_START = new ObjectName("Дата старту виконання", "dateTimeCreate");
    private static final ObjectName PLANNED_COMPLETION = new ObjectName("Запланована дата завершення", "planned_completion_date");
    private static final ObjectName DATE_END = new ObjectName("Дата завершення", "dateTimeEnd");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис", "description");

    public TaskUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                SPRINT.nameForUser(),
                TASK.nameForUser(),
                PERFORMER.nameForUser(),
                EXECUTION.nameForUser(),
                PRIORITY.nameForUser(),
                COMPLEXITY.nameForUser(),
                DATE_START.nameForUser(),
                PLANNED_COMPLETION.nameForUser(),
                DATE_END.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Завдання";
    }

    @Override
    public Class<Task> resolveEntityClass() {
        return Task.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull Task value) {
        Sprint sprint = value.getSprint();
        Project project = sprint.getProject();

        return new Object[]{
                project,
                sprint,
                value.getNameTask(),
                value.getPerformer(),
                value.getExecutionStatus(),
                value.getPriority(),
                value.getComplexity(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var sprint = table.getValueAt(indexRow, 1);
        var nameTask = table.getValueAt(indexRow, 2);

        return new SearchCriteria[] {
                new SearchCriteria(SPRINT.nameForSystem(), sprint, SearchOperation.EQUAL),
                new SearchCriteria(TASK.nameForSystem(), nameTask, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(11, 1));

        var sprint = windowComponent.createComboBoxPanel(SPRINT, new SprintUtil());
        var project = windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil(), SPRINT);

        panelBody.add(project);
        panelBody.add(sprint);
        panelBody.add(windowComponent.createTextFieldInputPanel(TASK));
        panelBody.add(windowComponent.createComboBoxPanel(PERFORMER, new EmployeeUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(EXECUTION, new ExecutionStatusUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(PRIORITY, new PriorityTaskUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(COMPLEXITY, new TypeComplexityUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_START));
        panelBody.add(windowComponent.createTextFieldInputPanel(PLANNED_COMPLETION));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_END));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameTask = valueFromTextField(TASK);
        String dateStart = valueFromTextField(DATE_START);
        String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);

        if(nameTask.isEmpty() || dateStart.isEmpty() || plannedCompletion.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    TASK.nameForUser(), DATE_START.nameForUser(), PLANNED_COMPLETION.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Task entity) {
        return new SearchCriteria[] {
                new SearchCriteria(SPRINT.nameForSystem(), entity.getSprint(), SearchOperation.EQUAL),
                new SearchCriteria(TASK.nameForSystem(), entity.getNameTask(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Task newEntity() {
        try{
            Sprint sprint = (Sprint) valueFromComboBox(SPRINT);
            String nameTask = valueFromTextField(TASK);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            PriorityTask priorityTask = (PriorityTask) valueFromComboBox(PRIORITY);
            TypeComplexity complexity = (TypeComplexity) valueFromComboBox(COMPLEXITY);
            String dateStart = valueFromTextField(DATE_START);
            String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
            String dateEndString = valueFromTextField(DATE_END);
            String description = valueFromTextField(DESCRIPTION);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return Task.builder()
                    .sprint(sprint)
                    .nameTask(nameTask)
                    .performer(performer)
                    .executionStatus(executionStatus)
                    .priority(priorityTask)
                    .complexity(complexity)
                    .dateTimeCreate(Timestamp.valueOf(dateStart))
                    .plannedCompletionDate(Timestamp.valueOf(plannedCompletion))
                    .dateTimeEnd(dateEnd)
                    .description(description)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        String dateEnd = convertFromTimestampToString(entity.getDateTimeEnd());

        windowComponent.updateComboBox(PROJECT, entity.getSprint().getProject());
        windowComponent.updateComboBox(SPRINT, entity.getSprint());
        windowComponent.updateComboBox(PERFORMER, entity.getPerformer());
        windowComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        windowComponent.updateComboBox(PRIORITY, entity.getPriority());
        windowComponent.updateComboBox(COMPLEXITY, entity.getComplexity());

        windowComponent.updateTextField(TASK, entity.getNameTask());
        windowComponent.updateTextField(DATE_START, entity.getDateTimeCreate().toString());
        windowComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        windowComponent.updateTextField(DATE_END, dateEnd);
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
