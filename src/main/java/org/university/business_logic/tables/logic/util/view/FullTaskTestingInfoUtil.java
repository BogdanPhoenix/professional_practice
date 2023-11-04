package org.university.business_logic.tables.logic.util.view;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.view.FullTaskTestingInfo;

import java.util.List;

public class FullTaskTestingInfoUtil extends TableModelView implements Select<FullTaskTestingInfo> {
    public FullTaskTestingInfoUtil(){
        titleColumns = List.of("Проект", "Спринт", "Завдання", "Тестове завдання", "Відповідальний", "Виконання", "Пріоритет", "Дата старту виконання", "Запланована дата завершення", "Дата завершення", "Опис");
        nameTable = "Завдання на тестування";
    }
    @Override
    protected void addRows() {
        List<FullTaskTestingInfo> fullTaskTestingInfos = selectAll();
        for(var info : fullTaskTestingInfos){
            model.addRow(new Object[]{
                    info.getNameProject(),
                    info.getNameSprint(),
                    info.getNameTask(),
                    info.getNameTaskTesting(),
                    info.getFullNameUser(),
                    info.getNameState(),
                    info.getNamePriority(),
                    info.getDateTimeCreate(),
                    info.getPlannedCompletionDate(),
                    info.getDateTimeEnd(),
                    info.getDescription()
            });
        }
    }

    @Override
    public Class<FullTaskTestingInfo> resolveEntityClass() {
        return FullTaskTestingInfo.class;
    }
}
