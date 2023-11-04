package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.ExecutionStatus;

import java.util.List;

public class ExecutionStatusUtil extends TableModelView implements Select<ExecutionStatus> {
    public ExecutionStatusUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Стани виконання";
    }

    @Override
    public Class<ExecutionStatus> resolveEntityClass() {
        return ExecutionStatus.class;
    }

    @Override
    protected void addRows() {
        List<ExecutionStatus> executionStatuses = selectAll();

        for (var executionStatus : executionStatuses){
            model.addRow(new Object[]{executionStatus.getNameExecution()});
        }
    }
}
