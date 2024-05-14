package org.carrental.UI;

import com.toedter.calendar.JDateChooser;
import org.carrental.Repository.BookingDao;
import org.carrental.Repository.MonthlyReportDao;
import org.carrental.Model.VehicleOwner;
import org.carrental.Services.MonthlyService;
import org.carrental.Services.PDFGeneratorForMonthlyReport;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class MonthlyReportUi {
    private MonthlyService monthlyService = new MonthlyService();
    private MonthlyReportDao monthlyReportDao = new MonthlyReportDao();
    private BookingDao bookingDao = new BookingDao();

    public MonthlyReportUi() {
        JFrame frame = new JFrame("Car Rental App - Monthly Report");
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2, 10, 10));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel startDateLabel = new JLabel("Start Date:");
        JDateChooser startDatePicker = new JDateChooser();
        panel.add(startDateLabel);
        panel.add(startDatePicker);

        JLabel endDateLabel = new JLabel("End Date:");
        JDateChooser endDatePicker = new JDateChooser();
        panel.add(endDateLabel);
        panel.add(endDatePicker);

        JButton generateButton = new JButton("Generate Report");
        panel.add(generateButton);

        JButton backBtn = new JButton("Back");
        panel.add(backBtn);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"Id", "CustomerName", "VehicleName", "OwnerName", "BookingDate", "Price", "Status", "CompleteBooking", "TotalAmount", "Commission"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table));

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = monthlyService.getAllMonthlyBookingsForJTable(startDatePicker.getDate(), endDatePicker.getDate());
                tableModel.setRowCount(0);
                Double finalTotal = monthlyReportDao.getAllTotalInFinalTotal();
                List<VehicleOwner> commission = bookingDao.totalCommission(startDatePicker.getDate(), endDatePicker.getDate());
                for (String[] row : data) {
                    tableModel.addRow(row);
                }
                try {
                    new PDFGeneratorForMonthlyReport(table, "Monthly-Report.pdf", finalTotal, commission);
                    File file = new File("Monthly-Report.pdf");
                    if (file.exists()) {
                        Desktop.getDesktop().open(file);
                    } else {
                        JOptionPane.showMessageDialog(frame, "File not found");
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        backBtn.addActionListener(e -> {
            frame.dispose();
            new ReportsUi();
        });

        frame.add(panel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}