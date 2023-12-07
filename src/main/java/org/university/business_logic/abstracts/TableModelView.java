package org.university.business_logic.abstracts;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.university.exception.*;
import org.university.ui.additional_tools.MessageWindow;
import org.university.business_logic.utils.ObjectName;
import org.university.ui.additional_tools.WindowComponent;
import org.university.entities.TableID;
import org.university.ui.mediator.interfaces.Mediator;
import org.university.business_logic.search_tools.SearchCriteria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.*;
import java.util.List;

public abstract class TableModelView<T extends TableID>
        implements SelectModelView<T>, InsertModelView<T>, DeleteModelView<T>, UpdateModelView<T>
{
    protected static Mediator mediator;

    @Getter
    protected String nameTable;
    protected List<String> titleColumns;
    protected JPanel panelBody;
    protected WindowComponent windowComponent;

    protected TableModelView(){
        windowComponent = new WindowComponent();
    }

    public static void setMediator(Mediator mediator) {
        TableModelView.mediator = mediator;
    }

    @Override
    public void createViewModel(@NotNull DefaultTableModel tableModel) {
        try{
            createColumns(tableModel);

            var values = selectAll();
            addRows(tableModel, values);
        } catch (SelectedException e) {
            MessageWindow.errorMessageWindow("Помилка під час створення рядків таблиці", e.getMessage());
        }
    }

    private void createColumns(@NotNull DefaultTableModel tableModel){
        for (var title : titleColumns){
            tableModel.addColumn(title);
        }
    }

    protected void addRows(DefaultTableModel tableModel, @NotNull List<Optional<T>> list){
        for(var info : list){
            info.ifPresent(
                    value -> tableModel.addRow(createAttribute(value))
            );
        }
    }

    protected String valueFromTextField(final @NotNull ObjectName nameComponent){
        var component = windowComponent.getComponent(nameComponent);
        return ((JTextField)component).getText();
    }

    protected String valueFromPasswordField(final @NotNull ObjectName nameComponent){
        var component = windowComponent.getComponent(nameComponent);
        var charsPassword = ((JPasswordField)component).getPassword();
        return new String(charsPassword);
    }

    protected Object valueFromComboBox(final @NotNull ObjectName nameComponent){
        var component = windowComponent.getComponent(nameComponent);
        return ((JComboBox<?>)component).getSelectedItem();
    }

    @Override
    public ActionListener commandSave() {
        return e -> {
            try{
                checkCompletenessFields();

                var entity = newEntity();

                isDuplicate(criteriaToSearchDuplicate(entity));
                save(entity);
            } catch (SelectedException ex){
                MessageWindow.errorMessageWindow("Помилка заповненості полів", ex.getMessage());
            } catch (DuplicateException ex){
                MessageWindow.errorMessageWindow("Дублювання даних", ex.getMessage());
            } catch (InsertException ex){
                MessageWindow.errorMessageWindow("Помилка під час додання даних", ex.getMessage());
            } catch (CastingException ex){
                MessageWindow.errorMessageWindow("Помилка перетворення типів.", ex.getMessage());
            }
        };
    }

    protected void isDuplicate(SearchCriteria... criteria) throws DuplicateException, SelectedException {
        var selectList = selectAll(criteria);

        if(!selectList.isEmpty()){
            throw new DuplicateException("Введені вами дані вже є присутні в таблиці. Спробуйте ввести інші дані.");
        }
    }

    @Override
    public ActionListener commandDelete() {
        return e -> {
            var entities = getEntities().stream()
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .toList();

            if(entities.isEmpty()){
                MessageWindow.errorMessageWindow("Помилка видалення", "Ви не обрали жодної сутності. Немає чого видаляти");
                return;
            }

            if(!MessageWindow.deleteMessageWindow()){
                return;
            }

            deleteAllEntities(entities);
        };
    }

    private void deleteAllEntities(List<T> entities) {
        try {
            delete(entities);
        } catch (RuntimeException e){
            MessageWindow.errorMessageWindow(
                    "Помилка під час видалення.",
                    e.getMessage()
            );
        }
    }

    @NotNull
    protected List<Optional<T>> getEntities(){
        JTable table = mediator.getTable();
        var selectedRows = table.getSelectedRows();

        if(selectedRows.length == 0){
            return new ArrayList<>();
        }

        return selectionAllNecessaryEntities(table, selectedRows);
    }

    private @NotNull List<Optional<T>> selectionAllNecessaryEntities(JTable table, int @NotNull [] selectedRows){
        try{
            List<Optional<T>> selected = new ArrayList<>();

            for(var index : selectedRows){
                SearchCriteria[] searchCriteria = criteriaToSearchEntities(table, index);
                selected.addAll(selectAll(searchCriteria));
            }

            return selected;
        } catch (SelectedException e){
            MessageWindow.errorMessageWindow("Помилка вибору сутностей", e.getMessage());
            return new ArrayList<>();
        }
    }

    protected T getSelectedEntity() throws SelectedException {
        return getEntities().stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() ->
                        new SelectedException("Не вдалося отримати виділений об'єкт.")
                );
    }

    @Override
    public ActionListener commandUpdate() {
        return e -> {
            try{
                checkSelectedRows();
                checkCompletenessFields();

                if(!MessageWindow.updateMessageWindow()){
                    return;
                }

                var prev = getSelectedEntity();
                var current = newEntity();

                current.setId(prev.getId());
                update(current);
            } catch (UpdateException ex){
                MessageWindow.errorMessageWindow("Помилка при оновленні.", ex.getMessage());
            } catch (SelectedException ex){
                MessageWindow.errorMessageWindow("Помилка під час оновлення", ex.getMessage());
            } catch (CastingException ex){
                MessageWindow.errorMessageWindow("Помилка перетворення типів.", ex.getMessage());
            }
        };
    }

    protected void checkSelectedRows(){
        JTable table = mediator.getTable();
        if(table.getSelectedRows().length > 1){
            throw new SelectedException("Не можливо оновити декілька виділених сутностей.");
        }
    }

    @Override
    public ActionListener selectEntity() {
        return e -> {
            try {
                checkSelectedRows();
                fillingFields();
            } catch (SelectedException ex){
                MessageWindow.errorMessageWindow(
                        "Помилка вибору сутності для редагування",
                        ex.getMessage()
                );
            }
        };
    }

    protected void checkCompletenessFields(final ObjectName name) throws SelectedException {
        String nameExtension = valueFromTextField(name);

        if(nameExtension.isEmpty()){
            throw new SelectedException(String.format(
                    "Поле %s має бути обов'язково заповненим.",
                    name.nameForUser()
            ));
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

    protected abstract Object[] createAttribute(T value);
    protected abstract SearchCriteria[] criteriaToSearchEntities(JTable table, int indexRow);
    protected abstract SearchCriteria[] criteriaToSearchDuplicate(T entity);
    protected abstract T newEntity() throws CastingException;
    protected abstract void checkCompletenessFields() throws SelectedException;
    protected abstract void fillingFields() throws SelectedException;
}
