package org.carrental.Services;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class NameOnlyFilter extends DocumentFilter{

        private int minLength;
        private int maxLength;

        public NameOnlyFilter(int minLength, int maxLength) {
            this.minLength = minLength;
            this.maxLength = maxLength;
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String newValue = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
            if (newValue.length() >= minLength && newValue.length() <= maxLength && text.matches("^\\s*$")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }


