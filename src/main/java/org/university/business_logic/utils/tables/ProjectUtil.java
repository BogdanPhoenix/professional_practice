package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.ExecutionStatus;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectUtil extends TableModelView<Project> {
    private static final AttributeName INTERMEDIARY = new AttributeNameComboBox(0, "Відповідальна особа", "intermediary", Employee.class);
    private static final AttributeName EXECUTION = new AttributeNameComboBox(1, "Стан виконання", "executionStatus", ExecutionStatus.class);
    private static final AttributeName PROJECT = new AttributeNameSimple(2, "Назва проекту", "nameProject");
    private static final AttributeName DATE_START = new AttributeNameSimple(3, "Дата старту проекту", "dateTimeStart");
    private static final AttributeName PLANNED_COMPLETION = new AttributeNameSimple(4, "Запланована дата закінчення", "plannedCompletionDate");
    private static final AttributeName DATE_END = new AttributeNameSimple(5, "Дата закінчення", "dateTimeEnd");
    private static final AttributeName BUDGET = new AttributeNameSimple(6, "Бюджет", "budget");
    private static final AttributeName CLIENT_INFO = new AttributeNameSimple(7, "Інформація про замовника", "currentData");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(8, "Опис проекту", "description");

    public ProjectUtil(){
        titleColumns = List.of(
                INTERMEDIARY.getNameForUser(),
                EXECUTION.getNameForUser(),
                PROJECT.getNameForUser(),
                DATE_START.getNameForUser(),
                PLANNED_COMPLETION.getNameForUser(),
                DATE_END.getNameForUser(),
                BUDGET.getNameForUser(),
                CLIENT_INFO.getNameForUser(),
                DESCRIPTION.getNameForUser()
        );
        nameTable = "Проекти";
    }

    @Override
    public Class<Project> resolveEntityClass() {
        return Project.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull Project value) {
        return new Object[]{
                value.getIntermediary(),
                value.getExecutionStatus(),
                value.getNameProject(),
                value.getDateTimeStart(),
                value.getPlannedCompletionDate(),
                value.getDateTimeEnd(),
                value.getBudget().toString(),
                value.getClientInfo(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var nameProject = table.getValueAt(indexRow, PROJECT.getId());
        return new SearchCriteria[] { new SearchCriteria(PROJECT.getNameForSystem(), nameProject, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        return createPanel(false);
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameProject = valueFromTextField(PROJECT);
        String dateStart = valueFromTextField(DATE_START);
        String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
        String budget = valueFromTextField(BUDGET);

        if(nameProject.isEmpty() || dateStart.isEmpty() || plannedCompletion.isEmpty() || budget.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n%s %n%s",
                    PROJECT.getNameForUser(), DATE_START.getNameForUser(),
                    PLANNED_COMPLETION.getNameForUser(), BUDGET.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Project entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), entity.getNameProject(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Project newEntity() {
        try {
            String nameProject = valueFromTextField(PROJECT);
            Employee intermediary = (Employee) valueFromComboBox(INTERMEDIARY);
            ExecutionStatus executionStatus = (ExecutionStatus) valueFromComboBox(EXECUTION);
            String dateStart = valueFromTextField(DATE_START);
            String plannedCompletion = valueFromTextField(PLANNED_COMPLETION);
            String dateEndString = valueFromTextField(DATE_END);
            String budget = valueFromTextField(BUDGET);
            String clientInfo = valueFromTextField(CLIENT_INFO);
            String description = valueFromTextField(DESCRIPTION);

            Timestamp dateEnd = convertFromStringToTimestamp(dateEndString);

            return Project.builder()
                    .executionStatus(executionStatus)
                    .intermediary(intermediary)
                    .nameProject(nameProject)
                    .dateTimeStart(Timestamp.valueOf(dateStart))
                    .plannedCompletionDate(Timestamp.valueOf(plannedCompletion))
                    .dateTimeEnd(dateEnd)
                    .budget(new BigDecimal(budget))
                    .description(description)
                    .clientInfo(clientInfo)
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

        panelComponent.updateTextField(PROJECT, entity.getNameProject());
        panelComponent.updateComboBox(INTERMEDIARY, entity.getIntermediary());
        panelComponent.updateComboBox(EXECUTION, entity.getExecutionStatus());

        panelComponent.updateTextField(DATE_START, entity.getDateTimeStart().toString());
        panelComponent.updateTextField(PLANNED_COMPLETION, entity.getPlannedCompletionDate().toString());
        panelComponent.updateTextField(DATE_END, dateEnd);
        panelComponent.updateTextField(BUDGET, entity.getBudget().toString());
        panelComponent.updateTextField(CLIENT_INFO, entity.getClientInfo());
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

        components.addAll(createComboBoxPanels(flag, INTERMEDIARY, EXECUTION));
        components.addAll(createTextFieldPanels(flag, names(flag)));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    private AttributeName @NotNull [] names(boolean flag){
        if(flag){
            return new AttributeName[] {PROJECT};
        }
        else {
            return new AttributeName[] {PROJECT, DATE_START, PLANNED_COMPLETION, DATE_END, BUDGET, CLIENT_INFO, DESCRIPTION};
        }
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_START, PLANNED_COMPLETION));
        searchCriteria.addAll(criteriaFromComboBox(INTERMEDIARY, EXECUTION));
        searchCriteria.addAll(criteriaTextField(PROJECT));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(INTERMEDIARY, EXECUTION, BUDGET);
            createGraphUI(variants);
        };
    }
}
