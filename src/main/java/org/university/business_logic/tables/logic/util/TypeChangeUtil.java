package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.TypeChange;

import java.util.List;

public class TypeChangeUtil extends TableModelView implements Select<TypeChange> {
    public TypeChangeUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Типи зміни";
    }

    @Override
    protected void addRows() {
        List<TypeChange> typeChanges = selectAll();
        for (var type : typeChanges){
            model.addRow(new Object[]{type.getNameTypeChange()});
        }
    }

    @Override
    public Class<TypeChange> resolveEntityClass() {
        return TypeChange.class;
    }
}
