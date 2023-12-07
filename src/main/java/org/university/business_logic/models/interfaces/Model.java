package org.university.business_logic.models.interfaces;

import org.university.ui.components.interfaces.Component;
import org.university.business_logic.abstracts.TableModelView;
import org.university.ui.mediator.interfaces.Mediator;

import java.util.List;
import java.util.Optional;

public abstract class Model implements Component {
    protected Mediator mediator;
    protected List<TableModelView<?>> values;

    public List<String> valuesModel(){
        return values.stream()
                .map(TableModelView::getNameTable)
                .toList();
    }

    public Optional<TableModelView<?>> valueOfString(String value){
        return values.stream()
                .filter(v -> v.getNameTable().equals(value))
                .findFirst();
    }

    @Override
    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    @Override
    public String getName() {
        return "Model";
    }
}
