package org.carrental.UI;

import org.carrental.Services.VehicleService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ReportsUi {
    private final VehicleService vehicleService = new VehicleService();

    public ReportsUi() {
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel(new GridLayout(6, 1, 20, 20));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.BLACK);

        JButton monthlyReport = createButton("Monthly Report");
        JButton commissionReport = createButton("Commission Report");
        JButton carAvailabilityReport = createButton("Car Availability Report");
        JButton analyticsReport = createButton("Analytics Report");
        JButton yearlyReport = createButton("Yearly Report");
        JButton backBtn = createButton("Back");
        backBtn.setBackground(Color.RED);

        mainPanel.add(monthlyReport);
        mainPanel.add(commissionReport);
        mainPanel.add(carAvailabilityReport);
        mainPanel.add(analyticsReport);
        mainPanel.add(yearlyReport);
        mainPanel.add(backBtn);

        frame.getContentPane().setBackground(Color.BLACK);
        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        monthlyReport.addActionListener(e -> {
            frame.dispose();
            new MonthlyReportUi();
        });

        commissionReport.addActionListener(e -> {
            frame.dispose();
            new CommissionReportUi();
        });

        carAvailabilityReport.addActionListener(e -> {
            frame.dispose();
            new CarAvailabilityReportUi();
        });

        analyticsReport.addActionListener(e -> {
            frame.dispose();
            new AnalyticsReportUi();
        });

        yearlyReport.addActionListener(e -> {
            frame.dispose();
            new YearlyReportsUi();
        });
        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomeUi();
        });
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 60));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBackground(new Color(30, 30, 30)); // Darker background color
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(Color.WHITE, 2)); // Add LineBorder
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return button;
    }
}
