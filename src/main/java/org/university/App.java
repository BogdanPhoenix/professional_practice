package org.university;

import org.university.business_logic.tables.logic.util.HibernateUtil;
import org.university.ui.interfaces.window.FinalWindow;
import org.university.ui.interfaces.window.builder.Builder;
import org.university.ui.realization.window.builder.Direction;
import org.university.ui.realization.window.builder.WindowBuilder;
import org.university.ui.realization.window.builder.WindowProgram;

import javax.swing.*;

public class App
{
    public static void main( String[] args )
    {
        HibernateUtil.getSessionFactory();

        Direction direction = new Direction();
        Builder builderWindow = new WindowBuilder();

        direction.createSimpleWindow(builderWindow);

        WindowProgram window = ((FinalWindow<WindowProgram>)builderWindow).getResult();
        JFrame frame = window.getFrame();
        frame.setVisible(true);
    }
}
