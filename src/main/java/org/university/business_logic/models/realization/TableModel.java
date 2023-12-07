package org.university.business_logic.models.realization;

import org.university.business_logic.models.interfaces.Model;
import org.university.business_logic.utils.tables.*;

import java.util.ArrayList;

public class TableModel extends Model {
    private static final Model INSTANCE = new TableModel();

    private TableModel(){
        values = new ArrayList<>();

        values.add(new BinFileProjectUtil());
        values.add(new CheckListUtil());
        values.add(new CommandProjectUtil());
        values.add(new CommentTaskUtil());
        values.add(new DocumentUtil());
        values.add(new EmployeeAccessRightUtil());
        values.add(new EmployeeUtil());
        values.add(new ProjectUtil());
        values.add(new SprintUtil());
        values.add(new TaskUtil());
        values.add(new TaskTestingUtil());
    }

    public static Model getInstance(){
        return INSTANCE;
    }
}
