package org.university.business_logic.abstracts;

import org.university.business_logic.action_with_database.Delete;
import org.university.entities.TableID;

import java.awt.event.ActionListener;

public interface DeleteModelView<T extends TableID> extends Delete<T> {
    ActionListener commandDelete();
}
