package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.reference_book.StateTesting;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.entities.tables.*;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskTestingUtil extends TableModelView<TaskTesting> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName SPRINT = new AttributeNameComboBox(1, "Спринт", "sprint", Sprint.class);
    private static final AttributeName TASK = new AttributeNameComboBox(2, "Завдання", "task", Task.class);
    private static final AttributeName PERFORMER = new AttributeNameComboBox(3, "Відповідальний", "performer", Employee.class);
    private static final AttributeName STATE = new AttributeNameComboBox(4, "Виконання", "stateTesting", StateTesting.class);
    private static final AttributeName PRIORITY = new AttributeNameComboBox(5, "Пріоритет", "priority", PriorityTask.class);
    private static final AttributeName TASK_TESTING = new AttributeNameSimple(6, "Тестове завдання", "nameTaskTesting");
    private static final AttributeName DATE_START = new AttributeNameSimple(7, "Дата старту виконання", "dateTimeCreate");
    private static final AttributeName PLANNED_COMPLETION = new AttributeNameSimple(8, "Запланована дата завершення", "plannedCompletionDate");
    private static final AttributeName DATE_END = new AttributeNameSimple(9, "Дата завершення", "dateTimeEnd");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(10, "Опис", "description");

    public TaskTestingUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                SPRINT.getNameForUser(),
                TASK.getNameForUser(),
                PERFORMER.getNameForUser(),
                STATE.getNameForUser(),
                PRIORITY.getNameForUser(),
                TASK_TESTING.getNameForUser(),
                DATE_START.getNameForUser(),
                PLANNED_COMPLETION.getNameForUser(),
                DATE_END.getNameForUser(),
                DESCRIPTION.getNameForUser()
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
                value.getPerformer(),
                value.getStateTesting(),
                value.getPriority(),
                value.getNameTaskTesting(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var task = table.getValueAt(indexRow, TASK.getId());
        var nameTaskTesting = table.getValueAt(indexRow, TASK_TESTING.getId());

        return new SearchCriteria[] {
                new SearchCriteria(TASK.getNameForSystem(), task, SearchOperation.EQUAL),
                new SearchCriteria(TASK_TESTING.getNameForSystem(), nameTaskTesting, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createCoherentComboBoxPanels(PROJECT, SPRINT, TASK));
        components.addAll(createComboBoxPanels(false, PERFORMER, STATE, PRIORITY));
        components.addAll(createTextFieldPanels(false, TASK_TESTING, DATE_START, PLANNED_COMPLETION, DATE_END, DESCRIPTION));
        addAllComponentsToPanel(components);

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
                    TASK_TESTING.getNameForUser(), DATE_START.getNameForUser(), PLANNED_COMPLETION.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull TaskTesting entity) {
        return new SearchCriteria[] {
                new SearchCriteria(TASK.getNameForSystem(), entity.getTask(), SearchOperation.EQUAL),
                new SearchCriteria(TASK_TESTING.getNameForSystem(), entity.getNameTaskTesting(), SearchOperation.EQUAL)
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

        panelComponent.updateComboBox(PROJECT, projectEntity);
        panelComponent.updateComboBox(SPRINT, sprintEntity);
        panelComponent.updateComboBox(TASK, entity.getTask());
        panelComponent.updateComboBox(PERFORMER, entity.getPerformer());
        panelComponent.updateComboBox(STATE, entity.getStateTesting());
        panelComponent.updateComboBox(PRIORITY, entity.getPriority());

        panelComponent.updateTextField(TASK_TESTING, entity.getNameTaskTesting());
        panelComponent.updateTextField(DATE_START, entity.getDateTimeCreate().toString());
        panelComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        panelComponent.updateTextField(DATE_END, dateEnd);
        panelComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }

    @Override
    public JPanel selectEntryPanel() {
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createIntervalPanels(DATE_START, PLANNED_COMPLETION));
        components.addAll(createComboBoxPanels(true, TASK, PERFORMER, STATE, PRIORITY));
        components.addAll(createTextFieldPanels(true, TASK_TESTING));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_START, PLANNED_COMPLETION));
        searchCriteria.addAll(criteriaFromComboBox(TASK, PERFORMER, STATE, PRIORITY));
        searchCriteria.addAll(criteriaTextField(TASK_TESTING));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, SPRINT, TASK, PERFORMER, STATE, PRIORITY);
            createGraphUI(variants);
        };
    }
}
