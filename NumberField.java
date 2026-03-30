// Joshua Staub

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

// JTextField that only accepts integers. Replacement for JFormattedTextField, which is unnecessarily complicated
// to use and doesn't behave as well.
public class NumberField extends JTextField implements DocumentListener {
    private int minValue;
    private int maxValue;
    private int curValue;
    private boolean debounce = false;

    public NumberField(int curValue, int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        setCurValue(curValue);

        this.getDocument().addDocumentListener(this);
    }

    public NumberField(int curValue) {
        this(curValue, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public NumberField() {
        this(0);
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
        setCurValue(curValue);
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        setCurValue(curValue);
    }

    public int getCurValue() {
        return curValue;
    }

    public void setCurValue(int curValue) {
        debounce = true;
        this.curValue = Math.clamp(curValue, minValue, maxValue);
        setText(Integer.toString(this.curValue));
        debounce = false;
    }

    private void textUpdated() {
        // Prevent infinite loops caused by updating the text in the listener
        if (debounce) {
            return;
        }

        // Cannot call setText from a document listener directly, so we have to defer it
        EventQueue.invokeLater(() -> {
            String text = getText();
            if (text.isEmpty()) {
                setCurValue(Math.max(0, minValue));
            } else {
                try {
                    setCurValue(Integer.parseInt(text));
                } catch (NumberFormatException ignored) {
                    setCurValue(curValue);
                }
            }
        });
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        textUpdated();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        textUpdated();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        textUpdated();
    }

    @Override
    public String toString() {
        return getText();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NumberField)) {
            return false;
        }

        NumberField otherField = (NumberField)obj;

        if (getText().equals(otherField.getText())) {
            return true;
        }

        return false;
    }
}
