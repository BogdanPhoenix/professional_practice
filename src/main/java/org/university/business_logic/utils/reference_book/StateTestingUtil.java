package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.entities.reference_book.StateTesting;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class StateTestingUtil extends TableModelView<StateTesting> {
    private static final ObjectName STATE_TESTING = new ObjectName("Стан тестування", "nameState");

    public StateTestingUtil(){
        titleColumns = List.of(STATE_TESTING.nameForUser());
        nameTable = "Стани тестування";
    }

    @Override
    public Class<StateTesting> resolveEntityClass() {
        return StateTesting.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull StateTesting value) {
        return new Object[]{ value.getNameState() };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow){
        String value = (String)table.getValueAt(indexRow, 0);
        return new SearchCriteria[]{ new SearchCriteria(STATE_TESTING.nameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = windowComponent.createTextFieldInputPanel(STATE_TESTING);
        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(STATE_TESTING);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull StateTesting entity) {
        return new SearchCriteria[]{
                new SearchCriteria(STATE_TESTING.nameForSystem(), entity.getNameState(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected StateTesting newEntity() {
        try {
            String value = valueFromTextField(STATE_TESTING);

            return StateTesting.builder()
                    .nameState(value)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        windowComponent.updateTextField(STATE_TESTING, entity.getNameState());
    }
}
