package org.university.business_logic.tables.logic.util.view;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.view.FullSprintInfo;

import java.util.List;

public class FullSprintInfoUtil extends TableModelView implements Select<FullSprintInfo> {
    public FullSprintInfoUtil(){
        titleColumns = List.of("Назва проекту", "Назва спринту", "Стан виконання", "Тип складності", "Дата початку", "Запланована дата закінчення", "Дата закінчення", "Опис");
        nameTable = "Основна інформація про спринт";
    }

    @Override
    protected void addRows() {
        List<FullSprintInfo> fullSprintInfos = selectAll();
        for(var info : fullSprintInfos){
            model.addRow(new Object[]{
                    info.getNameProject(),
                    info.getNameSprint(),
                    info.getNameExecution(),
                    info.getNameComplexity(),
                    info.getDateTimeCreate(),
                    info.getPlannedCompletionDate(),
                    info.getDateTimeEnd(),
                    info.getDescription()
            });
        }
    }

    @Override
    public Class<FullSprintInfo> resolveEntityClass() {
        return FullSprintInfo.class;
    }
}
