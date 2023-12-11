package org.university.business_logic.abstracts;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface SelectWithParametersModelView {
    JPanel selectEntryPanel();
    ActionListener commandSelect();
    ActionListener createGraph();
}
