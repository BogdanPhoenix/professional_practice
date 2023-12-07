package org.university.business_logic.abstracts;

import org.university.business_logic.action_with_database.Update;
import org.university.entities.TableID;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface UpdateModelView<T extends TableID> extends Update<T> {
    JPanel dataEntryPanel();
    ActionListener commandUpdate();
    ActionListener selectEntity();
}
