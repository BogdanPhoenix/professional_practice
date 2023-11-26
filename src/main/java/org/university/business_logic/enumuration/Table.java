package org.university.business_logic.enumuration;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.university.ui.interfaces.panel_interaction.logic.TableModelView;
import org.university.ui.realization.panel_interaction.logic.*;
import org.university.ui.realization.panel_interaction.logic.view.*;

import java.util.function.Supplier;

@Getter
public enum Table {
    ACCESS_RIGHT(AccessRightUtil::new),
    AUTHENTICATION(AuthenticationUtil::new),
    BIN_FILE_PROJECT(BinFileProjectUtil::new),
    FULL_CHECK_LIST_INFO(FullCheckListInfoUtil::new),
    COMMAND_PROJECT(CommandProjectUtil::new),
    COMMENT_TASK(CommentTaskUtil::new),
    DOCUMENT(DocumentUtil::new),
    EMPLOYEE(EmployeeUtil::new),
    EMPLOYEE_ACCESS_RIGHT(EmployeeAccessRightUtil::new),
    EXECUTION_STATUS(ExecutionStatusUtil::new),
    FILE_EXTENSION(FileExtensionUtil::new),
    HISTORY_CHANGE(HistoryChangeUtil::new),
    POSITION(PositionUtil::new),
    PRIORITY_TASK(PriorityTaskUtil::new),
    FULL_PROJECT_INFO(FullProjectInfoUtil::new),
    FULL_SPRINT_INFO(FullSprintInfoUtil::new),
    STATE_TESTING(StateTestingUtil::new),
    FULL_TASK_INFO(FullTaskInfoUtil::new),
    FULL_TASK_TESTING_INFO(FullTaskTestingInfoUtil::new),
    TYPE_CHANGE(TypeChangeUtil::new),
    TYPE_COMPLEXITY(TypeComplexityUtil::new);

    private final TableModelView<?> tableUtil;

    Table(@NotNull Supplier<TableModelView<?>> tableUtil) {
        this.tableUtil = tableUtil.get();
    }

    @Override
    public String toString() {
        return tableUtil.getNameTable();
    }
}
