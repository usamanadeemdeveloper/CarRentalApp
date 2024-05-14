package org.carrental.Services;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class TextOnlyFilter extends DocumentFilter{


        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            for (int i = 0; i < text.length(); i++) {
                if (!Character.isLetter(text.charAt(i))) {
                    Toolkit.getDefaultToolkit().beep(); // beep sound on invalid input
                    return;
                }
            }
            super.replace(fb, offset, length, text, attrs);
        }
}
