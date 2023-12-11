package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.AccessRight;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class AccessRightUtil extends ReferenceBookModelView<AccessRight> {
    private static final AttributeName NAME_RIGHT = new AttributeNameSimple(0,"Право доступу", "nameRight");

    public AccessRightUtil(){
        titleColumns = List.of( NAME_RIGHT.getNameForUser() );
        nameTable = "Права доступу";
    }

    @Override
    public Class<AccessRight> resolveEntityClass() {
        return AccessRight.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull AccessRight value) {
        return new Object[]{ value.getNameRight() };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow){
        var value = table.getValueAt(indexRow, NAME_RIGHT.getId());
        return new SearchCriteria[]{ new SearchCriteria(NAME_RIGHT.getNameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = panelComponent.createTextFieldInputPanel(NAME_RIGHT);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(NAME_RIGHT);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull AccessRight entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_RIGHT.getNameForSystem(), entity.getNameRight(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected AccessRight newEntity(){
        try {
            String value = valueFromTextField(NAME_RIGHT);
            return AccessRight.builder()
                    .nameRight(value)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    public void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        panelComponent.updateTextField(NAME_RIGHT, entity.getNameRight());
    }
}
