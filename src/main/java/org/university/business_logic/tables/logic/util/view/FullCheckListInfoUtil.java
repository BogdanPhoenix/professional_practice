package org.university.business_logic.tables.logic.util.view;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.view.FullCheckListInfo;

import java.util.List;

public class FullCheckListInfoUtil extends TableModelView implements Select<FullCheckListInfo> {
    public FullCheckListInfoUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Відповідальний", "Виконання", "Пріоритет", "Дата старту виконання", "Запланована дата завершення", "Дата завершення", "Опис");
        nameTable = "Підзавдання";
    }
    @Override
    protected void addRows() {
        List<FullCheckListInfo> fullCheckListInfos = selectAll();
        for(var info : fullCheckListInfos){
            model.addRow(new Object[]{
                    info.getNameProject(),
                    info.getNameSprint(),
                    info.getNameTask(),
                    info.getNameCheckList(),
                    info.getFullNameUser(),
                    info.getNameExecution(),
                    info.getNamePriority(),
                    info.getDateTimeCreate(),
                    info.getPlannedCompletionDate(),
                    info.getDateTimeEnd(),
                    info.getDescription()
            });
        }
    }

    @Override
    public Class<FullCheckListInfo> resolveEntityClass() {
        return FullCheckListInfo.class;
    }
}
