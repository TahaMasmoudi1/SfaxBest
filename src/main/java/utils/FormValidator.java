package utils;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FormValidator {

    private static final String ERROR_STYLE   = "-fx-border-color: #e50914; -fx-border-width: 1.5; -fx-border-radius: 6; -fx-background-radius: 6;";
    private static final String NORMAL_STYLE  = "";

    public static boolean requireNonEmpty(TextField field, Label errorLabel, String message) {
        if (field.getText() == null || field.getText().trim().isEmpty()) {
            markError(field, errorLabel, message);
            return false;
        }
        clearError(field, errorLabel);
        return true;
    }

    public static boolean requireNonEmpty(TextArea field, Label errorLabel, String message) {
        if (field.getText() == null || field.getText().trim().isEmpty()) {
            field.setStyle(ERROR_STYLE);
            showLabel(errorLabel, message);
            return false;
        }
        field.setStyle(NORMAL_STYLE);
        hideLabel(errorLabel);
        return true;
    }

    public static boolean requireInt(TextField field, Label errorLabel, String message) {
        String val = field.getText() == null ? "" : field.getText().trim();
        if (val.isEmpty()) {
            markError(field, errorLabel, "This field is required.");
            return false;
        }
        try {
            Integer.parseInt(val);
            clearError(field, errorLabel);
            return true;
        } catch (NumberFormatException e) {
            markError(field, errorLabel, message);
            return false;
        }
    }

    public static boolean requirePositiveInt(TextField field, Label errorLabel, String message) {
        if (!requireInt(field, errorLabel, message)) return false;
        if (Integer.parseInt(field.getText().trim()) <= 0) {
            markError(field, errorLabel, message);
            return false;
        }
        clearError(field, errorLabel);
        return true;
    }

    public static void clearError(TextField field, Label errorLabel) {
        field.setStyle(NORMAL_STYLE);
        hideLabel(errorLabel);
    }

    public static void clearError(TextArea field, Label errorLabel) {
        field.setStyle(NORMAL_STYLE);
        hideLabel(errorLabel);
    }

    public static void clearAll(TextField[] fields, Label[] labels) {
        for (int i = 0; i < fields.length; i++) clearError(fields[i], labels[i]);
    }

    private static void markError(TextField field, Label errorLabel, String message) {
        field.setStyle(ERROR_STYLE);
        showLabel(errorLabel, message);
    }

    private static void showLabel(Label label, String message) {
        if (label == null) return;
        label.setText(message);
        label.setVisible(true);
        label.setManaged(true);
    }

    private static void hideLabel(Label label) {
        if (label == null) return;
        label.setText("");
        label.setVisible(false);
        label.setManaged(false);
    }
}