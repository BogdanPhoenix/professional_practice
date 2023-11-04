package org.university.ui.realization.window.abstract_factory.panel_window;

import org.university.ui.interfaces.window.abstract_factory.menubar.MenuBarFactory;
import org.university.ui.interfaces.window.abstract_factory.panel_window.PanelTitleButton;
import org.university.ui.realization.window.abstract_factory.menubar.SimpleFactory;

import javax.swing.*;
import java.awt.*;

public class SimplePanelTitleButton implements PanelTitleButton {
    @Override
    public JPanel createPanel() {
        Dimension panelSize = new Dimension(100, 50);
        MenuBarFactory factory = new SimpleFactory();
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(factory.getSourceInfoMenu().create());

        JPanel panelTitleButton = new JPanel();
        panelTitleButton.setBackground(Color.BLUE);
        panelTitleButton.setPreferredSize(panelSize);

        return panelTitleButton;
    }
}
