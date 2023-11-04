package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.PriorityTask;

import java.util.List;

public class PriorityTaskUtil extends TableModelView implements Select<PriorityTask> {
    public PriorityTaskUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Пріоритети завдання";
    }

    @Override
    protected void addRows() {
        List<PriorityTask> priorityTasks = selectAll();
        for (var priorityTask : priorityTasks) {
            model.addRow(new Object[]{priorityTask.getNamePriority()});
        }
    }

    @Override
    public Class<PriorityTask> resolveEntityClass() {
        return PriorityTask.class;
    }
}
