package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.ExecutionStatus;
import org.university.business_logic.tables.orm.FileExtension;

import java.util.List;

public class FileExtensionUtil extends TableModelView implements Select<FileExtension> {
    public FileExtensionUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Розширення файлів";
    }

    @Override
    protected void addRows() {
        List<FileExtension> fileExtensions = selectAll();
        for (var extension : fileExtensions){
            model.addRow(new Object[]{extension.getNameExtension()});
        }
    }

    @Override
    public Class<FileExtension> resolveEntityClass() {
        return FileExtension.class;
    }
}
