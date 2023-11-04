package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.BinFileProject;
import org.university.business_logic.tables.orm.Employee;

import java.util.List;

public class BinFileProjectUtil extends TableModelView implements Select<BinFileProject> {
    public BinFileProjectUtil(){
        titleColumns = List.of("Проект", "Виконавець", "Розширення файлу", "Назва файлу", "Файл", "Дата завантаження", "Опис");
        nameTable = "Бінарні файли";
    }

    @Override
    protected void addRows() {
        List<BinFileProject> binFileProjects = selectAll();
        for (var binFileProject : binFileProjects){
            Employee performer = binFileProject.getPerformer();
            model.addRow(new Object[]{
                    binFileProject.getProject().getNameProject(),
                    performer.getFirstName() + " " + performer.getNameUser(),
                    binFileProject.getFileExtension().getNameExtension(),
                    binFileProject.getNameFile(),
                    binFileProject.getDateTimeDown(),
                    binFileProject.getDescription()
            });
        }
    }

    @Override
    public Class<BinFileProject> resolveEntityClass() {
        return BinFileProject.class;
    }
}
