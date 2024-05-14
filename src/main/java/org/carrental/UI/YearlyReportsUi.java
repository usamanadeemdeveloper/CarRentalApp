package org.carrental.UI;

import org.carrental.Repository.BookingDao;
import org.carrental.Repository.VehicleDao;
import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Model.VehicleOwner;
import org.carrental.Services.BookingService;
import org.carrental.Services.VehicleOwnerService;
import org.carrental.Services.YearlyService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

public class YearlyReportsUi {
    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();
    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();
    private final VehicleDao vehicleDao = new VehicleDao();
    private final BookingService bookingService = new BookingService();
    private final YearlyService yearlyService = new YearlyService();
    private final BookingDao bookingDao = new BookingDao();

    public YearlyReportsUi() {
        JFrame frame = new JFrame("Car Rental App - Yearly Reports Panel");
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        frame.add(mainPanel, gbc);

        JRadioButton ownerButton = new JRadioButton("VehicleOwner");

        JButton doneButton = new JButton("Done");
        JButton backBtn = new JButton("Back");


        JLabel ownerLabel = new JLabel("VehicleOwner");
        String[] dropdownOwner = vehicleOwnerService.transformToDropdownOwners(vehicleOwnerDao.getAll());
        JComboBox<String> comboBoxOwner = new JComboBox<>(dropdownOwner);

        int ownerCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel ownerSpinnerModel = new SpinnerNumberModel(ownerCurrentYear, ownerCurrentYear - 100, ownerCurrentYear + 0, 1);
        JSpinner ownerYearSpinner = new JSpinner(ownerSpinnerModel);
        JSpinner.DefaultEditor ownerEditor = (JSpinner.DefaultEditor) ownerYearSpinner.getEditor();
        ownerEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);

        ActionListener ownerActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ownerButton.isSelected()) {
                    mainPanel.add(ownerLabel);
                    mainPanel.add(comboBoxOwner);
                    mainPanel.add(ownerYearSpinner);
                    mainPanel.add(doneButton);
                } else {
                    mainPanel.remove(ownerLabel);
                    mainPanel.remove(comboBoxOwner);
                    mainPanel.remove(ownerYearSpinner);
                    mainPanel.remove(doneButton);
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        };
        ownerButton.addActionListener(ownerActionListener);

        mainPanel.add(ownerButton);

        JRadioButton vehicleButton = new JRadioButton("Vehicle");

        JLabel vehicleLabel = new JLabel("Vehicles");
        String[] dropdownVehicles = bookingService.transformToDropdownVehicles(vehicleDao.getAll());
        JComboBox<String> comboBoxVehicles = new JComboBox<>(dropdownVehicles);

        int vehicleCurrentYear = Calendar.getInstance().get(Calendar.YEAR);
        SpinnerNumberModel vehicleSpinnerModel = new SpinnerNumberModel(vehicleCurrentYear, vehicleCurrentYear - 100, vehicleCurrentYear + 0, 1);
        JSpinner vehicleYearSpinner = new JSpinner(vehicleSpinnerModel);
        JSpinner.DefaultEditor vehicleEditor = (JSpinner.DefaultEditor) vehicleYearSpinner.getEditor();
        vehicleEditor.getTextField().setHorizontalAlignment(JTextField.CENTER);

        ActionListener vehicleActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (vehicleButton.isSelected()) {
                    mainPanel.add(vehicleLabel);
                    mainPanel.add(comboBoxVehicles);
                    mainPanel.add(vehicleYearSpinner);
                    mainPanel.add(doneButton);
                } else {
                    mainPanel.remove(vehicleLabel);
                    mainPanel.remove(comboBoxVehicles);
                    mainPanel.remove(vehicleYearSpinner);
                    mainPanel.remove(doneButton);
                }

                mainPanel.revalidate();
                mainPanel.repaint();
            }
        };
        vehicleButton.addActionListener(vehicleActionListener);

        mainPanel.add(vehicleButton);
        mainPanel.add(backBtn);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        frame.add(tablePanel, gbc);

        String[] columns = {"Id", "VehicleName", "BookingDate", "CompleteBooking", "Price", "Status", "TotalAmount", "Commission"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);

        backBtn.addActionListener(e -> {
            frame.dispose();
            new ReportsUi();
        });

        doneButton.addActionListener(e -> {
            if (ownerButton.isSelected()) {
                int selectedYear = (int) ownerYearSpinner.getValue();
                LocalDate startDate = LocalDate.of(selectedYear, 1, 1);
                LocalDate endDate = LocalDate.of(selectedYear, 12, 31);
                String[] owner = comboBoxOwner.getSelectedItem().toString().split(",");
                String ownerName = owner[1];
                String[][] data = yearlyService.getAllYearlyBookingsForJTable(Date.valueOf(startDate), Date.valueOf(endDate), ownerName);
                tableModel.setRowCount(0);
                List<VehicleOwner> commission = bookingDao.getCommission(Date.valueOf(startDate), Date.valueOf(endDate), ownerName);
                for (String[] row : data) {
                    tableModel.addRow(row);
                }
                try {
                    new PDFGeneratorForOwnerYearlyReport(table, "Yearly-Report.pdf", commission);
                    File file = new File("yearly-Report.pdf");
                    if (file.exists()) {
                        Desktop.getDesktop().open(file);
                    } else {
                        JOptionPane.showMessageDialog(frame, "File not Found");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            if (vehicleButton.isSelected()) {
                int selectedYear = (int) vehicleYearSpinner.getValue();
                LocalDate startDate = LocalDate.of(selectedYear, 1, 1);
                LocalDate endDate = LocalDate.of(selectedYear, 12, 31);
                String[] vehicle = comboBoxVehicles.getSelectedItem().toString().split(",");
                String vehicleName = vehicle[1];
                String[][] data = yearlyService.getAllYearlyBookedVehicleForJTable(Date.valueOf(startDate), Date.valueOf(endDate), vehicleName);
                tableModel.setRowCount(0);
                for (String[] row : data) {
                    tableModel.addRow(row);
                }
                try {
                    new PDFGeneratorForYearlyVehicleReport(table, "Yearly-Report.pdf");
                    File file = new File("Yearly-Report.pdf");
                    if (file.exists()) {
                        Desktop.getDesktop().open(file);
                    } else {
                        JOptionPane.showMessageDialog(frame, "File not Found");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new YearlyReportsUi();
            }
        });
    }
}
