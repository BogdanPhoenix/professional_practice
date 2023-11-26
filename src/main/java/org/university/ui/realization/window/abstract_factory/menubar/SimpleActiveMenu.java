package org.university.ui.realization.window.abstract_factory.menubar;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.ControlPanel;
import org.university.business_logic.comands.WindowViewCommand;
import org.university.ui.interfaces.window.abstract_factory.menubar.ActiveMenu;
import org.university.ui.realization.panel_interaction.select.ViewPanel;
import org.university.ui.realization.panel_interaction.update_insert.InsertPanelImpl;

import javax.swing.*;
import java.awt.event.ActionListener;

public class SimpleActiveMenu implements ActiveMenu {
    private final JMenu menu;

    public SimpleActiveMenu(){
        menu = new JMenu("Дії");
    }

    @Override
    public JMenu create() {
        createItem("Перегляд", ViewPanel.getInstance());
        createItem("Створити", InsertPanelImpl.getInstance());

        return menu;
    }

    private void createItem(final String name, final ControlPanel panel){
        JMenuItem item = new JMenuItem(name);
        item.addActionListener(createListener(panel));
        menu.add(item);
    }

    @Contract(pure = true)
    private @NotNull ActionListener createListener(final ControlPanel panel){
        WindowViewCommand view = new WindowViewCommand(panel);

        return e -> view.execute();
    }
}
