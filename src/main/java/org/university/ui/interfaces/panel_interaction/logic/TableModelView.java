package org.university.ui.interfaces.panel_interaction.logic;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
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

public abstract class TableModelView<T> implements Select<T>, SelectModelView, InsertAndUpdateModelView {
    private static final int HEIGHT_COMPONENT = 50;
    private static final Insets PANEL_BORDER = new Insets(7, 20, 7, 20);
    private static final Dimension DIST_BETWEEN_ELEMENTS_X_ASIA = new Dimension(10, 0);
    private static final int TEXT_FIELD_SIZE_BORDER = 5;

    @Getter
    protected String nameTable;
    protected List<String> titleColumns;
    protected Map<String, Component> components = new HashMap<>();

    @Override
    public void createModel(DefaultTableModel tableModel) {
        updateColumnTitle(tableModel);
    }

    void updateColumnTitle(DefaultTableModel tableModel){
        for (var title : titleColumns){
            tableModel.addColumn(title);
        }
    }

    protected void setPanelBorder(@NotNull JPanel panel){
        panel.setBorder(new EmptyBorder(PANEL_BORDER));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
    }

    protected void addComponentToControlPanel(@NotNull JPanel panel, @NotNull List<Component> components){
        for (int i = 0; i < components.size(); ++i){
            panel.add(components.get(i));
            if(i == components.size() - 1){
                break;
            }
            panel.add(Box.createRigidArea(new Dimension(DIST_BETWEEN_ELEMENTS_X_ASIA)));
        }
    }

    protected @NotNull JPanel createTextFieldInputPanel(final String name){
        JLabel label = new JLabel("Введіть дані: ");
        JTextField textField = createTextFieldInput(name);

        components.put(name, textField);

        List<Component> componentsPanel = new ArrayList<>();
        componentsPanel.add(label);
        componentsPanel.add(textField);

        JPanel panel = new JPanel();
        setPanelBorder(panel);

        addComponentToControlPanel(panel, componentsPanel);

        Dimension size = new Dimension(0, HEIGHT_COMPONENT);
        panel.setMaximumSize(size);
        panel.setPreferredSize(size);

        return panel;
    }

    protected @NotNull JTextField createTextFieldInput(final String name){
        JTextField textField = new JTextField();
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setName(name);

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border compoundBorder = new CompoundBorder(lineBorder, new EmptyBorder(TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER,
                TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER));
        textField.setBorder(compoundBorder);

        return textField;
    }

    protected boolean isDuplicate(@NotNull Select<T> select, SearchCriteria criteriaList) {
        return select.selectOne(criteriaList).isPresent();
    }

    protected String valueFromTextField(final String nameField){
        return ((JTextField)components.get(nameField)).getText();
    }

    protected void addRows(DefaultTableModel tableModel, @NotNull List<Optional<T>> list){
        for(var info : list){
            info.ifPresent(
                    value -> tableModel.addRow(createAttribute(value))
            );
        }
    }

    protected abstract Object[] createAttribute(T value);
}
