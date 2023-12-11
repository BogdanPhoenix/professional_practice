package org.university.ui.components.input_panel;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.action_with_database.Select;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.entities.TableID;
import org.university.exception.SelectedException;
import org.university.ui.additional_tools.MessageWindow;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ComboBox<T extends TableID> extends JComboBox<T> {
    private ComboBox<?> children;
    private transient AttributeName nameColumnParent;
    private final transient Select<T> select;
    private final DefaultComboBoxModel<T> model;
    private final transient ItemListener listener = this::updateChildren;

    public ComboBox(@NotNull AttributeName name){
        AttributeNameComboBox nameComboBox = (AttributeNameComboBox) name;
        this.select = () -> (Class<T>) nameComboBox.getNameClass();

        model = new DefaultComboBoxModel<>();
        this.setModel(model);
        this.addItemListener(listener);
        this.setName(name.getNameForSystem());
        init();
    }

    public void setChildComboBox(ComboBox<?> children, AttributeName nameColumnParent){
        this.children = children;
        this.nameColumnParent = nameColumnParent;
        activeUpdate();
    }

    private void activeUpdate(){
        listener.itemStateChanged(new ItemEvent(this, ItemEvent.SELECTED, this.getSelectedItem(), ItemEvent.SELECTED));
    }

    private void updateChildren(@NotNull ItemEvent e) {
        if(children == null || e.getStateChange() != ItemEvent.SELECTED){
            return;
        }

        TableID item = (TableID) e.getItem();
        if(item == null){
            return;
        }

        children.removeAllItems();
        children.updateItem(item, nameColumnParent);
    }

    private void updateItem(@NotNull TableID entity, @NotNull AttributeName nameColumn){
        SearchCriteria searchCriteria = new SearchCriteria(nameColumn.getNameForSystem(), entity, SearchOperation.EQUAL);
        init(searchCriteria);
    }

    private void init(SearchCriteria... searchCriteria){
        var values = selectAll(searchCriteria);

        model.removeAllElements();

        if(values.isEmpty()){
            return;
        }

        for (var value : values){
            model.addElement(value);
        }

        model.setSelectedItem(values.get(0));
    }

    private List<T> selectAll(SearchCriteria... searchCriteria) {
        try{
            return select
                    .selectAll(searchCriteria)
                    .stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();
        } catch (SelectedException e){
            MessageWindow.errorMessageWindow("Помилка вибору даних", e.getMessage());
            return new ArrayList<>();
        }
    }
}
