package org.carrental.Services;

import com.toedter.calendar.JMonthChooser;

import javax.swing.*;
import java.time.LocalDate;
import java.util.Calendar;

public class CustomMonthChooser extends JMonthChooser {
    @Override
    public JComboBox<?> getComboBox() {
        JComboBox<?> comboBox = (JComboBox<?>) super.getComboBox();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(java.sql.Date.valueOf(LocalDate.now()));
        int currentMonth = calendar.get(Calendar.MONTH);

        comboBox.setMaximumRowCount(currentMonth + 1);

        return comboBox;
    }
}
