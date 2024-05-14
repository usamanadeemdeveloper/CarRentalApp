package org.carrental.UI;

import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Services.NumberOnlyFilter;
import org.carrental.Services.TextOnlyFilter;
import org.carrental.Services.VehicleOwnerService;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class AddVehicleOwnerUi {

    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();

    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();

    private final TextOnlyFilter filter = new TextOnlyFilter();

    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();


    public AddVehicleOwnerUi() {
        JFrame frame = new JFrame("Car Rental App - Add Customer");

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
        JButton saveBtn = new JButton("Save");

        backBtn.addActionListener(e -> {
            frame.dispose();
            new VehicleOwnerUi();
        });

        saveBtn.addActionListener(e -> {
            try {
                Long id = vehicleOwnerDao.insert(vehicleOwnerService.save(nameTF.getText(), Long.valueOf(phoneNoTF.getText()),
                        Long.valueOf(nicNoTF.getText()), addressTF.getText(), Float.valueOf(commissionTF.getText())));
                frame.dispose();
                new AddVehicleUi(id);

            }catch (Exception ex){
                JOptionPane.showMessageDialog(frame,"Please fill all fields");
            }
        });

//        if (id != null && id > -1){
//            VehicleOwner vehicleOwner = vehicleOwnerDao.getById(Long.valueOf(id));
//            nameTF.setText(vehicleOwner.getOwnerName());
//            phoneNoTF.setText(vehicleOwner.getPhoneNo());
//            nicNoTF.setText(vehicleOwner.getNicNo());
//            addressTF.setText(vehicleOwner.getAddress());
//            commissionTF.setText(vehicleOwner.getCommission().toString());
//        }

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
        frame.add(saveBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
