package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.TypeComplexity;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TypeComplexityUtil extends ReferenceBookModelView<TypeComplexity> {
    private static final AttributeName NAME_COMPLEXITY = new AttributeNameSimple(0, "Назва", "nameComplexity");
    private static final AttributeName NUMBER_VALUE = new AttributeNameSimple(1, "Числовий еквівалент", "numberValue");

    public TypeComplexityUtil(){
        titleColumns = List.of(
                NAME_COMPLEXITY.getNameForUser(),
                NUMBER_VALUE.getNameForUser()
        );
        nameTable = "Типи складності";
    }

    @Override
    public Class<TypeComplexity> resolveEntityClass() {
        return TypeComplexity.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull TypeComplexity value){
        return new Object[]{
                value.getNameComplexity(),
                value.getNumberValue()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var name = table.getValueAt(indexRow, NAME_COMPLEXITY.getId());
        var num = table.getValueAt(indexRow, NUMBER_VALUE.getId());

        return new SearchCriteria[]{
                new SearchCriteria(NAME_COMPLEXITY.getNameForSystem(), name, SearchOperation.EQUAL),
                new SearchCriteria(NUMBER_VALUE.getNameForSystem(), num, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null){
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(2, 1));

        panelBody.add(panelComponent.createTextFieldInputPanel(NAME_COMPLEXITY));
        panelBody.add(panelComponent.createTextFieldInputPanel(NUMBER_VALUE));

        panelBody = panelComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameComplexity = valueFromTextField(NAME_COMPLEXITY);
        String numValue = valueFromTextField(NUMBER_VALUE);

        if(nameComplexity.isEmpty() || numValue.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n",
                    NAME_COMPLEXITY.getNameForUser(), NUMBER_VALUE.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull TypeComplexity entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_COMPLEXITY.getNameForSystem(), entity.getNameComplexity(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected TypeComplexity newEntity() {
        try {
            String name = valueFromTextField(NAME_COMPLEXITY);
            int value = Integer.parseInt(valueFromTextField(NUMBER_VALUE));

            return TypeComplexity.builder()
                    .nameComplexity(name)
                    .numberValue(value)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        panelComponent.updateTextField(NAME_COMPLEXITY, entity.getNameComplexity());
        panelComponent.updateTextField(NUMBER_VALUE, String.valueOf(entity.getNumberValue()));
    }
}
