package org.university.business_logic.tables.logic.interfaces;

import lombok.Getter;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public abstract class TableModelView {
    protected static DefaultTableModel model = new DefaultTableModel();
    protected List<String> titleColumns;
    @Getter
    protected String nameTable;

    public static AbstractTableModel getTableModel(){
        return model;
    }

    public void updateTableModel(){
        clearModel();
        updateColumnTitle();
        addRows();
    }

    protected static void clearModel(){
        model.setRowCount(0);
        model.setColumnCount(0);
    }

    private void updateColumnTitle(){
        for (var title : titleColumns){
            model.addColumn(title);
        }
    }

    protected abstract void addRows();
}
