package org.university.business_logic.comands;

import org.university.ControlPanel;
import org.university.StrategyControlPanel;
import org.university.ui.interfaces.panel_interaction.strategy.MainPanel;

public class WindowViewCommand implements Command {
    private final ControlPanel controlPanel;

    public WindowViewCommand(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }

    @Override
    public void execute() {
        StrategyControlPanel strategy = MainPanel.getInstance();
        strategy.setControlPanel(controlPanel);
    }
}
