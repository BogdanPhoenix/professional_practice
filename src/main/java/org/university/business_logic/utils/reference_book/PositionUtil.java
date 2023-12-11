package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.Position;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class PositionUtil extends ReferenceBookModelView<Position> {
    private static final AttributeName NAME_POSITION = new AttributeNameSimple(0, "Посада", "namePosition");

    public PositionUtil(){
        titleColumns = List.of(NAME_POSITION.getNameForUser());
        nameTable = "Посади";
    }

    @Override
    public Class<Position> resolveEntityClass() {
        return Position.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull Position value) {
        return new Object[]{ value.getNamePosition() };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow){
        var value = table.getValueAt(indexRow, NAME_POSITION.getId());
        return new SearchCriteria[]{ new SearchCriteria(NAME_POSITION.getNameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = panelComponent.createTextFieldInputPanel(NAME_POSITION);
        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(NAME_POSITION);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Position entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_POSITION.getNameForSystem(), entity.getNamePosition(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Position newEntity() {
        try {
            String value = valueFromTextField(NAME_POSITION);

            return Position.builder()
                    .namePosition(value)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        panelComponent.updateTextField(NAME_POSITION, entity.getNamePosition());
    }
}
