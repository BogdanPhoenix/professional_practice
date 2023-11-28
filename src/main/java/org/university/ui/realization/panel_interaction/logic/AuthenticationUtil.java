package org.university.ui.realization.panel_interaction.logic;

import org.university.business_logic.tables.Authentication;
import org.university.ui.interfaces.panel_interaction.logic.action_with_database.Insert;
import org.university.ui.interfaces.panel_interaction.logic.action_with_database.Select;

public class AuthenticationUtil implements Select<Authentication>, Insert<Authentication> {
    @Override
    public Class<Authentication> resolveEntityClass() {
        return Authentication.class;
    }
}
