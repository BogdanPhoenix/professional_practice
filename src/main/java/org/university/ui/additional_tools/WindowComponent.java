package org.university.ui.additional_tools;

import org.jetbrains.annotations.NotNull;
import org.university.ui.components.ComboBox;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.action_with_database.Select;
import org.university.entities.TableID;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.*;

public class WindowComponent {
    private static final int TEXT_FIELD_SIZE_BORDER = 5;
    private static final int HEIGHT_COMPONENT = 50;
    private static final Dimension SIZE_LABEL = new Dimension(110, 20);
    protected static final Insets PANEL_BORDER = new Insets(7, 20, 7, 20);
    private static final Dimension DIST_BETWEEN_ELEMENTS_X_ASIA = new Dimension(10, 0);
    private static final EmptyBorder TEXT_FIELD_BORDER = new EmptyBorder(TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER,
            TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER);

    private final Map<ObjectName, Component> components;

    public WindowComponent(){
        components = new HashMap<>();
    }

    public Component getComponent(final ObjectName name){
        return components.get(name);
    }

    public void updateTextField(final @NotNull ObjectName name, String value){
        JTextField textField = (JTextField) components.get(name);
        textField.setText(value);
    }

    public void updatePasswordField(final @NotNull ObjectName name, String value){
        JPasswordField passwordField = (JPasswordField) components.get(name);
        passwordField.setText(value);
    }

    public <S extends TableID> void updateComboBox(final @NotNull ObjectName name, S value){
        ComboBox<S> comboBox = (ComboBox<S>) components.get(name);
        comboBox.setSelectedItem(value);
    }

    public @NotNull JPanel createTextFieldInputPanel(final @NotNull ObjectName name){
        JLabel label = createLabel(name.nameForUser());
        JTextField textField = createTextFieldInput(name);

        return createPanel(label, textField);
    }

    private @NotNull JTextField createTextFieldInput(final ObjectName name){
        JTextField textField = new JTextField();
        settingTextField(textField, name);

        return textField;
    }

    public @NotNull JPanel createPasswordFieldInputPanel(final @NotNull ObjectName name){
        JLabel label = createLabel(name.nameForUser());
        JPasswordField passwordField = createPasswordFieldInput(name);
        passwordField.setEchoChar('*');

        return createPanel(label, passwordField);
    }

    private @NotNull JPasswordField createPasswordFieldInput(final ObjectName name){
        JPasswordField passwordField = new JPasswordField();
        settingTextField(passwordField, name);

        return passwordField;
    }

    private void settingTextField(@NotNull JTextField textField, @NotNull ObjectName name){
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setName(name.nameForSystem());

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border compoundBorder = new CompoundBorder(lineBorder, TEXT_FIELD_BORDER);
        textField.setBorder(compoundBorder);

        components.put(name, textField);
    }

    public <E extends TableID> @NotNull JPanel createComboBoxPanel(
            final @NotNull ObjectName name,
            final Select<E> table,
            final @NotNull ObjectName children
    ){
        JLabel label = createLabel(name.nameForUser());

        ComboBox<?> childrenComboBox = (ComboBox<?>) components.get(children);
        ComboBox<E> comboBox = createComboBox(name, table);
        comboBox.setChildComboBox(childrenComboBox, name);

        return createPanel(label, comboBox);
    }

    public <S extends TableID> @NotNull JPanel createComboBoxPanel(
            final @NotNull ObjectName name,
            final Select<S> table
    ){
        JLabel label = createLabel(name.nameForUser());
        ComboBox<S> comboBox = createComboBox(name, table);

        return createPanel(label, comboBox);
    }

    private <S extends TableID> @NotNull ComboBox<S> createComboBox(
            final @NotNull ObjectName name,
            final Select<S> table
    ){
        ComboBox<S> comboBox = new ComboBox<>(table);
        comboBox.setName(name.nameForSystem());
        components.put(name, comboBox);

        return comboBox;
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
