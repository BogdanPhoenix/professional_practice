package org.university.ui.components.input_panel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PasswordField extends JPasswordField {
    private static final int TEXT_FIELD_SIZE_BORDER = 5;
    private static final EmptyBorder TEXT_FIELD_BORDER = new EmptyBorder(TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER,
            TEXT_FIELD_SIZE_BORDER, TEXT_FIELD_SIZE_BORDER);

    public PasswordField(String name, char echoChar){
        this.setFont(new Font("Arial", Font.PLAIN, 14));
        this.setName(name);

        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border compoundBorder = new CompoundBorder(lineBorder, TEXT_FIELD_BORDER);
        this.setBorder(compoundBorder);
        this.setEchoChar(echoChar);
    }
}
