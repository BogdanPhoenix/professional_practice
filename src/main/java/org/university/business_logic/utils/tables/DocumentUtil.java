package org.university.business_logic.utils.tables;

import org.jetbrains.annotations.NotNull;
import org.university.business_logic.utils.ObjectName;
import org.university.business_logic.utils.reference_book.FileExtensionUtil;
import org.university.entities.reference_book.FileExtension;
import org.university.entities.tables.Document;
import org.university.entities.tables.Employee;
import org.university.entities.tables.Project;
import org.university.business_logic.search_tools.SearchOperation;
import org.university.business_logic.abstracts.TableModelView;
import org.university.business_logic.search_tools.SearchCriteria;
import org.university.exception.CastingException;
import org.university.exception.SelectedException;

import javax.swing.*;
import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class DocumentUtil extends TableModelView<Document> {
    private static final ObjectName PROJECT = new ObjectName("Проект", "project");
    private static final ObjectName PERFORMER = new ObjectName("Виконавець", "performer");
    private static final ObjectName FILE_EXTENSION = new ObjectName("Розширення файлу", "fileExtension");
    private static final ObjectName NAME_FILE = new ObjectName("Назва файлу", "nameFile");
    private static final ObjectName DATE_DOWN = new ObjectName("Дата завантаження", "bitFile");
    private static final ObjectName DESCRIPTION = new ObjectName("Опис", "description");
    private static final ObjectName BIT_FILE = new ObjectName("Файл", "bitFile");

    public DocumentUtil(){
        titleColumns = List.of(
                PROJECT.nameForUser(),
                PERFORMER.nameForUser(),
                FILE_EXTENSION.nameForUser(),
                NAME_FILE.nameForUser(),
                BIT_FILE.nameForUser(),
                DATE_DOWN.nameForUser(),
                DESCRIPTION.nameForUser()
        );
        nameTable = "Документація";
    }

    @Override
    public Class<Document> resolveEntityClass() {
        return Document.class;
    }

    @Override
    protected Object @NotNull [] createAttribute(@NotNull Document value){
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
        Project project = (Project) table.getValueAt(indexRow, 0);
        String nameFile = (String) table.getValueAt(indexRow, 3);
        byte[] bitFile = (byte[]) table.getValueAt(indexRow, 4);

        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), project, SearchOperation.EQUAL),
                new SearchCriteria(NAME_FILE.nameForSystem(), nameFile, SearchOperation.EQUAL),
                new SearchCriteria(BIT_FILE.nameForSystem(), bitFile, SearchOperation.EQUAL)
        };
    }

    @Override
    public JPanel dataEntryPanel() {
        if(panelBody != null) {
            panelBody.removeAll();
        }

        panelBody = new JPanel();
        panelBody.setLayout(new GridLayout(6, 1));

        panelBody.add(windowComponent.createComboBoxPanel(PROJECT, new ProjectUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(PERFORMER, new EmployeeUtil()));
        panelBody.add(windowComponent.createComboBoxPanel(FILE_EXTENSION, new FileExtensionUtil()));
        panelBody.add(windowComponent.createTextFieldInputPanel(NAME_FILE));
        panelBody.add(windowComponent.createTextFieldInputPanel(BIT_FILE));
        panelBody.add(windowComponent.createTextFieldInputPanel(DESCRIPTION));

        panelBody = windowComponent.createScrollPanel(panelBody);

        return panelBody;
    }

    @Override
    protected void checkCompletenessFields() throws SelectedException {
        String nameFile = valueFromTextField(NAME_FILE);
        String bitFile = valueFromTextField(BIT_FILE);

        if(nameFile.isEmpty() || bitFile.isEmpty()){
            throw new SelectedException(String.format(
                    "Одне з наступних обов'язкових полів не заповнене: %n%s %n%s",
                    NAME_FILE.nameForUser(), BIT_FILE.nameForUser()
            ));
        }
    }

    @Override
    protected SearchCriteria[] criteriaToSearchDuplicate(@NotNull Document entity) {
        return new SearchCriteria[] {
                new SearchCriteria(PROJECT.nameForSystem(), entity.getProject(), SearchOperation.EQUAL),
                new SearchCriteria(NAME_FILE.nameForSystem(), entity.getNameFile(), SearchOperation.EQUAL),
                new SearchCriteria(BIT_FILE.nameForSystem(), entity.getBitFile(), SearchOperation.EQUAL)
        };
    }

    @Override
    protected Document newEntity() {
        try {
            Project project = (Project) valueFromComboBox(PROJECT);
            FileExtension fileExtension = (FileExtension) valueFromComboBox(FILE_EXTENSION);
            Employee performer = (Employee) valueFromComboBox(PERFORMER);
            String nameFile = valueFromTextField(NAME_FILE);
            String description = valueFromTextField(DESCRIPTION);
            String bitFile = valueFromTextField(BIT_FILE);
            LocalDateTime dateDown = LocalDateTime.now();

            return Document.builder()
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

        windowComponent.updateComboBox(PROJECT, entity.getProject());
        windowComponent.updateComboBox(PERFORMER, entity.getPerformer());
        windowComponent.updateComboBox(FILE_EXTENSION, entity.getFileExtension());
        windowComponent.updateTextField(NAME_FILE, entity.getNameFile());
        windowComponent.updateTextField(BIT_FILE, new String(entity.getBitFile(), StandardCharsets.UTF_8));
        windowComponent.updateTextField(DESCRIPTION, entity.getDescription());
    }
}
