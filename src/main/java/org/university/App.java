package org.university;

import org.university.ui.interfaces.window.FinalWindow;
import org.university.ui.interfaces.window.builder.Builder;
import org.university.ui.realization.panel_interaction.logic.HibernateUtil;
import org.university.ui.realization.window.builder.Direction;
import org.university.ui.realization.window.builder.WindowBuilder;

import javax.swing.*;

public class App
{
    public static void main( String[] args )
    {
        HibernateUtil.getSessionFactory();

        Direction direction = new Direction();
        Builder builderWindow = new WindowBuilder();

        direction.createSimpleWindow(builderWindow);

        JFrame frame =  ((FinalWindow<JFrame>)builderWindow).getResult();
        frame.setVisible(true);
    }
}
