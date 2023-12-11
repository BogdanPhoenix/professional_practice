package org.university.ui.control_panel.realization;

import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.business_logic.abstracts.UpdateModelView;
import org.university.ui.mediator.interfaces.Mediator;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class UpdatePanel extends ControlPanelImpl {
    private UpdateModelView<?> modelView;

    public UpdatePanel(Mediator mediator) {
        super(mediator);
    }

    @Override
    public String getName() {
        return "UpdatePanel";
    }

    @Override
    protected ActionListener getCommand() {
        return modelView.commandUpdate();
    }

    @Override
    protected JPanel getEntryPanel() {
        return modelView.dataEntryPanel();
    }

    @Override
    public void setModelView(ReferenceBookModelView<?> modelView) {
        this.modelView = modelView;
    }

    @Override
    protected List<JButton> createButtons() {
        JButton select = new JButton("Обрати");
        select.addActionListener(modelView.selectEntity());

        button = new JButton("Оновити");
        button.addActionListener(updateTable());

        return List.of(select, button);
    }
}
