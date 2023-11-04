package org.university.ui.realization.window.builder;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class WindowProgram {
    private final JFrame frame;

    public WindowProgram(String title, Dimension windowSize, JMenuBar menuBar, JPanel panelInteraction) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.add(panelInteraction);

        frame.setSize(windowSize);
    }
}
