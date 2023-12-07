package org.university.ui.control_panel.realization;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.abstracts.UpdateModelView;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import java.awt.*;

public class UpdatePanel extends InsertPanel {
    private UpdateModelView<?> modelView;

    public UpdatePanel(Mediator mediator) {
        super(mediator);
    }

    @Override
    public String getName() {
        return "UpdatePanel";
    }

    @Override
    protected @NotNull JPanel createPanelButton(){
        JPanel panel = new JPanel(new BorderLayout());

        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setPreferredSize(SIZE_PANEL_BUTTON);
        panel.setMaximumSize(SIZE_PANEL_BUTTON);
        panel.setMinimumSize(SIZE_PANEL_BUTTON);

        JButton select = new JButton("Обрати");
        select.addActionListener(modelView.selectEntity());

        button = new JButton("Оновити");
        button.addActionListener(updateTable());

        panel.add(select);
        panel.add(button);
        panel.setBorder(HORIZONTAL_BORDER);

        return panel;
    }

    @Override
    protected void updateButtonLogic(){
        button.removeActionListener(command);
        command = modelView.commandUpdate();
        button.addActionListener(command);
    }

    @Override
    protected void updateDataEntryPanel(@NotNull JPanel controlPanel){
        controlPanel.remove(dataEntryPanel);
        dataEntryPanel = modelView.dataEntryPanel();
    }

    @Override
    public void setModelView(TableModelView<?> modelView) {
        this.modelView = modelView;
    }
}
