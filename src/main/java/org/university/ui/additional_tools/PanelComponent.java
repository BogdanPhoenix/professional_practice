package org.university.ui.additional_tools;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.ui.components.input_panel.CheckBox;
import org.university.ui.components.input_panel.ComboBox;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.entities.TableID;
import org.university.ui.components.input_panel.TextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;

public class PanelComponent {
    private static final int HEIGHT_COMPONENT = 50;
    private static final Dimension SIZE_LABEL = new Dimension(110, 20);
    protected static final Insets PANEL_BORDER = new Insets(7, 20, 7, 20);
    private static final Dimension DIST_BETWEEN_ELEMENTS_X_ASIA = new Dimension(10, 0);

    private final Map<AttributeName, Component> components;

    public PanelComponent(){
        components = new HashMap<>();
    }

    public Component getComponent(final AttributeName name){
        return components.get(name);
    }

    public void updateTextField(final @NotNull AttributeName name, String value){
        JTextField textField = (JTextField) components.get(name);
        textField.setText(value);
    }

    public void updatePasswordField(final @NotNull AttributeName name, String value){
        JPasswordField passwordField = (JPasswordField) components.get(name);
        passwordField.setText(value);
    }

    @SuppressWarnings("unchecked")
    public <S extends TableID> void updateComboBox(final @NotNull AttributeName name, S value){
        ComboBox<S> comboBox = (ComboBox<S>) components.get(name);
        comboBox.setSelectedItem(value);
    }

    public @NotNull JPanel createTextFieldInputPanel(final @NotNull AttributeName name){
        JLabel label = createLabel(name.getNameForUser());
        TextField textField = createTextField(name);

        return createPanel(label, textField);
    }

    public @NotNull JPanel createTextFieldInputWithFlagPanel(final @NotNull AttributeName name){
        JLabel label = createLabel(name.getNameForUser());
        TextField textField = createTextField(name);
        CheckBox checkBox = createCheckBox(name, textField);

        return createPanel(label, textField, checkBox);
    }

    public @NotNull JPanel createIntervalInputPanel(final @NotNull AttributeName name){
        AttributeName nameFrom = new AttributeNameSimple(0, "З", name.getNameForSystem() + "From");
        AttributeName nameTo = new AttributeNameSimple(1, "До", name.getNameForSystem() + "To");

        JLabel labelTitle = createLabel(name.getNameForUser());
        JLabel labelFrom = createLabel(nameFrom.getNameForUser());
        JLabel labelTo = createLabel(nameTo.getNameForUser());

        TextField textFieldFrom = createTextField(nameFrom);
        TextField textFieldTo = createTextField(nameTo);
        CheckBox checkBox = createCheckBox(name, textFieldFrom, textFieldTo);

        return createPanel(labelTitle, labelFrom, textFieldFrom, labelTo, textFieldTo, checkBox);
    }

    private @NotNull TextField createTextField(@NotNull AttributeName name){
        TextField textField = new TextField(name.getNameForSystem());
        components.put(name, textField);

        return textField;
    }

    public <E extends TableID> @NotNull JPanel createComboBoxPanel(
            final @NotNull AttributeName name,
            final @NotNull AttributeName children
    ){
        JLabel label = createLabel(name.getNameForUser());

        ComboBox<?> childrenComboBox = (ComboBox<?>) components.get(children);
        ComboBox<E> comboBox = createComboBox(name);
        comboBox.setChildComboBox(childrenComboBox, name);

        return createPanel(label, comboBox);
    }

    public <S extends TableID> @NotNull JPanel createComboBoxPanel(
            final @NotNull AttributeName name
    ){
        JLabel label = createLabel(name.getNameForUser());
        ComboBox<S> comboBox = createComboBox(name);

        return createPanel(label, comboBox);
    }

    public <S extends TableID> @NotNull JPanel createComboBoxWithFlagPanel(
            final @NotNull AttributeName name
    ){
        JLabel label = createLabel(name.getNameForUser());
        ComboBox<S> comboBox = createComboBox(name);
        CheckBox checkBox = createCheckBox(name, comboBox);

        return createPanel(label, comboBox, checkBox);
    }

    private <S extends TableID> @NotNull ComboBox<S> createComboBox(
            final @NotNull AttributeName name
    ){
        ComboBox<S> comboBox = new ComboBox<>(name);
        components.put(name, comboBox);

        return comboBox;
    }

    private @NotNull CheckBox createCheckBox(final @NotNull AttributeName name, Component... parents){
        AttributeName nameCheckBox = new AttributeNameSimple(0, "Використовувати", name.getNameForSystem() + "Flag");
        CheckBox checkBox = new CheckBox(nameCheckBox, parents);
        components.put(nameCheckBox, checkBox);

        return checkBox;
    }

    private @NotNull JPanel createPanel(Component... componentsPanel){
        JPanel panel = new JPanel();
        setPanelBorder(panel);

        addComponentsToPanel(panel, componentsPanel);

        Dimension size = new Dimension(0, HEIGHT_COMPONENT);
        panel.setMaximumSize(size);
        panel.setPreferredSize(size);
        panel.setMinimumSize(size);

        return panel;
    }

    private @NotNull JLabel createLabel(final String title){
        JLabel label = new JLabel(title + ": ");
        label.setToolTipText(title);

        label.setMaximumSize(SIZE_LABEL);
        label.setPreferredSize(SIZE_LABEL);
        label.setMinimumSize(SIZE_LABEL);

        return label;
    }

    private void setPanelBorder(@NotNull JPanel panel){
        panel.setBorder(new EmptyBorder(PANEL_BORDER));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    }

    private void addComponentsToPanel(@NotNull JPanel panel, Component @NotNull ... components){
        for (int i = 0; i < components.length; ++i){
            panel.add(components[i]);

            if(i == components.length - 1){
                break;
            }

            panel.add(Box.createRigidArea(new Dimension(DIST_BETWEEN_ELEMENTS_X_ASIA)));
        }
    }

    public @NotNull JPanel createScrollPanel(JPanel panel){
        JPanel panelView = new JPanel();
        panelView.setLayout(new BorderLayout());
        panelView.add(new JScrollPane(panel), BorderLayout.CENTER);

        return panelView;
    }
}
