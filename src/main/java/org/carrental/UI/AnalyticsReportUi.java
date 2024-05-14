package org.carrental.UI;

import com.toedter.calendar.JDateChooser;
import org.carrental.Services.VehicleService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class AnalyticsReportUi {
    private final VehicleService vehicleService = new VehicleService();

    public AnalyticsReportUi() {
        JFrame frame = new JFrame("Car Rental App - Analytics Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JLabel startDateLabel = new JLabel("Start Date:");
        JDateChooser startDateChooser = new JDateChooser();
        startDateChooser.setDateFormatString("yyyy-MM-dd");

        JLabel endDateLabel = new JLabel("End Date:");
        JDateChooser endDateChooser = new JDateChooser();
        endDateChooser.setDateFormatString("yyyy-MM-dd");

        datePanel.add(startDateLabel);
        datePanel.add(startDateChooser);
        datePanel.add(endDateLabel);
        datePanel.add(endDateChooser);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton doneButton = new JButton("Done");
        JButton backButton = new JButton("Back");

        buttonPanel.add(doneButton);
        buttonPanel.add(backButton);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columnNames = {"Id", "Vehicle Name", "Model", "Brand", "Color", "Commission"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        doneButton.addActionListener(e -> {
            String[][] data = vehicleService.getAllBookedTotalCarsForJTable(startDateChooser.getDate(), endDateChooser.getDate());
            tableModel.setDataVector(data, columnNames);
            try {
                new PDFGeneratorForAllBookedCars(table, "Analytics-Report.pdf");
                File file = new File("Analytics-Report.pdf");
                if (file.exists()) {
                    Desktop.getDesktop().open(file);
                } else {
                    JOptionPane.showMessageDialog(frame, "File not Found");
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        backButton.addActionListener(e -> {
            frame.dispose();
            new ReportsUi();
        });

        mainPanel.add(datePanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setVisible(true);
    }
}
