package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.PriorityTaskUtil;
import org.university.business_logic.utils.reference_book.StateTestingUtil;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.reference_book.StateTesting;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.entities.tables.*;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class TaskTestingUtil extends TableModelView<TaskTesting>{
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName SPRINT = new ObjectName("Спринт", "sprint");
    private static final ObjectName TASK = new ObjectName("Завдання", "task");
    private static final ObjectName TASK_TESTING = new ObjectName("Тестове завдання", "nameTaskTesting");
    private static final ObjectName PERFORMER = new ObjectName("Відповідальний", "performer");
    private static final ObjectName STATE = new ObjectName("Виконання", "stateTesting");
    private static final ObjectName PRIORITY = new ObjectName("Пріоритет", "priority");
    private static final ObjectName DATE_START = new ObjectName("Дата старту виконання", "dateTimeCreate");
    private static final ObjectName PLANNED_COMPLETION = new ObjectName("Запланована дата завершення", "plannedCompletionDate");
    private static final ObjectName DATE_END = new ObjectName("Дата завершення", "dateTimeEnd");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис", "description");

    public TaskTestingUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                SPRINT.nameForUser(),
                TASK.nameForUser(),
                TASK_TESTING.nameForUser(),
                PERFORMER.nameForUser(),
                STATE.nameForUser(),
                PRIORITY.nameForUser(),
                DATE_START.nameForUser(),
                PLANNED_COMPLETION.nameForUser(),
                DATE_END.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Завдання на тестування";
    }

    @Override
    public Class<TaskTesting> resolveEntityClass() {
        return TaskTesting.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull TaskTesting value) {
        Task task = value.getTask();
        Sprint sprint = task.getSprint();
        Project project = sprint.getProject();

        return new Object[]{
                project,
                sprint,
                task,
                value.getNameTaskTesting(),
                value.getPerformer(),
                value.getStateTesting(),
                value.getPriority(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var task = table.getValueAt(indexRow, 2);
        var nameTaskTesting = table.getValueAt(indexRow, 3);

        return new SearchCriteria[] {
                new SearchCriteria(TASK.nameForSystem(), task, SearchOperation.EQUAL),
                new SearchCriteria(TASK_TESTING.nameForSystem(), nameTaskTesting, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(11, 1));

        var task = windowComponent.createComboBoxPanel(TASK, new TaskUtil());
        var sprint = windowComponent.createComboBoxPanel(SPRINT, new SprintUtil(), TASK);
        var project = windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil(), SPRINT);

        panelBody.add(project);
        panelBody.add(sprint);
        panelBody.add(task);
        panelBody.add(windowComponent.createTextFieldInputPanel(TASK_TESTING));
        panelBody.add(windowComponent.createComboBoxPanel(PERFORMER, new EmployeeUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(STATE, new StateTestingUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(PRIORITY, new PriorityTaskUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_START));
        panelBody.add(windowComponent.createTextFieldInputPanel(PLANNED_COMPLETION));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_END));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameTask = valueFromTextField(TASK_TESTING);
        String dateStart = valueFromTextField(DATE_START);
        String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);

        if(nameTask.isEmpty() || dateStart.isEmpty() || plannedCompletion.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    TASK_TESTING.nameForUser(), DATE_START.nameForUser(), PLANNED_COMPLETION.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull TaskTesting entity) {
        return new SearchCriteria[] {
                new SearchCriteria(TASK.nameForSystem(), entity.getTask(), SearchOperation.EQUAL),
                new SearchCriteria(TASK_TESTING.nameForSystem(), entity.getNameTaskTesting(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected TaskTesting newEntity() {
        try {
            Task task = (Task) valueFromComboBox(TASK);
            String nameTaskTesting = valueFromTextField(TASK_TESTING);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            StateTesting stateTesting = (StateTesting) valueFromComboBox(STATE);
            PriorityTask priorityTask = (PriorityTask) valueFromComboBox(PRIORITY);
            String dateStart = valueFromTextField(DATE_START);
            String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
            String dateEndString = valueFromTextField(DATE_END);
            String description = valueFromTextField(DESCRIPTION);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return TaskTesting.builder()
                    .task(task)
                    .nameTaskTesting(nameTaskTesting)
                    .performer(performer)
                    .stateTesting(stateTesting)
                    .priority(priorityTask)
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
        var sprintEntity = entity.getTask().getSprint();
        var projectEntity = sprintEntity.getProject();
        String dateEnd = convertFromTimestampToString(entity.getDateTimeEnd());

        windowComponent.updateComboBox(PROJECT, projectEntity);
        windowComponent.updateComboBox(SPRINT, sprintEntity);
        windowComponent.updateComboBox(TASK, entity.getTask());
        windowComponent.updateComboBox(PERFORMER, entity.getPerformer());
        windowComponent.updateComboBox(STATE, entity.getStateTesting());
        windowComponent.updateComboBox(PRIORITY, entity.getPriority());

        windowComponent.updateTextField(TASK_TESTING, entity.getNameTaskTesting());
        windowComponent.updateTextField(DATE_START, entity.getDateTimeCreate().toString());
        windowComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        windowComponent.updateTextField(DATE_END, dateEnd);
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
