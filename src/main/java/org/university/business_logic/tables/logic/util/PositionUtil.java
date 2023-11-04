package org.university.business_logic.tables.logic.util;

import org.university.business_logic.tables.logic.interfaces.TableModelView;
import org.university.business_logic.tables.logic.interfaces.action_with_database.Select;
import org.university.business_logic.tables.orm.Position;

import java.util.List;

public class PositionUtil extends TableModelView implements Select<Position> {
    public PositionUtil(){
        titleColumns = List.of("Назва");
        nameTable = "Посади";
    }

    @Override
    protected void addRows() {
        List<Position> executionStatuses = selectAll();
        for (var executionStatus : executionStatuses){
            model.addRow(new Object[]{executionStatus.getNamePosition()});
        }
    }

    @Override
    public Class<Position> resolveEntityClass() {
        return Position.class;
    }
}
