package org.university.business_logic.tables.logic.util.view;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.view.FullProjectInfo;

import java.util.List;

public class FullProjectInfoUtil extends TableModelView implements Select<FullProjectInfo> {
    public FullProjectInfoUtil(){
        titleColumns = List.of("Назва проекту", "Відповідальна особа", "Стан виконання", "Дата старту проекту", "Запланована дата закінчення", "Дата закінчення",
                "Бюджет", "Інформація про замовника", "Опис проекту");
        nameTable = "Основна інформація про проекти";
    }

    @Override
    protected void addRows() {
        List<FullProjectInfo> fullProjectInfos = selectAll();
        for(var info : fullProjectInfos){
            model.addRow(new Object[]{
                    info.getNameProject(),
                    info.getFullUserName(),
                    info.getNameExecution(),
                    info.getDateTimeStart(),
                    info.getPlannedCompletionDate(),
                    info.getDateTimeEnd(),
                    info.getBudget(),
                    info.getClientIndo(),
                    info.getDescription()
            });
        }
    }

    @Override
    public Class<FullProjectInfo> resolveEntityClass() {
        return FullProjectInfo.class;
    }
}
