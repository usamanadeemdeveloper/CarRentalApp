package org.carrental.UI;

import org.carrental.Services.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class CarAvailabilityReportUi {
        private final VehicleService vehicleService = new VehicleService();

        public CarAvailabilityReportUi() {
                JFrame frame = new JFrame("Car Rental App - Car Availability Report");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());

                // Background Image
                ImageIcon backgroundImage = new ImageIcon("path/to/background.jpg");
                JLabel backgroundLabel = new JLabel(backgroundImage);
                backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());
                frame.add(backgroundLabel);

                JPanel contentPanel = new JPanel();
                contentPanel.setOpaque(false);
                contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
                contentPanel.setLayout(new BorderLayout());

                JPanel tablePanel = new JPanel(new BorderLayout());
                String[] column = {"Id", "Vehicle Name", "Model", "Brand", "Color", "Owner Id", "Booking Status"};
                DefaultTableModel tableModel = new DefaultTableModel(column, 0);
                JTable table = new JTable(tableModel);
                tablePanel.add(new JScrollPane(table));

                JButton generatePDFAndExtractAvailableCars = new JButton("Generate PDF");
                JButton backBtn = new JButton("Back");

                // Button Styles
                generatePDFAndExtractAvailableCars.setForeground(Color.WHITE);
                generatePDFAndExtractAvailableCars.setBackground(Color.BLUE);
                generatePDFAndExtractAvailableCars.setFont(new Font("Arial", Font.BOLD, 16));

                backBtn.setForeground(Color.WHITE);
                backBtn.setBackground(Color.RED);
                backBtn.setFont(new Font("Arial", Font.BOLD, 16));

                JPanel btnPanel = new JPanel();
                btnPanel.setOpaque(false);
                btnPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
                btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                btnPanel.add(generatePDFAndExtractAvailableCars);
                btnPanel.add(backBtn);

                contentPanel.add(tablePanel, BorderLayout.CENTER);
                contentPanel.add(btnPanel, BorderLayout.SOUTH);

                generatePDFAndExtractAvailableCars.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                String[][] data = vehicleService.getAllAvailableCarsJTable();
                                if (data.length > 0) {
                                        tableModel.setRowCount(0);

                                        for (String[] row : data) {
                                                tableModel.addRow(row);
                                        }
                                        try {
                                                new PDFGeneratorForCarAvailability("Car-Availability-Report.pdf", table);
                                                File file = new File("Car-Availability-Report.pdf");
                                                if (file.exists()) {
                                                        Desktop.getDesktop().open(file);
                                                } else {
                                                        JOptionPane.showMessageDialog(frame, "File not Found");
                                                }
                                        } catch (Exception ex) {
                                                throw new RuntimeException(ex);
                                        }
                                } else {
                                        JOptionPane.showMessageDialog(frame, "No car is available right now");
                                }
                        }
                });
                backBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                frame.dispose();
                                new ReportsUi();
                        }
                });

                frame.add(contentPanel);
                frame.pack(); // Adjust frame size to fit components
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
        }
}