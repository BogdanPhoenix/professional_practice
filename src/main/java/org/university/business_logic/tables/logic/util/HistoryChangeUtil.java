package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.Employee;
import org.university.business_logic.tables.orm.HistoryChange;
import org.university.business_logic.tables.orm.Project;
import org.university.business_logic.tables.orm.TypeChange;

import java.util.List;

public class HistoryChangeUtil extends TableModelView implements Select<HistoryChange> {
    public HistoryChangeUtil(){
        titleColumns = List.of("Проект", "Тип зміни", "Виконавець", "Назва", "Дата створення");
        nameTable = "Історія змін";
    }

    @Override
    protected void addRows() {
        List<HistoryChange> historyChanges = selectAll();
        for (HistoryChange historyChange : historyChanges) {
            Project project = historyChange.getProject();
            Employee performer = historyChange.getPerformer();
            TypeChange typeChange = historyChange.getTypeChange();

            model.addRow(new Object[]{
                    project.getNameProject(),
                    typeChange.getNameTypeChange(),
                    performer.getFirstName() + " " + performer.getNameUser(),
                    historyChange.getNameChange(),
                    historyChange.getDateTimeCreate()
            });
        }
    }

    @Override
    public Class<HistoryChange> resolveEntityClass() {
        return HistoryChange.class;
    }
}
