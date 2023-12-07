package org.university.business_logic.abstracts;

import org.university.business_logic.action_with_database.Insert;
import org.university.entities.TableID;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface InsertModelView<T extends TableID> extends Insert<T> {
    JPanel dataEntryPanel();
    ActionListener commandSave();
}
