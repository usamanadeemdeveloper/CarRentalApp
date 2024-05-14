package org.carrental.UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeUi {
    public HomeUi() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 720);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        frame.add(mainPanel);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JPanel buttonPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));


        JButton vehicleOwner = createButton("Vehicle Owner", "vehicle-owner.png", Color.BLACK);
        vehicleOwner.addActionListener(e -> {
            frame.dispose();
            new VehicleOwnerUi();
        });
        buttonPanel.add(vehicleOwner);

        JButton vehicle = createButton("Vehicle", "vehicle.png", Color.BLACK);
        vehicle.addActionListener(e -> {
            frame.dispose();
            new VehicleUi();
        });
        buttonPanel.add(vehicle);

        JButton customer = createButton("Customer", "customer.png", Color.BLACK);
        customer.addActionListener(e -> {
            frame.dispose();
            new CustomerUi();
        });
        buttonPanel.add(customer);

        JButton booking = createButton("Booking", "booking.png", Color.BLACK);
        booking.addActionListener(e -> {
            frame.dispose();
            new BookingUi();
        });
        buttonPanel.add(booking);

        JButton user = createButton("User", "user.png", Color.BLACK);
        user.addActionListener(e -> {
            frame.dispose();
            new UserTable();
        });
        buttonPanel.add(user);

        JButton reports = createButton("Reports", "reports.png", Color.BLACK);
        reports.addActionListener(e -> {
            frame.dispose();
            new ReportsUi();
        });
        buttonPanel.add(reports);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonPanel, constraints);

        JPanel logoutPanel = new JPanel();
        logoutPanel.setOpaque(false);
        JButton logOut = createButton("Log Out", "logout.png", Color.RED);
        logOut.addActionListener(e -> {
            frame.dispose();
            new LoginUi();
        });
        logoutPanel.add(logOut);

        logOut.setPreferredSize(new Dimension(150, logOut.getPreferredSize().height));  // Adjust the width as needed
        constraints.gridy = 1;
        constraints.weighty = 0;
        mainPanel.add(logoutPanel, constraints);

        frame.setVisible(true);
    }

    private JButton createButton(String text, String iconPath, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        if (iconPath != null) {
            ImageIcon icon = new ImageIcon(iconPath);
            button.setIcon(icon);
        }
        return button;
    }

}