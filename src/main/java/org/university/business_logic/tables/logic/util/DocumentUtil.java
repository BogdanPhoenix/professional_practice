package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.Document;
import org.university.business_logic.tables.orm.Employee;

import java.util.List;

public class DocumentUtil extends TableModelView implements Select<Document> {
    public DocumentUtil(){
        titleColumns = List.of("Проект", "Виконавець", "Розширення файлу", "Назва файлу", "Файл", "Дата завантаження", "Опис");
        nameTable = "Документація";
    }

    @Override
    protected void addRows() {
        List<Document> documents = selectAll();
        for (var document : documents){
            Employee performer = document.getPerformer();
            model.addRow(new Object[]{
                    document.getProject().getNameProject(),
                    performer.getFirstName() + " " + performer.getNameUser(),
                    document.getFileExtension().getNameExtension(),
                    document.getNameFile(),
                    document.getDateTimeDown(),
                    document.getDescription()
            });
        }
    }

    @Override
    public Class<Document> resolveEntityClass() {
        return Document.class;
    }
}
