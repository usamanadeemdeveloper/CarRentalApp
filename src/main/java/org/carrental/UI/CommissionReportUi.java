package org.carrental.UI;

import com.toedter.calendar.JDateChooser;
import org.carrental.Repository.BookingDao;
import org.carrental.Repository.MonthlyReportDao;
import org.carrental.Model.VehicleOwner;
import org.carrental.Services.CommissionService;
import org.carrental.Services.MonthlyService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class CommissionReportUi {
    private final CommissionService commissionService = new CommissionService();
    private final BookingDao bookingDao = new BookingDao();
    private final MonthlyReportDao monthlyReportDao = new MonthlyReportDao();
    MonthlyService monthlyService = new MonthlyService();

    public CommissionReportUi() {
        JFrame frame = new JFrame("Car Rental App - Commission Report");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        contentPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel startDatePicker = new JLabel("Start Date:");
        JDateChooser startDate = new JDateChooser();
        startDate.setDateFormatString("yyyy-MM-dd");

        JLabel endDatePicker = new JLabel("End Date:");
        JDateChooser endDate = new JDateChooser();
        endDate.setDateFormatString("yyyy-MM-dd");

        JButton doneBtn = new JButton("Generate Report");
        JButton backBtn = new JButton("Back");

        contentPanel.add(startDatePicker);
        contentPanel.add(startDate);
        contentPanel.add(endDatePicker);
        contentPanel.add(endDate);
        contentPanel.add(doneBtn);
        contentPanel.add(backBtn);

        JPanel tablePanel = new JPanel(new BorderLayout());
        String[] columns = {"Id", "Owner Name", "Vehicle Name", "Phone No", "NIC No", "Address", "Commission"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        tablePanel.add(new JScrollPane(table));

        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new ReportsUi();
            }
        });

        doneBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data = commissionService.getAllOwnersCommissionForJTable(startDate.getDate(), endDate.getDate());
                tableModel.setRowCount(0);
                for (String[] row : data) {
                    tableModel.addRow(row);
                }
                Double amount = monthlyReportDao.getAllTotalInFinalTotal();
                List<VehicleOwner> commission = bookingDao.totalCommission(startDate.getDate(), endDate.getDate());
                try {
                    new PDFGeneratorForCommissionHolders(table, "Commission-Report.pdf", amount, commission);
                    File file = new File("Commission-Report.pdf");
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

        frame.add(contentPanel, BorderLayout.NORTH);
        frame.add(tablePanel, BorderLayout.CENTER);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
