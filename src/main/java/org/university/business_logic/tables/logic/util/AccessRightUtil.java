package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.AccessRight;
import org.university.business_logic.tables.orm.ExecutionStatus;

import java.util.List;

public class AccessRightUtil extends TableModelView implements Select<AccessRight> {
    public AccessRightUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Права доступу";
    }

    @Override
    protected void addRows() {
        List<AccessRight> accessRights = selectAll();
        for (var right : accessRights){
            model.addRow(new Object[]{right.getNameRight()});
        }
    }

    @Override
    public Class<AccessRight> resolveEntityClass() {
        return AccessRight.class;
    }
}
