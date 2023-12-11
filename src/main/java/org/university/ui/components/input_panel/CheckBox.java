package org.university.ui.components.input_panel;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeName;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CheckBox extends JCheckBox {
    private final Component[] parents;

    public CheckBox(@NotNull AttributeName name, Component... parents){
        super(name.getNameForUser());

        this.parents = parents;
        this.setName(name.getNameForSystem());
        this.setSelected(true);
        this.addActionListener(this::active);
    }

    private void active(ActionEvent e) {
        for(var component : parents){
            component.setEnabled(this.isSelected());
        }
    }
}
