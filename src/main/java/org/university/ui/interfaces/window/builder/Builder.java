package org.university.ui.interfaces.window.builder;

import javax.swing.*;
import java.awt.*;

public interface Builder {
    void setTitle(String title);
    void setSize(Dimension size);
    void setMenuBar(JMenuBar menu);
    void setPanelInteraction(JPanel panel);
}
