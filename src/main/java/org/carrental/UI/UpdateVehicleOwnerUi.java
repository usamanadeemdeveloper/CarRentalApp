package org.carrental.UI;

import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Model.VehicleOwner;
import org.carrental.Services.NumberOnlyFilter;
import org.carrental.Services.TextOnlyFilter;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class UpdateVehicleOwnerUi {
    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();
    private final TextOnlyFilter filter = new TextOnlyFilter();
    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();
    public UpdateVehicleOwnerUi(Long id, String ownerName, String phoneNo, String nicNo, String address, Float commission) {
        JFrame frame = new JFrame("Car Rental App - Update Customer");

        frame.setLayout(new GridLayout(6,4,10,10));

        JLabel nameLabel = new JLabel("OwnerName");
        JTextField nameTF = new JTextField(20);
        ((AbstractDocument) nameTF.getDocument()).setDocumentFilter(filter);

        JLabel phoneNoLabel = new JLabel("PhoneNo");
        JTextField phoneNoTF = new JTextField(20);
        ((AbstractDocument) phoneNoTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JLabel nicNoLabel = new JLabel("NicNo");
        JTextField nicNoTF = new JTextField(20);
        ((AbstractDocument) nicNoTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JLabel addressLabel = new JLabel("Address");
        JTextField addressTF = new JTextField(20);
        ((AbstractDocument) addressTF.getDocument()).setDocumentFilter(filter);

        JLabel commissionLabel = new JLabel("Commission");
        JTextField commissionTF = new JTextField(20);
        ((AbstractDocument) commissionTF.getDocument()).setDocumentFilter(numberOnlyFilter);


        JButton backBtn = new JButton("Back");
        JButton updateBtn = new JButton("Update");

        backBtn.addActionListener(e -> {
            frame.dispose();
            new VehicleOwnerUi();
        });

        updateBtn.addActionListener(e -> {
            if (nameTF.getText().isEmpty() || phoneNoTF.getText().isEmpty()
                    || nicNoTF.getText().isEmpty() || addressTF.getText().isEmpty()
                    || commissionTF.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill fields");
            }
            if (id !=null){
                VehicleOwner vehicleOwner = VehicleOwner.builder()
                        .id(id)
                        .ownerName(nameTF.getText())
                        .phoneNo(Long.valueOf(phoneNoTF.getText()))
                        .nicNo(Long.valueOf(nicNoTF.getText()))
                        .address(addressTF.getText())
                        .commission(Float.valueOf(commissionTF.getText()))
                        .build();
                vehicleOwnerDao.update(vehicleOwner,id);
                frame.dispose();
                new VehicleOwnerUi();
            }
        });

        nameTF.setText(ownerName);
        phoneNoTF.setText(phoneNo);
        nicNoTF.setText(nicNo);
        addressTF.setText(address);
        commissionTF.setText(String.valueOf(commission));
        frame.add(nameLabel);
        frame.add(nameTF);
        frame.add(phoneNoLabel);
        frame.add(phoneNoTF);
        frame.add(nicNoLabel);
        frame.add(nicNoTF);
        frame.add(addressLabel);
        frame.add(addressTF);
        frame.add(commissionLabel);
        frame.add(commissionTF);
        frame.add(updateBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
