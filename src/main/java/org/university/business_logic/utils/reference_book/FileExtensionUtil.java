package org.university.business_logic.utils.reference_book;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.FileExtension;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.util.List;

public class FileExtensionUtil extends ReferenceBookModelView<FileExtension> {
    private static final AttributeName NAME_EXTENSION = new AttributeNameSimple(0, "Розширення файлу", "nameExtension");

    public FileExtensionUtil(){
        titleColumns = List.of(NAME_EXTENSION.getNameForUser());
        nameTable = "Розширення файлів";
    }

    @Override
    public Class<FileExtension> resolveEntityClass() {
        return FileExtension.class;
    }

    @Override
    protected Object[] createAttribute(@NotNull FileExtension value) {
        return new Object[]{ value.getNameExtension() };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow){
        var value = table.getValueAt(indexRow, NAME_EXTENSION.getId());
        return new SearchCriteria[]{ new SearchCriteria(NAME_EXTENSION.getNameForSystem(), value, SearchOperation.EQUAL) };
    }

    @Override
    public JPanel dataEntryPanel() {
        if (panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = panelComponent.createTextFieldInputPanel(NAME_EXTENSION);
        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        checkCompletenessFields(NAME_EXTENSION);
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull FileExtension entity) {
        return new SearchCriteria[]{
                new SearchCriteria(NAME_EXTENSION.getNameForSystem(), entity.getNameExtension(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected FileExtension newEntity() {
        try {
            String value = valueFromTextField(NAME_EXTENSION);

            return FileExtension.builder()
                    .nameExtension(value)
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();
        panelComponent.updateTextField(NAME_EXTENSION, entity.getNameExtension());
    }
}
