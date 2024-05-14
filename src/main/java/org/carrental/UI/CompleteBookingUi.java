package org.carrental.UI;

import com.toedter.calendar.JDateChooser;
import org.carrental.Repository.BookingDao;
import org.carrental.Repository.VehicleDao;
import org.carrental.Model.Booking;
import org.carrental.Model.Vehicle;

import javax.swing.*;
import java.awt.*;
import java.util.Date;
import java.util.List;

public class CompleteBookingUi {
    private final VehicleDao vehicleDao = new VehicleDao();
    private final BookingDao bookingDao = new BookingDao();
    public CompleteBookingUi(Long id,String status) {
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout(3,4,10,10));
        JLabel datePicker = new JLabel("CompleteBooking");
        JDateChooser endDatePicker = new JDateChooser();
        endDatePicker.setMinSelectableDate(new java.util.Date());
        endDatePicker.setMaxSelectableDate(new java.util.Date());
        JButton completeBooking = new JButton("CompleteBooking");
        JButton backBtn = new JButton("Back");

        completeBooking.addActionListener(e -> {
            Date completeDate;
            completeDate = endDatePicker.getDate();
            if (id!=null) {
                 List<Vehicle> vehicleName = vehicleDao.getVehicleById(id);
                     Booking booking = Booking.builder()
                             .bookingStatus(status)
                             .completeBooking(new java.sql.Date(completeDate.getTime()))
                             .build();
                     bookingDao.completeBookingStatus(id, booking);
                     frame.dispose();
                     new BookingUi();
                JOptionPane.showMessageDialog(frame,"Now vehicle ID : " + vehicleName.get(0).getId() + "" + vehicleName.get(0).getVehicleName() + " is available");
                String UnActive = "Available";
                vehicleDao.setVehicleStatusUnActive(UnActive,id);
            }
        });
        backBtn.addActionListener(e -> {
            frame.dispose();
            new BookingUi();
        });

        frame.add(datePicker);
        frame.add(endDatePicker);
        frame.add(completeBooking);
        frame.add(backBtn);
        frame.setSize(500,500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
