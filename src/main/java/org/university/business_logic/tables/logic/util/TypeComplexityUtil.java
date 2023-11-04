package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.TypeComplexity;

import java.util.List;

public class TypeComplexityUtil extends TableModelView implements Select<TypeComplexity> {
    public TypeComplexityUtil(){
        titleColumns = List.of("Назва", "Числовий еквівалент");
        nameTable = "Типи складності";
    }

    @Override
    protected void addRows() {
        List<TypeComplexity> typeComplexities = selectAll();
        for (var typeComplexity : typeComplexities) {
            model.addRow(new Object[]{
                    typeComplexity.getNameComplexity(),
                    typeComplexity.getNumberValue()
            });
        }
    }

    @Override
    public Class<TypeComplexity> resolveEntityClass() {
        return TypeComplexity.class;
    }
}
