package org.carrental.UI;

import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Services.*;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddVehicleUi {
    private final VehicleService vehicleService = new VehicleService();
    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();
    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();

    private final TextOnlyFilter filter = new TextOnlyFilter();

    private final NumberOnlyFilter numberOnlyFilter = new NumberOnlyFilter();

    public AddVehicleUi(Long id){
        JFrame frame = new JFrame("Car Rental App - Add Vehicle");

        frame.setLayout(new GridLayout(6,6,10,10));

        JLabel nameLabel = new JLabel("VehicleName");
        JTextField nameTF = new JTextField(10);
        ((AbstractDocument) nameTF.getDocument()).setDocumentFilter(filter);

        JLabel modelLabel = new JLabel("Model");
        JTextField modelTF = new JTextField(10);
        ((AbstractDocument) modelTF.getDocument()).setDocumentFilter(numberOnlyFilter);

        JLabel brandLabel = new JLabel("Brand");
        JTextField brandTF = new JTextField(10);
        ((AbstractDocument) brandTF.getDocument()).setDocumentFilter(filter);

        JLabel colorLabel = new JLabel("Color");
        JTextField colorTF = new JTextField(10);
        ((AbstractDocument) colorTF.getDocument()).setDocumentFilter(filter);

        JLabel ownerLabel = new JLabel("VehicleOwner");
        String[] dropdownOwner = vehicleOwnerService.transformToDropdownOwners(vehicleOwnerDao.getAll());
        JComboBox<String> comboBoxOwner = new JComboBox<>(dropdownOwner);



        JButton backBtn = new JButton("Back");
        JButton saveBtn = new JButton("Save");



        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBoxOwner.getSelectedItem() != id) {
                    frame.dispose();
                    vehicleOwnerDao.deleteByOnlyId(id);
                    new BookingUi();
                    // code to go back
                }
            }
        });
        vehicleOwnerDao.setForeignKeyTo1();
        saveBtn.addActionListener(e -> {
            if ( nameTF.getText().isEmpty() || modelTF.getText().isEmpty() ||
                 brandTF.getText().isEmpty() || colorTF.getText().isEmpty()||
                 comboBoxOwner.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill fields");
            }
                String[] owner = comboBoxOwner.getSelectedItem().toString().split(",");
                String oId = owner[0];
                if (id != null) {
                    if (oId.equalsIgnoreCase(String.valueOf(id))) {
                        vehicleService.save(nameTF.getText(), Integer.valueOf(modelTF.getText()), brandTF.getText(),
                                colorTF.getText(), oId);
                        frame.dispose();
                        new VehicleUi();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Vehicle owner" + id + "can't be added until you add his vehicle");
                    }
                    if (comboBoxOwner.getSelectedItem() != id) {
                        frame.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                vehicleOwnerDao.deleteByOnlyId(id);
                            }
                        });
                    }
                }
        });

        vehicleOwnerDao.setForeignKeyTo1();

//        if (id != null && id > -1){
//            Vehicle vehicle = vehicleDao.getById(Long.valueOf(id));
//            nameTF.setText(vehicle.getVehicleName());
//            modelTF.setText(vehicle.getModel().toString());
//            brandTF.setText(vehicle.getBrand());
//            colorTF.setText(vehicle.getColor());
//            ownerIdTF.setText(vehicle.getOwnerId().toString());
//            bookingStatusTF.setText(vehicle.getBookingStatus());
//        }

        frame.add(nameLabel);
        frame.add(nameTF);
        frame.add(modelLabel);
        frame.add(modelTF);
        frame.add(brandLabel);
        frame.add(brandTF);
        frame.add(colorLabel);
        frame.add(colorTF);
        frame.add(ownerLabel);
        frame.add(comboBoxOwner);
        frame.add(saveBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

}
