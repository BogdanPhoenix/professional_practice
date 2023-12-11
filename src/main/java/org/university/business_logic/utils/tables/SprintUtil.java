package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.reference_book.TypeComplexity;
import org.university.entities.tables.Project;
import org.university.entities.tables.Sprint;
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

public class SprintUtil extends TableModelView<Sprint> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Назва проекту", "project", Project.class);
    private static final AttributeName EXECUTION = new AttributeNameComboBox(1, "Стан виконання", "executionStatus", ExecutionStatus.class);
    private static final AttributeName COMPLEXITY = new AttributeNameComboBox(2, "Тип складності", "complexity", TypeComplexity.class);
    private static final AttributeName SPRINT = new AttributeNameSimple(3, "Назва спринту", "nameSprint");
    private static final AttributeName DATE_START = new AttributeNameSimple(4, "Дата початку", "dateTimeCreate");
    private static final AttributeName PLANNED_COMPLETION = new AttributeNameSimple(5, "Запланована дата закінчення", "plannedCompletionDate");
    private static final AttributeName DATE_END = new AttributeNameSimple(6, "Дата закінчення", "dateTimeEnd");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(7, "Опис", "description");

    public SprintUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                EXECUTION.getNameForUser(),
                COMPLEXITY.getNameForUser(),
                SPRINT.getNameForUser(),
                DATE_START.getNameForUser(),
                PLANNED_COMPLETION.getNameForUser(),
                DATE_END.getNameForUser(),
                DESCRIPTION.getNameForUser()
        );
        nameTable = "Спринти";
    }

    @Override
    public Class<Sprint> resolveEntityClass() {
        return Sprint.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull Sprint value) {
        return new Object[]{
                value.getProject(),
                value.getExecutionStatus(),
                value.getComplexity(),
                value.getNameSprint(),
                value.getDateTimeCreate(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var project = table.getValueAt(indexRow, PROJECT.getId());
        var nameSprint = table.getValueAt(indexRow, SPRINT.getId());

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(SPRINT.getNameForSystem(), nameSprint, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        return createPanel(false);
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameSprint = valueFromTextField(SPRINT);
        String dateStart = valueFromTextField(DATE_START);
        String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);

        if(nameSprint.isEmpty() || dateStart.isEmpty() || plannedCompletion.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s",
                    SPRINT.getNameForUser(), DATE_START.getNameForUser(), PLANNED_COMPLETION.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Sprint entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(SPRINT.getNameForSystem(), entity.getNameSprint(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Sprint newEntity() {
        try {
            Project project = (Project) valueFromComboBox(PROJECT);
            String nameSprint = valueFromTextField(SPRINT);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            TypeComplexity typeComplexity = (TypeComplexity) valueFromComboBox(COMPLEXITY);
            String dateStart = valueFromTextField(DATE_START);
            String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
            String dateEndString = valueFromTextField(DATE_END);
            String description = valueFromTextField(DESCRIPTION);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return Sprint.builder()
                    .project(project)
                    .nameSprint(nameSprint)
                    .executionStatus(executionStatus)
                    .complexity(typeComplexity)
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

        panelComponent.updateComboBox(PROJECT, entity.getProject());
        panelComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());
        panelComponent.updateComboBox(COMPLEXITY, entity.getComplexity());

        panelComponent.updateTextField(SPRINT, entity.getNameSprint());
        panelComponent.updateTextField(DATE_START, entity.getDateTimeCreate().toString());
        panelComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        panelComponent.updateTextField(DATE_END, dateEnd);
        panelComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }

    @Override
    public JPanel selectEntryPanel() {
        return createPanel(true);
    }

    private JPanel createPanel(boolean flag){
        clearPanelBody();
        List<JPanel> components = new ArrayList<>();

        if(flag){
            components.addAll(createIntervalPanels(DATE_START, PLANNED_COMPLETION));
        }

        components.addAll(createComboBoxPanels(flag, PROJECT, EXECUTION, COMPLEXITY));
        components.addAll(createTextFieldPanels(flag, names(flag)));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Contract(value = "_ -> new", pure = true)
    private AttributeName @NotNull [] names(boolean flag){
        if(flag){
            return new AttributeName[] {SPRINT};
        }
        else {
            return new AttributeName[] {SPRINT, DATE_START, PLANNED_COMPLETION, DATE_END, DESCRIPTION};
        }
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_START, PLANNED_COMPLETION));
        searchCriteria.addAll(criteriaFromComboBox(PROJECT, EXECUTION, COMPLEXITY));
        searchCriteria.addAll(criteriaTextField(SPRINT));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, EXECUTION, COMPLEXITY);
            createGraphUI(variants);
        };
    }
}
