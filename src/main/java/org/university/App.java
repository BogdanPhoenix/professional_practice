package org.university;

import org.university.ui.components.menu_panel.realization.MenuBar;
import org.university.ui.mediator.realization.Editor;
import org.university.ui.mediator.interfaces.Mediator;
import org.university.business_logic.utils.HibernateUtil;

public class App
{
    public static void main( String[] args )
    {
        HibernateUtil.getSessionFactory();

        Mediator mediator = new Editor();
        mediator.registerComponent(new MenuBar(mediator));
        mediator.createGUI();
    }
}
