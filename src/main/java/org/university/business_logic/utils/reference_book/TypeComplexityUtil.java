package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.entities.reference_book.TypeComplexity;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TypeComplexityUtil extends TableModelView<TypeComplexity> {
    private static final ObjectName NAME_COMPLEXITY = new ObjectName("Назва", "nameComplexity");
    private static final ObjectName NUMBER_VALUE = new ObjectName("Числовий еквівалент", "numberValue");

    public TypeComplexityUtil(){
        titleColumns = List.of(
                NAME_COMPLEXITY.nameForUser(),
                NUMBER_VALUE.nameForUser()
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
        String name = (String) table.getValueAt(indexRow, 0);
        Integer num = (Integer) table.getValueAt(indexRow, 1);

        return new SearchCriteria[]{
                new SearchCriteria(NAME_COMPLEXITY.nameForSystem(), name, SearchOperation.EQUAL),
                new SearchCriteria(NUMBER_VALUE.nameForSystem(), num, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null){
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(2, 1));

        panelBody.add(windowComponent.createTextFieldInputPanel(NAME_COMPLEXITY));
        panelBody.add(windowComponent.createTextFieldInputPanel(NUMBER_VALUE));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameComplexity = valueFromTextField(NAME_COMPLEXITY);
        String numValue = valueFromTextField(NUMBER_VALUE);

        if(nameComplexity.isEmpty() || numValue.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s %n",
                    NAME_COMPLEXITY.nameForUser(), NUMBER_VALUE.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull TypeComplexity entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_COMPLEXITY.nameForSystem(), entity.getNameComplexity(), SearchOperation.EQUAL)
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
        windowComponent.updateTextField(NAME_COMPLEXITY, entity.getNameComplexity());
        windowComponent.updateTextField(NUMBER_VALUE, String.valueOf(entity.getNumberValue()));
    }
}
