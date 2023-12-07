package org.university.business_logic.models.realization;

import lombok.Getter;
import org.university.business_logic.models.interfaces.Model;
import org.university.business_logic.utils.reference_book.*;

import java.util.ArrayList;

@Getter
public class ReferenceBookModel extends Model {
    private static final Model INSTANCE = new ReferenceBookModel();

    private ReferenceBookModel(){
        values = new ArrayList<>();

        values.add(new AccessRightUtil());
        values.add(new ExecutionStatusUtil());
        values.add(new FileExtensionUtil());
        values.add(new PositionUtil());
        values.add(new PriorityTaskUtil());
        values.add(new StateTestingUtil());
        values.add(new TypeComplexityUtil());
    }

    public static Model getInstance(){
        return INSTANCE;
    }
}
