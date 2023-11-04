package org.university.business_logic.tables.logic.util.view;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.view.FullTaskInfo;

import java.util.List;

public class FullTaskInfoUtil extends TableModelView implements Select<FullTaskInfo> {
    public FullTaskInfoUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Відповідальний", "Виконання", "Пріоритет", "Складність", "Дата старту виконання", "Запланована дата завершення", "Дата завершення", "Опис");
        nameTable = "Завдання";
    }
    @Override
    protected void addRows() {
        List<FullTaskInfo> fullTaskInfos = selectAll();
        for(var info : fullTaskInfos){
            model.addRow(new Object[]{
                    info.getNameProject(),
                    info.getNameSprint(),
                    info.getNameTask(),
                    info.getFullNameUser(),
                    info.getNameExecution(),
                    info.getNamePriority(),
                    info.getNameComplexity(),
                    info.getDateTimeCreate(),
                    info.getPlannedCompletionDate(),
                    info.getDateTimeEnd(),
                    info.getDescription()
            });
        }
    }

    @Override
    public Class<FullTaskInfo> resolveEntityClass() {
        return FullTaskInfo.class;
    }
}
