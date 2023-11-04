package org.university.ui.realization.window.abstract_factory.menubar;

import org.university.ui.interfaces.window.abstract_factory.menubar.ActiveMenu;
import org.university.ui.interfaces.window.abstract_factory.menubar.MenuBarFactory;
import org.university.ui.interfaces.window.abstract_factory.menubar.SourceInfoMenu;

public class SimpleFactory implements MenuBarFactory {
    @Override
    public SourceInfoMenu getSourceInfoMenu() {
        return new SimpleSourceInfoMenu();
    }

    @Override
    public ActiveMenu getActiveMenu() {
        return new SimpleActiveMenu();
    }
}
