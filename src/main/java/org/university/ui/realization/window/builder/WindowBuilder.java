package org.university.ui.realization.window.builder;

import org.university.ui.interfaces.window.builder.Builder;
import org.university.ui.interfaces.window.FinalWindow;

import javax.swing.*;
import java.awt.*;

public class WindowBuilder implements Builder, FinalWindow<WindowProgram> {
    private String title;
    private Dimension size;
    private JMenuBar menuBar;
    private JPanel panelInteraction;

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setSize(Dimension size) {
        this.size = size;
    }

    @Override
    public void setMenuBar(JMenuBar menu) {
        this.menuBar = menu;
    }

    @Override
    public void setPanelInteraction(JPanel panel) {
        this.panelInteraction = panel;
    }

    @Override
    public WindowProgram getResult() {
        return new WindowProgram(title, size, menuBar, panelInteraction);
    }
}
