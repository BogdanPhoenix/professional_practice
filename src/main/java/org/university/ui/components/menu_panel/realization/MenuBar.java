package org.university.ui.components.menu_panel.realization;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.models.interfaces.Model;
import org.university.business_logic.models.realization.ReferenceBookModel;
import org.university.business_logic.models.realization.TableModel;
import org.university.ui.components.menu_panel.interfaces.Component;
import org.university.ui.control_panel.interfaces.ControlPanel;
import org.university.ui.control_panel.realization.*;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MenuBar extends JMenuBar implements Component {
    private transient Mediator mediator;
    private JMenuItem viewReferenceItem;

    public MenuBar(Mediator mediator){
        this.mediator = mediator;

        createReferenceBookMenu();
        createTableMenu();

        var listener = viewReferenceItem.getActionListeners()[0];
        listener.actionPerformed(new ActionEvent(viewReferenceItem, ActionEvent.ACTION_PERFORMED, null));
    }

    private void createReferenceBookMenu(){
        viewReferenceItem = createMenuItem("Перегляд", new SelectReferenceBookPanel(), ReferenceBookModel.getInstance());
        JMenuItem insertReferenceItem = createMenuItem("Створити", new InsertPanel(mediator), ReferenceBookModel.getInstance());
        JMenuItem deleteReferenceItem = createMenuItem("Видалити", new DeletePanel(mediator), ReferenceBookModel.getInstance());
        JMenuItem updateReferenceItem = createMenuItem("Оновити", new UpdatePanel(mediator), ReferenceBookModel.getInstance());

        JMenu referenceBook = createMenu("Довідники");
        referenceBook.add(viewReferenceItem);
        referenceBook.add(insertReferenceItem);
        referenceBook.add(deleteReferenceItem);
        referenceBook.add(updateReferenceItem);

        this.add(referenceBook);
    }

    private void createTableMenu(){
        JMenuItem viewTableItem = createMenuItem("Перегляд", new SelectTablePanel(mediator), TableModel.getInstance());
        JMenuItem insertTableItem = createMenuItem("Створити", new InsertPanel(mediator), TableModel.getInstance());
        JMenuItem deleteTableItem = createMenuItem("Видалити", new DeletePanel(mediator), TableModel.getInstance());
        JMenuItem updateTableItem = createMenuItem("Оновити", new UpdatePanel(mediator), TableModel.getInstance());

        JMenu table = createMenu("Таблиці");
        table.add(viewTableItem);
        table.add(insertTableItem);
        table.add(deleteTableItem);
        table.add(updateTableItem);

        this.add(table);
    }

    private @NotNull JMenuItem createMenuItem(String name, ControlPanel controlPanel, Model modelView){
        JMenuItem item = new MenuItem(mediator, controlPanel, modelView);
        item.setText(name);
        return item;
    }

    private @NotNull JMenu createMenu(String name){
        JMenu menu = new Menu(mediator);
        menu.setText(name);
        return menu;
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "MenuBar";
    }
}
