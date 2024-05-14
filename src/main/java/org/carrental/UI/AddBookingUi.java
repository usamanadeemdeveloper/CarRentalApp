package org.carrental.UI;


import com.toedter.calendar.JDateChooser;
import org.carrental.Repository.CustomerDao;
import org.carrental.Repository.VehicleDao;
import org.carrental.Services.BookingService;
import org.carrental.Services.NumberOnlyFilter;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddBookingUi {

    private final BookingService bookingService = new BookingService();
    private final CustomerDao customerDao = new CustomerDao();

    private final VehicleDao vehicleDao = new VehicleDao();

    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();

    public AddBookingUi(Long customerId,Long vehicleId) {
            JFrame frame = new JFrame("Car Rental App - Add Booking");

            frame.setLayout(new GridLayout(6,4,10,10));

            JLabel customerLabel = new JLabel("ActiveCustomers");
            String[] dropdownCustomers = bookingService.transformToDropdownCustomers(customerDao.getActiveCustomers());
            JComboBox<String> comboBoxCustomers = new JComboBox<>(dropdownCustomers);


            JLabel vehicleLabel = new JLabel("Vehicles");
            String[] dropdownVehicles = bookingService.transformToDropdownVehicles(vehicleDao.getBookedVehicles());
            JComboBox<String> comboBoxVehicles = new JComboBox<>(dropdownVehicles);


           JLabel priceLabel = new JLabel("Price");
           JTextField priceTF = new JTextField();
           ((AbstractDocument) priceTF.getDocument()).setDocumentFilter(numberOnlyFilter);

           JLabel datePicker = new JLabel("DatePicker");
           JDateChooser calendar = new JDateChooser();
           calendar.setMinSelectableDate(new java.util.Date());
           calendar.setMaxSelectableDate(new java.util.Date());

           String status = "Close";

           JButton backBtn = new JButton("Back");
           JButton bookBtn = new JButton("Book");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxCustomers.getSelectedItem() != customerId) {
                    frame.dispose();
                    customerDao.deleteById(status,customerId);
                    String vehicleStatus = "UnActive";
                    vehicleDao.activate(vehicleStatus, vehicleId);
                    new VehicleUi();
                    // code to go back
                }
            }
        });


        bookBtn.addActionListener(e -> {
            if (comboBoxCustomers.getSelectedItem().toString().isEmpty() ||
                    comboBoxVehicles.getSelectedItem().toString().isEmpty()
                    || priceTF.getText().isEmpty() || calendar.getDate().toString().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill fields");
            };
                    String[] customer = comboBoxCustomers.getSelectedItem().toString().split(",");
                    String cId = customer[0];
                    String[] vehicle = comboBoxVehicles.getSelectedItem().toString().split(",");
                    String vId = vehicle[0];
            if (customerId != null && vehicleId != null){
                        if (cId.equalsIgnoreCase(String.valueOf(customerId)) && vId.equals(String.valueOf(vehicleId))){
                            bookingService.save(cId,vId,
                                    Double.valueOf(priceTF.getText()),  calendar.getDate());
                            frame.dispose();
                            new BookingUi();
                        }else {
                            JOptionPane.showMessageDialog(frame,"Customer id " + customerId + " can't be added until he booked some vehicle");
                        }
                        if (!vId.equalsIgnoreCase(String.valueOf(vehicleId))){
                            JOptionPane.showMessageDialog(frame,"Vehicle id " + vehicleId + " can't be active until it's booked");
                        }
                    }
            });



            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    String vehicleStatus = "UnActive";
                    vehicleDao.activate(vehicleStatus, vehicleId);
                    customerDao.deleteById(status,customerId);
                }
            });

            frame.add(customerLabel);
            frame.add(comboBoxCustomers);
            frame.add(vehicleLabel);
            frame.add(comboBoxVehicles);
            frame.add(priceLabel);
            frame.add(priceTF);
            frame.add(datePicker);
            frame.add(calendar);
            frame.add(bookBtn);
            frame.add(backBtn);
            frame.setSize(2000,750);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
    }
}
