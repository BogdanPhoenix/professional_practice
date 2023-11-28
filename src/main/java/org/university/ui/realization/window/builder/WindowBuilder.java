package org.university.ui.realization.window.builder;

import org.university.ui.interfaces.window.builder.Builder;
import org.university.ui.interfaces.window.FinalWindow;

import javax.swing.*;
import java.awt.*;

public class WindowBuilder implements Builder, FinalWindow<JFrame> {
    private String title;
    private Dimension size;
    private Dimension minSize;
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
    public void setMinSize(Dimension minSize) {
        this.minSize = minSize;
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
    public JFrame getResult() {
        return WindowProgram.createFrame(title, size, minSize, menuBar, panelInteraction);
    }
}
