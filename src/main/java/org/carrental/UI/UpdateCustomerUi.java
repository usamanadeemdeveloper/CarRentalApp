package org.carrental.UI;

import org.carrental.Repository.BookingDao;
import org.carrental.Repository.CustomerDao;
import org.carrental.Model.Customer;
import org.carrental.Services.NumberOnlyFilter;
import org.carrental.Services.TextOnlyFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class UpdateCustomerUi {
    private final CustomerDao customerDao = new CustomerDao();

    private final BookingDao bookingDao = new BookingDao();
    private final TextOnlyFilter filter = new TextOnlyFilter();
    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();
    public UpdateCustomerUi(Long id,String name, String phoneNo, String nicNo, String address, String referencePhoneNo,String status){
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
        ((AbstractDocument) phoneTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JButton backBtn = new JButton("Back");
        JButton updateBtn = new JButton("Update");

        backBtn.addActionListener(e -> {
            frame.dispose();
            new CustomerUi();
        });

        updateBtn.addActionListener(e -> {
            if (nameTF.getText().isEmpty() || phoneTF.getText().isEmpty()
                    || nicTF.getText().isEmpty() || addressTF.getText().isEmpty()
                    || referencePhoneNoTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill fields");
            };


            if (id !=null){
                        Customer customer = Customer.builder()
                                .name(nameTF.getText())
                                .phoneNo(Long.valueOf(phoneTF.getText()))
                                .nicNo(Long.valueOf(nicTF.getText()))
                                .address(addressTF.getText())
                                .referencePhoneNo(Long.valueOf(referencePhoneNoTF.getText()))
                                .build();
                        customerDao.update(customer, id);
                        frame.dispose();
                        new CustomerUi();
                    }
        });

        nameTF.setText(name);
        phoneTF.setText(phoneNo);
        nicTF.setText(nicNo);
        addressTF.setText(address);
        referencePhoneNoTF.setText(referencePhoneNo);


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
        frame.add(updateBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }
}
