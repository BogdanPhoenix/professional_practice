package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.ExecutionStatus;
import org.university.business_logic.tables.orm.StateTesting;

import java.util.List;

public class StateTestingUtil extends TableModelView implements Select<StateTesting> {
    public StateTestingUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Стани тестування";
    }

    @Override
    protected void addRows() {
        List<StateTesting> stateTestings = selectAll();
        for (var stateTesting : stateTestings){
            model.addRow(new Object[]{stateTesting.getTaskTestings()});
        }
    }

    @Override
    public Class<StateTesting> resolveEntityClass() {
        return StateTesting.class;
    }
}
