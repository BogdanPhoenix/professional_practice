package org.university.business_logic.abstracts;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.university.business_logic.Interval;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.TableID;
import org.university.exception.SelectedException;
import org.university.ui.additional_tools.MessageWindow;
import org.university.ui.components.input_panel.CheckBox;
import org.university.ui.statistic.GraphUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public abstract class TableModelView<T extends TableID> extends ReferenceBookModelView<T> implements SelectWithParametersModelView{
    protected void clearPanelBody(){
        if(panelBody != null) {
            panelBody.removeAll();
        }
    }
    protected void addAllComponentsToPanel(@NotNull List<JPanel> panels){
        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(panels.size(), 1));

        for(var panel : panels){
            panelBody.add(panel);
        }

        panelBody = panelComponent.createScrollPanel(panelBody);
    }

    protected List<JPanel> createTextFieldPanels(boolean isFlag, AttributeName @NotNull ... namesPanel){
        List<JPanel> panels = new ArrayList<>();

        for(var name : namesPanel){
            if(isFlag){
                panels.add(panelComponent.createTextFieldInputWithFlagPanel(name));
            }
            else {
                panels.add(panelComponent.createTextFieldInputPanel(name));
            }
        }

        return panels;
    }

    protected List<JPanel> createComboBoxPanels(boolean isFlag, AttributeName @NotNull ... namesPanel){
        List<JPanel> panels = new ArrayList<>();

        for(var name : namesPanel){
            if(isFlag){
                panels.add(panelComponent.createComboBoxWithFlagPanel(name));
            }
            else {
                panels.add(panelComponent.createComboBoxPanel(name));
            }
        }

        return panels;
    }

    protected List<JPanel> createCoherentComboBoxPanels(AttributeName @NotNull ... namesPanel){
        List<JPanel> panels = new ArrayList<>();

        int lastIndex = namesPanel.length - 1;
        for(int i = lastIndex; i >= 0; --i) {
            if(i == lastIndex){
                panels.add(panelComponent.createComboBoxPanel(namesPanel[i]));
            }
            else {
                panels.add(panelComponent.createComboBoxPanel(namesPanel[i], namesPanel[i + 1]));
            }
        }

        Collections.reverse(panels);
        return panels;
    }

    protected List<JPanel> createIntervalPanels(AttributeName @NotNull ... namesPanel){
        List<JPanel> panels = new ArrayList<>();

        for (var name : namesPanel){
            panels.add(panelComponent.createIntervalInputPanel(name));
        }

        return panels;
    }

    @Override
    public ActionListener commandSelect() {
        return e -> {
            try{
                JTable table = mediator.getTable();
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                SearchCriteria[] searchCriteria = createSearchCriteria();
                var values = selectAll(searchCriteria);

                model.setRowCount(0);
                addRows(model, values);
            } catch (RuntimeException ex) {
                MessageWindow.errorMessageWindow("Помилка під час створення рядків таблиці", ex.getMessage());
            }
        };
    }

    protected SearchCriteria @NotNull [] createSearchCriteria(){
        List<Optional<SearchCriteria>> searchCriteria = createListCriteria();

        return searchCriteria.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toArray(SearchCriteria[]::new);
    }

    protected List<Optional<SearchCriteria>> criteriaFromInterval(AttributeName @NotNull ... nameComponentSearch) {
        List<Optional<SearchCriteria>> criteria = new ArrayList<>();

        for(var name : nameComponentSearch){
            criteria.add(createCriteriaFromInterval(name));
        }

        return criteria;
    }

    private Optional<SearchCriteria> createCriteriaFromInterval(AttributeName nameComponentSearch) {
        try{
            Optional<String> from = valueFromIntervalFrom(nameComponentSearch);
            Optional<String> to = valueFromIntervalTo(nameComponentSearch);

            if(from.isEmpty()){
                return Optional.empty();
            }
            else if(to.isEmpty()){
                LocalDateTime localDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                String formattedDateTime = localDateTime.format(formatter);

                to = Optional.of(formattedDateTime);
            }

            return Optional.of(new SearchCriteria(nameComponentSearch.getNameForSystem(), new Interval(from.get(), to.get()), SearchOperation.BETWEEN));
        } catch (Exception e){
            MessageWindow.errorMessageWindow("Помилка створення критерію", String.format("Під час створення критерію за атрибутом %s для вибору даних з таблиці сталася наступна помилка: %s. Даний критерій не буде враховуватися під час вибору даних.", nameComponentSearch.getNameForUser(), e.getMessage()));
            return Optional.empty();
        }
    }

    protected List<Optional<SearchCriteria>> criteriaFromComboBox(AttributeName... names) throws SelectedException {
        try {
            List<Optional<SearchCriteria>> searchCriteriaList = new ArrayList<>();

            for(var name : names) {
                Optional<Object> value = valueFromComboBoxWithFlag(name);
                var criteria = value.map(o -> new SearchCriteria(name.getNameForSystem(), o, SearchOperation.EQUAL));
                searchCriteriaList.add(criteria);
            }

            return searchCriteriaList;
        } catch (RuntimeException e) {
            throw new SelectedException(e.getMessage());
        }
    }

    protected List<Optional<SearchCriteria>> criteriaTextField(AttributeName... names) throws SelectedException {
        try {
            List<Optional<SearchCriteria>> searchCriteriaList = new ArrayList<>();

            for(var name : names) {
                Optional<String> value = valueFromTextFieldWithFlag(name);
                var criteria = value.map(o -> new SearchCriteria(name.getNameForSystem(), o, SearchOperation.LIKE));
                searchCriteriaList.add(criteria);
            }

            return searchCriteriaList;
        } catch (RuntimeException e) {
            throw new SelectedException(e.getMessage());
        }
    }

    protected Optional<String> valueFromIntervalFrom(final @NotNull AttributeName nameComponent) throws SelectedException, IllegalStateException{
        return valueFromInterval(nameComponent, "From");
    }

    protected Optional<String> valueFromIntervalTo(final @NotNull AttributeName nameComponent) throws SelectedException, IllegalStateException{
        return valueFromInterval(nameComponent, "To");
    }

    private Optional<String> valueFromInterval(AttributeName nameComponent, String partOf){
        if(isIgnoreComponent(nameComponent)){
            return Optional.empty();
        }
        AttributeName nameTo = new AttributeNameSimple(0, nameComponent.getNameForUser(), nameComponent.getNameForSystem() + partOf);
        String value = valueFromTextField(nameTo);

        if(value.isEmpty()){
            return Optional.empty();
        }
        else {
            return Optional.of(value);
        }
    }

    protected Optional<String> valueFromTextFieldWithFlag(final @NotNull AttributeName nameComponent) throws SelectedException, IllegalStateException {
        if(isIgnoreComponent(nameComponent)){
            return Optional.empty();
        }
        String value = valueFromTextField(nameComponent);
        return Optional.of(value);
    }

    protected Optional<Object> valueFromComboBoxWithFlag(final @NotNull AttributeName nameComponent) throws SelectedException {
        if(isIgnoreComponent(nameComponent)){
            return Optional.empty();
        }
        var value = valueFromComboBox(nameComponent);
        return Optional.of(value);
    }

    private boolean isIgnoreComponent(final @NotNull AttributeName nameComponent) {
        AttributeName nameCheck = new AttributeNameSimple(0, "", nameComponent.getNameForSystem() + "Flag");
        CheckBox checkBox = (CheckBox) panelComponent.getComponent(nameCheck);

        return !checkBox.isSelected();
    }

    protected Object valueFromComboBox(final @NotNull AttributeName nameComponent){
        try{
            var component = panelComponent.getComponent(nameComponent);
            return ((JComboBox<?>)component).getSelectedItem();
        }  catch (Exception e) {
            throw new SelectedException(String.format("Не вдалося отримати значення з полю %s. Спробуйте іще раз.", nameComponent.getNameForUser()));
        }
    }

    protected static @Nullable Timestamp convertFromStringToTimestamp(@NotNull String text){
        return text.isEmpty()
                ? null
                : Timestamp.valueOf(text);
    }

    protected static String convertFromTimestampToString(Timestamp timestamp){
        return timestamp == null
                ? ""
                : timestamp.toString();
    }

    protected void createGraphUI(List<AttributeName> variants){
        GraphUI graphUI = GraphUI.getInstance(variants, mediator.getTable());
        graphUI.setVisible(true);
    }

    protected abstract List<Optional<SearchCriteria>> createListCriteria();
}
