package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.ExecutionStatusUtil;
import org.university.business_logic.utils.reference_book.PriorityTaskUtil;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.tables.*;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class CheckListUtil extends TableModelView<CheckList> {
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName SPRINT = new ObjectName("Спринт", "sprint");
    private static final ObjectName TASK = new ObjectName("Завдання", "task");
    private static final ObjectName CHECK_LIST = new ObjectName("Підзавдання", "nameTask");
    private static final ObjectName PERFORMER = new ObjectName("Відповідальний", "performer");
    private static final ObjectName EXECUTION = new ObjectName("Виконання", "executionStatus");
    private static final ObjectName PRIORITY = new ObjectName("Пріоритет", "priority");
    private static final ObjectName DATE_CREATE = new ObjectName("Дата старту виконання", "dateTimeCreate");
    private static final ObjectName PLANNED_DATE = new ObjectName("Запланована дата завершення", "plannedCompletionDate");
    private static final ObjectName DATE_END = new ObjectName("Дата завершення", "dateTimeEnd");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис", "description");

    public CheckListUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                SPRINT.nameForUser(),
                TASK.nameForUser(),
                PERFORMER.nameForUser(),
                CHECK_LIST.nameForUser(),
                EXECUTION.nameForUser(),
                PRIORITY.nameForUser(),
                DATE_CREATE.nameForUser(),
                PLANNED_DATE.nameForUser(),
                DATE_END.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Підзавдання";
    }

    @Override
    public Class<CheckList> resolveEntityClass() {
        return CheckList.class;
    }

    @Override
    @Contract("_ -> new")
    protected Object @NotNull [] createAttribute(@NotNull CheckList value){
        var task = value.getTask();
        var sprint = task.getSprint();
        var project = sprint.getProject();

        return new Object[]{
                project,
                sprint,
                task,
                value.getPerformer(),
                value.getNameTask(),
                value.getExecutionStatus(),
                value.getPriority(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        Task task = (Task) table.getValueAt(indexRow, 2);
        String checkList = (String) table.getValueAt(indexRow, 4);

        return new SearchCriteria[] {
                new SearchCriteria(TASK.nameForSystem(), task, SearchOperation.EQUAL),
                new SearchCriteria(CHECK_LIST.nameForSystem(), checkList, SearchOperation.EQUAL),
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null){
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(11, 1));

        JPanel task = windowComponent.createComboBoxPanel(TASK, new TaskUtil());
        JPanel sprint = windowComponent.createComboBoxPanel(SPRINT, new SprintUtil(), TASK);
        JPanel project = windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil(), SPRINT);

        panelBody.add(project);
        panelBody.add(sprint);
        panelBody.add(task);
        panelBody.add(windowComponent.createComboBoxPanel(PERFORMER, new EmployeeUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(CHECK_LIST));
        panelBody.add(windowComponent.createComboBoxPanel(EXECUTION, new ExecutionStatusUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(PRIORITY, new PriorityTaskUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_CREATE));
        panelBody.add(windowComponent.createTextFieldInputPanel(PLANNED_DATE));
        panelBody.add(windowComponent.createTextFieldInputPanel(DATE_END));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameTask = valueFromTextField(CHECK_LIST);
        String dateCreate = valueFromTextField(DATE_CREATE);
        String plannedDate = valueFromTextField(PLANNED_DATE);

        if(nameTask.isEmpty() || dateCreate.isEmpty() || plannedDate.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    CHECK_LIST.nameForUser(), DATE_CREATE.nameForUser(), PLANNED_DATE.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull CheckList entity) {
        return new SearchCriteria[] {
                new SearchCriteria(TASK.nameForSystem(), entity.getTask(), SearchOperation.EQUAL),
                new SearchCriteria(CHECK_LIST.nameForSystem(), entity.getNameTask(), SearchOperation.EQUAL),
        };
    }

    @Override
    protected CheckList newEntity() {
        try {
            Task task = (Task) valueFromComboBox(TASK);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            PriorityTask priority = (PriorityTask) valueFromComboBox(PRIORITY);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            String nameTask = valueFromTextField(CHECK_LIST);
            String description = valueFromTextField(DESCRIPTION);
            String dateCreate = valueFromTextField(DATE_CREATE);
            String planned = valueFromTextField(PLANNED_DATE);
            String dateEndString = valueFromTextField(DATE_END);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return CheckList.builder()
                    .task(task)
                    .executionStatus(executionStatus)
                    .priority(priority)
                    .performer(performer)
                    .nameTask(nameTask)
                    .description(description)
                    .dateTimeCreate(Timestamp.valueOf(dateCreate))
                    .plannedCompletionDate(Timestamp.valueOf(planned))
                    .dateTimeEnd(dateEnd)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        Sprint sprintEntity = entity.getTask().getSprint();
        Project projectEntity = sprintEntity.getProject();

        String dateEnd = convertFromTimestampToString(entity.getDateTimeEnd());

        windowComponent.updateComboBox(PROJECT, projectEntity);
        windowComponent.updateComboBox(SPRINT, sprintEntity);
        windowComponent.updateComboBox(TASK, entity.getTask());
        windowComponent.updateComboBox(PERFORMER, entity.getPerformer());
        windowComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        windowComponent.updateComboBox(PRIORITY, entity.getPriority());

        windowComponent.updateTextField(CHECK_LIST, entity.getNameTask());
        windowComponent.updateTextField(DATE_CREATE, entity.getDateTimeCreate().toString());
        windowComponent.updateTextField(PLANNED_DATE, entity.getPlannedCompletionDate().toString());
        windowComponent.updateTextField(DATE_END, dateEnd);
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
