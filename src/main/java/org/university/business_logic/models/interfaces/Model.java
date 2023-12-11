package org.university.business_logic.models.interfaces;

import org.university.ui.components.menu_panel.interfaces.Component;
import org.university.business_logic.abstracts.ReferenceBookModelView;
import org.university.ui.mediator.interfaces.Mediator;

import java.util.List;
import java.util.Optional;

public abstract class Model implements Component {
    protected Mediator mediator;
    protected List<ReferenceBookModelView<?>> values;

    public List<String> valuesModel(){
        return values.stream()
                .map(ReferenceBookModelView::getNameTable)
                .toList();
    }

    public Optional<ReferenceBookModelView<?>> valueOfString(String value){
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
