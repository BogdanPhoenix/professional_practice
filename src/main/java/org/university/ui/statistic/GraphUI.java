package org.university.ui.statistic;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.statistics.Statistics;
import org.university.entities.TableID;
import org.university.ui.additional_tools.FrameComponent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.List;

public class GraphUI extends JFrame {
    private static final String NAME_VALUE_ROW = "Значення";
    private static final String NAME_FREQUENCY_ROW = "Частота";
    private static GraphUI instance;
    private JTable tableData;
    private final JTable tableParameter;
    private final JComboBox<AttributeName> comboBox;
    private final DefaultTableModel model;
    private JPanel graphPanel;
    private final JPanel mainPanel;

    private GraphUI() {
        super("Граф");

        this.comboBox = new JComboBox<>();
        this.comboBox.addItemListener(this::selectVariant);
        this.model = new DefaultTableModel();

        this.model.addColumn(NAME_VALUE_ROW);
        this.model.addColumn(NAME_FREQUENCY_ROW);
        this.tableParameter = new JTable(model);

        this.mainPanel = createContextPanel();
        this.setContentPane(mainPanel);

        this.setSize(500, 500);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    public static GraphUI getInstance(List<AttributeName> variants, JTable tableData){
        if(instance == null){
            instance = new GraphUI();
        }

        instance.repaintUI(variants, tableData);
        return instance;
    }

    private @NotNull JPanel createContextPanel(){
        JPanel panelView = FrameComponent.createPanelView(tableParameter);
        JPanel panelSelectTable = FrameComponent.createPanelSelect(comboBox);
        graphPanel = createPanelGraph();

        return FrameComponent.createContextPanel(panelSelectTable, panelView, graphPanel);
    }

    private @NotNull JPanel createPanelGraph(){
        DefaultPieDataset<String> dataset = createDataset();
        JFreeChart pieChart = createPieChart(dataset);

        return new ChartPanel(pieChart);
    }

    private @NotNull DefaultPieDataset<String> createDataset() {
        DefaultPieDataset<String> data = new DefaultPieDataset<>();

        for (int row = 0; row < model.getRowCount(); row++) {
            var category = (TableID) model.getValueAt(row, 0);
            var value = (long) model.getValueAt(row, 1);
            data.setValue(category.toString(), value);
        }

        return data;
    }

    @Contract("_ -> new")
    private @NotNull JFreeChart createPieChart(DefaultPieDataset<String> dataset) {
        return ChartFactory.createPieChart(
                "Візуальна інформація",
                dataset,
                true,
                true,
                false
        );
    }

    private void repaintUI(@NotNull List<AttributeName> variants, JTable tableData){
        this.tableData = tableData;
        comboBox.removeAllItems();
        variants.forEach(comboBox::addItem);
    }

    private void selectVariant(@NotNull ItemEvent itemEvent) {
        if(itemEvent.getStateChange() != ItemEvent.SELECTED){
            return;
        }

        AttributeName name = (AttributeName) itemEvent.getItem();
        List<Object> values = selectData(name);
        currentParameter(values);
        updatePieChart();
    }

    public void updatePieChart() {
        mainPanel.remove(graphPanel);
        graphPanel = createPanelGraph();
        mainPanel.add(graphPanel);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private @NotNull List<Object> selectData(AttributeName variant){
        List<Object> items = new ArrayList<>();

        DefaultTableModel modelData = (DefaultTableModel) tableData.getModel();

        for(int i = 0; i < modelData.getRowCount(); ++i){
            Object cell = modelData.getValueAt(i, Objects.requireNonNull(variant).getId());
            items.add(cell);
        }

        return items;
    }

    private void currentParameter(@NotNull List<Object> items){
        model.setRowCount(0);

        if(items.isEmpty()){
            return;
        }

        Map<Object, Long> frequency = Statistics.createTableFrequencyData(items);
        frequency.forEach((key, value) -> model.addRow(new Object[]{key, value}));
    }
}
