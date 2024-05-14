package org.carrental.UI;

import com.toedter.calendar.JDateChooser;
import org.carrental.Repository.BookingDao;
import org.carrental.Model.Booking;
import org.carrental.Services.BookingService;
import org.carrental.Services.NumberOnlyFilter;
import org.carrental.Services.TextOnlyFilter;

import javax.swing.*;
import java.awt.*;
import java.sql.Date;

public class UpdateBookingUi {
    private final BookingService bookingService = new BookingService();

    private final BookingDao bookingDao = new BookingDao();

    private final TextOnlyFilter filter = new TextOnlyFilter();

    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();

    public UpdateBookingUi(Integer id, String customerDropDown, String vehicleDropDown, String price,Date calender) {
        JFrame frame = new JFrame("Car Rental App - UpdateBooking");

        frame.setLayout(new GridLayout(6,4,10,10));

        JLabel customerLabel = new JLabel("ActiveCustomers");
        String[] dropdownCustomers = bookingService.transformToDropdownCustomers(bookingDao.getActiveCustomers());
        JComboBox<String> comboBoxCustomers = new JComboBox<>(dropdownCustomers);


        JLabel vehicleLabel = new JLabel("Vehicles");
        String[] dropdownVehicles = bookingService.transformToDropdownVehicles(bookingDao.getBookedVehicles());
        JComboBox<String> comboBoxVehicles = new JComboBox<>(dropdownVehicles);


        JLabel priceLabel = new JLabel("Price");
        JTextField priceTF = new JTextField();

        JLabel datePicker = new JLabel("DatePicker");
        JDateChooser calendar = new JDateChooser();
        calendar.setEnabled(false);

        JButton backBtn = new JButton("Back");
        JButton updateBtn = new JButton("Update");

        String[] customer = comboBoxCustomers.getSelectedItem().toString().split(",");
        String cId = customer[0];
        comboBoxCustomers.setEnabled(false);
        String[] vehicle = comboBoxVehicles.getSelectedItem().toString().split(",");
        String vId = vehicle[0];
        comboBoxVehicles.setEnabled(false);

        backBtn.addActionListener(e -> {
            frame.dispose();
            new BookingUi();
        });

        updateBtn.addActionListener(e -> {
            if (comboBoxCustomers.getSelectedItem().toString().isEmpty() || comboBoxVehicles.getSelectedItem().toString().isEmpty()
                    || priceTF.getText().isEmpty() || calendar.getDate().toString().isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill fields");
            }

            if (id !=null){
                Booking booking = Booking.builder()
                                                .price(Double.valueOf(priceTF.getText()))
                                                        .datePicker(new java.sql.Date(calender.getTime()))
                                                                .build();
                bookingDao.update(booking,Long.valueOf(id));
                frame.dispose();
                new BookingUi();
            }
        });

        comboBoxCustomers.setSelectedItem(customerDropDown);
        comboBoxVehicles.setSelectedItem(vehicleDropDown);
        priceTF.setText(price);
        calendar.setDate(calender);
        frame.add(customerLabel);
        frame.add(comboBoxCustomers);
        frame.add(vehicleLabel);
        frame.add(comboBoxVehicles);
        frame.add(priceLabel);
        frame.add(priceTF);
        frame.add(datePicker);
        frame.add(calendar);
        frame.add(updateBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
