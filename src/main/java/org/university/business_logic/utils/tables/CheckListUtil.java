package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.tables.*;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CheckListUtil extends TableModelView<CheckList> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName SPRINT = new AttributeNameComboBox(1, "Спринт", "sprint", Sprint.class);
    private static final AttributeName TASK = new AttributeNameComboBox(2, "Завдання", "task", Task.class);
    private static final AttributeName PERFORMER = new AttributeNameComboBox(3, "Відповідальний", "performer", Employee.class);
    private static final AttributeName EXECUTION = new AttributeNameComboBox(4, "Виконання", "executionStatus", ExecutionStatus.class);
    private static final AttributeName PRIORITY = new AttributeNameComboBox(5, "Пріоритет", "priority", PriorityTask.class);
    private static final AttributeName CHECK_LIST_NAME = new AttributeNameSimple(6, "Підзавдання", "nameTask");
    private static final AttributeName DATE_START = new AttributeNameSimple(7, "Дата старту виконання", "dateTimeCreate");
    private static final AttributeName PLANNED_COMPLETION = new AttributeNameSimple(8, "Запланована дата завершення", "plannedCompletionDate");
    private static final AttributeName DATE_END = new AttributeNameSimple(9, "Дата завершення", "dateTimeEnd");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(10, "Опис", "description");

    public CheckListUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                SPRINT.getNameForUser(),
                TASK.getNameForUser(),
                PERFORMER.getNameForUser(),
                EXECUTION.getNameForUser(),
                PRIORITY.getNameForUser(),
                CHECK_LIST_NAME.getNameForUser(),
                DATE_START.getNameForUser(),
                PLANNED_COMPLETION.getNameForUser(),
                DATE_END.getNameForUser(),
                DESCRIPTION.getNameForUser()
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
                value.getExecutionStatus(),
                value.getPriority(),
                value.getNameTask(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var task = table.getValueAt(indexRow, TASK.getId());
        var checkList = table.getValueAt(indexRow, CHECK_LIST_NAME.getId());

        return new SearchCriteria[] {
                new SearchCriteria(TASK.getNameForSystem(), task, SearchOperation.EQUAL),
                new SearchCriteria(CHECK_LIST_NAME.getNameForSystem(), checkList, SearchOperation.EQUAL),
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createCoherentComboBoxPanels(PROJECT, SPRINT, TASK));
        components.addAll(createComboBoxPanels(false, PERFORMER, EXECUTION, PRIORITY));
        components.addAll(createTextFieldPanels(false, CHECK_LIST_NAME, DATE_START, PLANNED_COMPLETION, DATE_END, DESCRIPTION));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameTask = valueFromTextField(CHECK_LIST_NAME);
        String dateCreate = valueFromTextField(DATE_START);
        String plannedDate = valueFromTextField(PLANNED_COMPLETION);

        if(nameTask.isEmpty() || dateCreate.isEmpty() || plannedDate.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    CHECK_LIST_NAME.getNameForUser(), DATE_START.getNameForUser(), PLANNED_COMPLETION.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull CheckList entity) {
        return new SearchCriteria[] {
                new SearchCriteria(TASK.getNameForSystem(), entity.getTask(), SearchOperation.EQUAL),
                new SearchCriteria(CHECK_LIST_NAME.getNameForSystem(), entity.getNameTask(), SearchOperation.EQUAL),
        };
    }

    @Override
    protected CheckList newEntity() {
        try {
            Task task = (Task) valueFromComboBox(TASK);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            PriorityTask priority = (PriorityTask) valueFromComboBox(PRIORITY);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            String nameTask = valueFromTextField(CHECK_LIST_NAME);
            String description = valueFromTextField(DESCRIPTION);
            String dateCreate = valueFromTextField(DATE_START);
            String planned = valueFromTextField(PLANNED_COMPLETION);
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

        panelComponent.updateComboBox(PROJECT, projectEntity);
        panelComponent.updateComboBox(SPRINT, sprintEntity);
        panelComponent.updateComboBox(TASK, entity.getTask());
        panelComponent.updateComboBox(PERFORMER, entity.getPerformer());
        panelComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        panelComponent.updateComboBox(PRIORITY, entity.getPriority());

        panelComponent.updateTextField(CHECK_LIST_NAME, entity.getNameTask());
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
        components.addAll(createComboBoxPanels(true, TASK, PERFORMER, EXECUTION, PRIORITY));
        components.addAll(createTextFieldPanels(true, CHECK_LIST_NAME));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_START, PLANNED_COMPLETION));
        searchCriteria.addAll(criteriaFromComboBox(TASK, PERFORMER, EXECUTION, PRIORITY));
        searchCriteria.addAll(criteriaTextField(CHECK_LIST_NAME));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, SPRINT, TASK, PERFORMER, EXECUTION, PRIORITY);
            createGraphUI(variants);
        };
    }
}
