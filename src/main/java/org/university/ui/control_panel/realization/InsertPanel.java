package org.university.ui.control_panel.realization;

import org.university.business_logic.abstracts.InsertModelView;
import org.university.ui.mediator.interfaces.Mediator;
import org.university.business_logic.abstracts.ReferenceBookModelView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class InsertPanel extends ControlPanelImpl {
    private InsertModelView<?> modelView;

    public InsertPanel(Mediator mediator){
        super(mediator);
    }

    @Override
    protected ActionListener getCommand() {
        return modelView.commandSave();
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
        button = new JButton("Додати");
        button.addActionListener(updateTable());

        return List.of(button);
    }

    @Override
    public String getName() {
        return "InsertPanel";
    }
}
