package org.carrental.UI;

import org.carrental.Repository.VehicleDao;
import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Model.Vehicle;
import org.carrental.Services.TextOnlyFilter;
import org.carrental.Services.VehicleOwnerService;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;

public class UpdateVehicleUi {
    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();
    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();
    private final VehicleDao vehicleDao = new VehicleDao();
    private final TextOnlyFilter filter = new TextOnlyFilter();

    public UpdateVehicleUi(Long id, String name, String model, String brand, String color, String ownerIdDropDown) {
        JFrame frame = new JFrame("Car Rental App - Update Customer");

        frame.setLayout(new GridLayout(6,4,10,10));

        JLabel nameLabel = new JLabel("Name");
        JTextField nameTF = new JTextField(20);
        ((AbstractDocument) nameTF.getDocument()).setDocumentFilter(filter);

        JLabel modelLabel = new JLabel("Model");
        JTextField modelTF = new JTextField(20);

        JLabel brandLabel = new JLabel("Brand");
        JTextField brandTF = new JTextField(20);
        ((AbstractDocument) brandTF.getDocument()).setDocumentFilter(filter);

        JLabel colorLabel = new JLabel("Color");
        JTextField colorTF = new JTextField(20);
        ((AbstractDocument) colorTF.getDocument()).setDocumentFilter(filter);

        JLabel ownerLabel = new JLabel("VehicleOwner");
        String[] dropdownOwner = vehicleOwnerService.transformToDropdownOwners(vehicleOwnerDao.getAll());
        JComboBox<String> comboBoxOwner = new JComboBox<>(dropdownOwner);


        JButton backBtn = new JButton("Back");
        JButton updateBtn = new JButton("Update");


        backBtn.addActionListener(e -> {
            frame.dispose();
            new VehicleUi();
        });

        updateBtn.addActionListener(e -> {

            if (nameTF.getText().isEmpty() || modelTF.getText().isEmpty()
                    || brandTF.getText().isEmpty() || colorTF.getText().isEmpty()
                    || comboBoxOwner.getSelectedItem().toString().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill fields");
            }

            if (id !=null){
                String[] owner = comboBoxOwner.getSelectedItem().toString().split(",");
                String oId = owner[0];
                        Vehicle vehicle = Vehicle.builder()
                                .vehicleName(nameTF.getText())
                                .model(Integer.valueOf(modelTF.getText()))
                                .brand(brandTF.getText())
                                .color(colorTF.getText())
                                .ownerId(oId)
                                .build();
                        vehicleDao.update(vehicle,id);
                        frame.dispose();
                        new VehicleUi();
                    }
        });

        nameTF.setText(name);
        modelTF.setText(model);
        brandTF.setText(brand);
        colorTF.setText(color);
        comboBoxOwner.setSelectedItem(ownerIdDropDown);
        frame.add(nameLabel);
        frame.add(nameTF);
        frame.add(modelLabel);
        frame.add(modelTF);
        frame.add(brandLabel);
        frame.add(brandTF);
        frame.add(colorLabel);
        frame.add(colorTF);
        frame.add(updateBtn);
        frame.add(backBtn);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
}
