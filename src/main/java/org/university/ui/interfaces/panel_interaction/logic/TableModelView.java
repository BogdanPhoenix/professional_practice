package org.university.ui.interfaces.panel_interaction.logic;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.action_with_database.Insert;
import org.university.ui.interfaces.panel_interaction.logic.action_with_database.Select;
import org.university.ui.realization.panel_interaction.SearchCriteria;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class TableModelView<T> implements Select<T>, Insert<T>, SelectModelView, InsertModelView {
    private static final String ERROR_MESSAGE_DUPLICATE = "Введені вами дані вже є присутні в таблиці. Спробуйте ввести інші дані.";
    private static final int HEIGHT_COMPONENT = 50;
    protected static final Insets PANEL_BORDER = new Insets(7, 20, 7, 20);
    private static final Dimension DIST_BETWEEN_ELEMENTS_X_ASIA = new Dimension(10, 0);
    private static final int TEXT_FIELD_SIZE_BORDER = 5;
    private static final Dimension SIZE_LABEL = new Dimension(110, 20);

    @Getter
    protected String nameTable;
    protected List<String> titleColumns;
    protected Map<String, Component> components = new HashMap<>();

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        for (var title : titleColumns){
            tableModel.addColumn(title);
        }
    }

    protected @NotNull JPanel createTextFieldInputPanel(final String title, final String name){
        JLabel label = createLabel(title);
        JTextField textField = createTextFieldInput(name);

        List<Component> componentsPanel = new ArrayList<>();
        componentsPanel.add(label);
        componentsPanel.add(textField);

        return createPanel(componentsPanel);
    }

    private @NotNull JTextField createTextFieldInput(final String name){
        JTextField textField = new JTextField();
        settingTextField(textField, name);

        return textField;
    }

    protected @NotNull JPanel createPasswordFieldInputPanel(final String title, final String name){
        JLabel label = createLabel(title);
        JPasswordField passwordField = createPasswordFieldInput(name);
        passwordField.setEchoChar('*');

        List<Component> componentsPanel = new ArrayList<>();
        componentsPanel.add(label);
        componentsPanel.add(passwordField);

        return createPanel(componentsPanel);
    }

    private @NotNull JPasswordField createPasswordFieldInput(final String name){
        JPasswordField passwordField = new JPasswordField();
        settingTextField(passwordField, name);

        return passwordField;
    }

    private void settingTextField(@NotNull JTextField textField, String name){
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setName(name);

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border compoundBorder = new CompoundBorder(lineBorder, new EmptyBorder(TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER,
                TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER));
        textField.setBorder(compoundBorder);

        components.put(name, textField);
    }

    protected <S> @NotNull JPanel createComboBoxPanel(final String title, final String name, final Select<S> table){
        JLabel label = createLabel(title);
        JComboBox<S> comboBox = createComboBox(name, table);

        List<Component> componentsPanel = new ArrayList<>();
        componentsPanel.add(label);
        componentsPanel.add(comboBox);

        return createPanel(componentsPanel);
    }

    private <S> @NotNull JComboBox<S> createComboBox(String name, @NotNull Select<S> table){
        var values = table
                .selectAll()
                .stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        DefaultComboBoxModel<S> model = new DefaultComboBoxModel<>();

        for (var value : values){
            model.addElement(value);
        }

        JComboBox<S> comboBox = new JComboBox<>(model);
        comboBox.setName(name);
        components.put(name, comboBox);

        return comboBox;
    }

    private @NotNull JPanel createPanel(List<Component> componentsPanel){
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

    private void addComponentsToPanel(@NotNull JPanel panel, @NotNull List<Component> components){
        for (int i = 0; i < components.size(); ++i){
            panel.add(components.get(i));
            if(i == components.size() - 1){
                break;
            }
            panel.add(Box.createRigidArea(new Dimension(DIST_BETWEEN_ELEMENTS_X_ASIA)));
        }
    }

    protected String valueFromTextField(final String nameField){
        return ((JTextField)components.get(nameField)).getText();
    }

    protected String valueFromPasswordField(final String nameField){
        var charsPassword = ((JPasswordField)components.get(nameField)).getPassword();
        return new String(charsPassword);
    }

    protected Object valueFromComboBox(final String nameField){
        return ((JComboBox<?>)components.get(nameField)).getSelectedItem();
    }

    protected void addRows(DefaultTableModel tableModel, @NotNull List<Optional<T>> list){
        for(var info : list){
            info.ifPresent(
                    value -> tableModel.addRow(createAttribute(value))
            );
        }
    }

    protected Optional<T> saveToTable(T value, SearchCriteria... criteria){
        if(isDuplicate(criteria)){
            errorMessageWindow("Дублювання даних", ERROR_MESSAGE_DUPLICATE);
            return Optional.empty();
        }

        try {
            return save(value);
        }catch (IllegalArgumentException e){
            errorMessageWindow("Помилка під час додання даних", e.getMessage());
            return Optional.empty();
        }
    }

    protected boolean isDuplicate(SearchCriteria... criteria) {
        var selectList = selectAll(criteria);
        return !selectList.isEmpty();
    }

    protected void errorMessageWindow(String title, String message){
        JOptionPane.showMessageDialog(null,message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    protected JPanel createScrollPanel(JPanel panel){
        JPanel panelView = new JPanel();
        panelView.setLayout(new BorderLayout());
        panelView.add(new JScrollPane(panel), BorderLayout.CENTER);

        return panelView;
    }

    protected abstract Object[] createAttribute(T value);

    public void repaint() {

    }
}
