package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.attribute_name.AttributeName;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.attribute_name.AttributeNameComboBox;
import org.university.business_logic.attribute_name.AttributeNameSimple;
import org.university.entities.reference_book.FileExtension;
import org.university.entities.tables.Project;
import org.university.entities.tables.BinFileProject;
import org.university.entities.tables.Employee;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BinFileProjectUtil extends TableModelView<BinFileProject> {
    private static final AttributeName PROJECT = new AttributeNameComboBox(0, "Проект", "project", Project.class);
    private static final AttributeName PERFORMER = new AttributeNameComboBox(1, "Виконавець", "performer", Employee.class);
    private static final AttributeName FILE_EXTENSION = new AttributeNameComboBox(2,"Розширення файлу", "fileExtension", FileExtension.class);
    private static final AttributeName NAME_FILE = new AttributeNameSimple(3, "Назва файлу", "nameFile");
    private static final AttributeName BIT_FILE = new AttributeNameSimple(4, "Файл", "bitFile");
    private static final AttributeName DATE_DOWN = new AttributeNameSimple(5, "Дата завантаження", "dateTimeDown");
    private static final AttributeName DESCRIPTION = new AttributeNameSimple(6, "Опис", "description");

    public BinFileProjectUtil(){
        titleColumns = List.of(
                PROJECT.getNameForUser(),
                PERFORMER.getNameForUser(),
                FILE_EXTENSION.getNameForUser(),
                NAME_FILE.getNameForUser(),
                BIT_FILE.getNameForUser(),
                DATE_DOWN.getNameForUser(),
                DESCRIPTION.getNameForUser()
        );
        nameTable = "Бінарні файли";
    }

    @Override
    public Class<BinFileProject> resolveEntityClass() {
        return BinFileProject.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull BinFileProject value){
        return new Object[]{
                value.getProject(),
                value.getPerformer(),
                value.getFileExtension(),
                value.getNameFile(),
                value.getBitFile(),
                value.getDateTimeDown(),
                value.getDescription()
        };
    }

    @Override
    protected SearchCriteria[] criteriaToSearchEntities(@NotNull JTable table, int indexRow) {
        var project = table.getValueAt(indexRow, PROJECT.getId());
        var nameFile = table.getValueAt(indexRow, NAME_FILE.getId());
        var bitFile = table.getValueAt(indexRow, BIT_FILE.getId());

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(NAME_FILE.getNameForSystem(), nameFile, SearchOperation.EQUAL),
                new SearchCriteria(BIT_FILE.getNameForSystem(), bitFile, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        return createPanel(false);
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameFile = valueFromTextField(NAME_FILE);
        String bitFile = valueFromTextField(BIT_FILE);

        if(nameFile.isEmpty() || bitFile.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s",
                    NAME_FILE.getNameForUser(), BIT_FILE.getNameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull BinFileProject entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.getNameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(NAME_FILE.getNameForSystem(), entity.getNameFile(), SearchOperation.EQUAL),
                new SearchCriteria(BIT_FILE.getNameForSystem(), entity.getBitFile(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected BinFileProject newEntity() {
        try {
            Project project = (Project) valueFromComboBox(PROJECT);
            FileExtension fileExtension = (FileExtension) valueFromComboBox(FILE_EXTENSION);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            String nameFile = valueFromTextField(NAME_FILE);
            String description = valueFromTextField(DESCRIPTION);
            String bitFile = valueFromTextField(BIT_FILE);
            LocalDateTime dateDown = LocalDateTime.now();

            return BinFileProject.builder()
                    .project(project)
                    .fileExtension(fileExtension)
                    .performer(performer)
                    .nameFile(nameFile)
                    .description(description)
                    .bitFile(bitFile.getBytes())
                    .dateTimeDown(Timestamp.valueOf(dateDown))
                    .currentData(true)
                    .build();
        } catch (Exception e){
            throw new CastingException(e.getMessage());
        }
    }

    @Override
    protected void fillingFields() throws SelectedException {
        var entity = getSelectedEntity();

        panelComponent.updateComboBox(PROJECT, entity.getProject());
        panelComponent.updateComboBox(PERFORMER, entity.getPerformer());
        panelComponent.updateComboBox(FILE_EXTENSION, entity.getFileExtension());

        panelComponent.updateTextField(NAME_FILE, entity.getNameFile());
        panelComponent.updateTextField(BIT_FILE, new String(entity.getBitFile(), StandardCharsets.UTF_8));
        panelComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }

    @Override
    public JPanel selectEntryPanel() {
        return createPanel(true);
    }

    private JPanel createPanel(boolean flag){
        clearPanelBody();
        List<JPanel> components = new ArrayList<>();

        if(flag){
            components.addAll(createIntervalPanels(DATE_DOWN));
        }

        components.addAll(createComboBoxPanels(flag, PROJECT, PERFORMER, FILE_EXTENSION));
        components.addAll(createTextFieldPanels(flag, names(flag)));
        addAllComponentsToPanel(components);

        return panelBody;
    }

    @Contract(value = "_ -> new", pure = true)
    private AttributeName @NotNull [] names(boolean flag) {
        if(flag){
            return new AttributeName[] {NAME_FILE};
        }
        else {
            return new AttributeName[] {NAME_FILE, BIT_FILE, DESCRIPTION};
        }
    }

    @Override
    protected List<Optional<SearchCriteria>> createListCriteria() {
        List<Optional<SearchCriteria>> searchCriteria = new ArrayList<>();

        searchCriteria.addAll(criteriaFromInterval(DATE_DOWN));
        searchCriteria.addAll(criteriaFromComboBox(PROJECT, PERFORMER, FILE_EXTENSION));
        searchCriteria.addAll(criteriaTextField(NAME_FILE));

        return searchCriteria;
    }

    @Override
    public ActionListener createGraph() {
        return e -> {
            List<AttributeName> variants = List.of(PROJECT, PERFORMER, FILE_EXTENSION);
            createGraphUI(variants);
        };
    }
}
