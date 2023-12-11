package org.university.ui.control_panel.realization;

import org.university.business_logic.abstracts.SelectWithParametersModelView;
import org.university.ui.mediator.interfaces.Mediator;
import org.university.business_logic.abstracts.ReferenceBookModelView;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SelectTablePanel extends ControlPanelImpl {
    private SelectWithParametersModelView modelView;

    public SelectTablePanel(Mediator mediator){
        super(mediator);
    }

    @Override
    protected ActionListener getCommand() {
        return modelView.commandSelect();
    }

    @Override
    protected JPanel getEntryPanel() {
        return modelView.selectEntryPanel();
    }


    @Override
    public void setModelView(ReferenceBookModelView<?> modelView) {
        this.modelView = (SelectWithParametersModelView) modelView;
    }

    @Override
    protected List<JButton> createButtons() {
        JButton graph = new JButton("Граф");
        graph.addActionListener(modelView.createGraph());

        button = new JButton("Обрати");

        JButton reset = new JButton("Скинути");
        reset.addActionListener(updateTable());

        return List.of(graph, button, reset);
    }

    @Override
    public String getName() {
        return "SelectTablePanel";
    }
}
