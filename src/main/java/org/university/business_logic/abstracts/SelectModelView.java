package org.university.business_logic.abstracts;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.action_with_database.Select;
import org.university.entities.TableID;

import javax.swing.table.DefaultTableModel;

public interface SelectModelView<T extends TableID> extends Select<T> {
    void createViewModel(@NotNull DefaultTableModel tableModel);
}
