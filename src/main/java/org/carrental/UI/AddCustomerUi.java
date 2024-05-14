package org.carrental.UI;

import org.carrental.Repository.CustomerDao;
import org.carrental.Repository.VehicleDao;
import org.carrental.Services.CustomerService;
import org.carrental.Services.NumberOnlyFilter;
import org.carrental.Services.TextOnlyFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddCustomerUi {

    private final CustomerService customerService = new CustomerService();
    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();
    private final TextOnlyFilter filter = new TextOnlyFilter();
    private final CustomerDao customerDao = new CustomerDao();

    private final VehicleDao vehicleDao = new VehicleDao();
    public AddCustomerUi(Long vehicleId){
        JFrame frame = new JFrame("Car Rental App - Add Customer");

        frame.setLayout(new GridLayout(6,4,10,10));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameTF = new JTextField(20);
        ((AbstractDocument) nameTF.getDocument()).setDocumentFilter(filter);

        JLabel phoneLabel = new JLabel("Phone");
        JTextField phoneTF = new JTextField(20);
        ((AbstractDocument) phoneTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JLabel nicLabel = new JLabel("NicNo");
        JTextField nicTF = new JTextField(20);
        ((AbstractDocument) nicTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JLabel addressLabel = new JLabel("Address");
        JTextField addressTF = new JTextField(20);
        ((AbstractDocument) addressTF.getDocument()).setDocumentFilter(filter);

        JLabel referencePhoneNoLabel = new JLabel("ReferencePhoneNo");
        JTextField referencePhoneNoTF = new JTextField(20);
        ((AbstractDocument) referencePhoneNoTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JButton backBtn = new JButton("Back");
        JButton saveBtn = new JButton("Save");

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                    String status = "UnActive";
                    vehicleDao.activate(status, vehicleId);
                    new VehicleUi();
                    // code to go back
                }

        });

        saveBtn.addActionListener(e -> {
            try {
                    JTextField statusTF = new JTextField();
                    statusTF.setText("Active");
                    Long customerId = customerDao.insert(customerService.save(nameTF.getText(), Long.valueOf(phoneTF.getText()),
                            Long.valueOf(nicTF.getText()), addressTF.getText(), Long.valueOf(referencePhoneNoTF.getText()), statusTF.getText()));
                    frame.dispose();
                    new AddBookingUi(customerId,vehicleId);

            }catch (Exception ex){
                JOptionPane.showMessageDialog(frame,"Please fill all fields");
            }
        });



            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    String status = "UnActive";
                    vehicleDao.activate(status, vehicleId);
                    frame.dispose();
                    new VehicleUi();
                }
            });



        frame.add(nameLabel);
        frame.add(nameTF);
        frame.add(phoneLabel);
        frame.add(phoneTF);
        frame.add(nicLabel);
        frame.add(nicTF);
        frame.add(addressLabel);
        frame.add(addressTF);
        frame.add(referencePhoneNoLabel);
        frame.add(referencePhoneNoTF);
        frame.add(saveBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }
}
