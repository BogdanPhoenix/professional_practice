package org.university.ui.additional_tools;

import javax.swing.*;

public class MessageWindow {
    private MessageWindow(){}

    public static void errorMessageWindow(String title, String message){
        JOptionPane.showMessageDialog(null,message,
                title, JOptionPane.ERROR_MESSAGE);
    }

    public static boolean updateMessageWindow(){
        int result = createOptionPanel("Підтвердження оновлення", "Ви впевнені, що хочете оновити дані обраного об'єкта? Попереднє значення буде видалене.");
        return result == JOptionPane.YES_OPTION;
    }

    public static boolean deleteMessageWindow(){
        int result = createOptionPanel("Підтвердження видалення", "Ви впевнені, що хочете видалити обраний об'єкт? Всі дочірні дані також будуть видалені.");
        return result == JOptionPane.YES_OPTION;
    }

    private static int createOptionPanel(String title, String message){
        return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
    }
}
