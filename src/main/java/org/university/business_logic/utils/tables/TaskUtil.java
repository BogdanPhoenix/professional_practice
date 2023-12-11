package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.PriorityTask;
import org.university.entities.reference_book.TypeComplexity;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.entities.tables.Sprint;
import org.university.entities.tables.Task;
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

public class TaskUtil extends TableModelView<Task> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName SPRINT = new AttributeNameComboBox(1, "Спринт", "sprint", Sprint.class);
    private static final AttributeName PERFORMER = new AttributeNameComboBox(2, "Відповідальний", "performer", Employee.class);
    private static final AttributeName EXECUTION = new AttributeNameComboBox(3, "Виконання", "executionStatus", ExecutionStatus.class);
    private static final AttributeName PRIORITY = new AttributeNameComboBox(4, "Пріоритет", "priority", PriorityTask.class);
    private static final AttributeName COMPLEXITY = new AttributeNameComboBox(5, "Складність", "complexity", TypeComplexity.class);
    private static final AttributeName TASK = new AttributeNameSimple(6, "Завдання", "nameTask");
    private static final AttributeName DATE_START = new AttributeNameSimple(7, "Дата старту виконання", "dateTimeCreate");
    private static final AttributeName PLANNED_COMPLETION = new AttributeNameSimple(8, "Запланована дата завершення", "planned_completion_date");
    private static final AttributeName DATE_END = new AttributeNameSimple(9, "Дата завершення", "dateTimeEnd");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(10, "Опис", "description");

    public TaskUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                SPRINT.getNameForUser(),
                PERFORMER.getNameForUser(),
                EXECUTION.getNameForUser(),
                PRIORITY.getNameForUser(),
                COMPLEXITY.getNameForUser(),
                TASK.getNameForUser(),
                DATE_START.getNameForUser(),
                PLANNED_COMPLETION.getNameForUser(),
                DATE_END.getNameForUser(),
                DESCRIPTION.getNameForUser()
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
                value.getPerformer(),
                value.getExecutionStatus(),
                value.getPriority(),
                value.getComplexity(),
                value.getNameTask(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var sprint = table.getValueAt(indexRow, SPRINT.getId());
        var nameTask = table.getValueAt(indexRow, TASK.getId());

        return new SearchCriteria[] {
                new SearchCriteria(SPRINT.getNameForSystem(), sprint, SearchOperation.EQUAL),
                new SearchCriteria(TASK.getNameForSystem(), nameTask, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        clearPanelBody();

        List<JPanel> components = new ArrayList<>();
        components.addAll(createCoherentComboBoxPanels(PROJECT, SPRINT));
        components.addAll(createComboBoxPanels(false, PERFORMER, EXECUTION, PRIORITY, COMPLEXITY));
        components.addAll(createTextFieldPanels(false, TASK, DATE_START, PLANNED_COMPLETION, DATE_END, DESCRIPTION));
        addAllComponentsToPanel(components);

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
                    TASK.getNameForUser(), DATE_START.getNameForUser(), PLANNED_COMPLETION.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Task entity) {
        return new SearchCriteria[] {
                new SearchCriteria(SPRINT.getNameForSystem(), entity.getSprint(), SearchOperation.EQUAL),
                new SearchCriteria(TASK.getNameForSystem(), entity.getNameTask(), SearchOperation.EQUAL)
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

        panelComponent.updateComboBox(PROJECT, entity.getSprint().getProject());
        panelComponent.updateComboBox(SPRINT, entity.getSprint());
        panelComponent.updateComboBox(PERFORMER, entity.getPerformer());
        panelComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        panelComponent.updateComboBox(PRIORITY, entity.getPriority());
        panelComponent.updateComboBox(COMPLEXITY, entity.getComplexity());

        panelComponent.updateTextField(TASK, entity.getNameTask());
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
        components.addAll(createComboBoxPanels(true, SPRINT, PERFORMER, EXECUTION, PRIORITY, COMPLEXITY));
        components.addAll(createTextFieldPanels(true, TASK));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_START, PLANNED_COMPLETION));
        searchCriteria.addAll(criteriaFromComboBox(SPRINT, PERFORMER, EXECUTION, PRIORITY, COMPLEXITY));
        searchCriteria.addAll(criteriaTextField(TASK));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, SPRINT, PERFORMER, EXECUTION, PRIORITY, COMPLEXITY);
            createGraphUI(variants);
        };
    }
}
