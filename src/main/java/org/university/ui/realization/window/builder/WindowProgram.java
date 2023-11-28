package org.university.ui.realization.window.builder;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class WindowProgram {
    @Getter
    private static JFrame frame;

    private WindowProgram() {}

    public static JFrame createFrame(String title, Dimension windowSize, Dimension minWindowSize, JMenuBar menuBar, JPanel panelInteraction) {
        if(frame == null) {
            frame = new JFrame(title);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setJMenuBar(menuBar);
            frame.setContentPane(panelInteraction);
            frame.setSize(windowSize);
            frame.setMinimumSize(minWindowSize);
        }

        return frame;
    }
}
