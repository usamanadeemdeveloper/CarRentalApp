package org.carrental.UI;

import org.carrental.Repository.BookingDao;
import org.carrental.Repository.VehicleDao;
import org.carrental.Services.BookingService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Date;

public class BookingUi {
    private final BookingService bookingService = new BookingService();
    private final BookingDao bookingDao = new BookingDao();
    private final VehicleDao vehicleDao = new VehicleDao();

    public BookingUi() {
        JFrame frame = new JFrame("Car Rental App - Booking Panel");
        JTextField search = new JTextField(30);
        JPanel tableAndSearchPanel = new JPanel();
        JButton editBookingBtn = createStyledButton("EditBooking");
        JButton deleteBookingBtn = createStyledButton("DeleteBooking");
        JButton completeBookingBtn = createStyledButton("CompleteBooking");
        JButton backBtn = createStyledButton("Back");
        JPanel btnPanel = new JPanel();
        tableAndSearchPanel.setBackground(Color.gray);

        String[][] data = bookingService.getAllCustomerForJTable();
        String column[] = {"Id", "CustomerId", "VehicleId", "BookingDate", "Price", "Status", "CompleteBooking"};
        DefaultTableModel dtm = new DefaultTableModel(data, column);
        JTable table = new JTable(dtm);

        tableAndSearchPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JScrollPane sp = new JScrollPane(table);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        tableAndSearchPanel.add(search, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        tableAndSearchPanel.add(sp, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 10, 10, 10);
        btnPanel.add(editBookingBtn);
        btnPanel.add(deleteBookingBtn);
        btnPanel.add(completeBookingBtn);
        btnPanel.add(backBtn);
        tableAndSearchPanel.add(btnPanel, gbc);

        frame.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints frameGbc = new GridBagConstraints();
        frameGbc.gridx = 0;
        frameGbc.gridy = 0;
        frameGbc.weightx = 1;
        frameGbc.weighty = 1;
        frameGbc.fill = GridBagConstraints.BOTH;
        frame.getContentPane().add(tableAndSearchPanel, frameGbc);

        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        search.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String[][] data = bookingService.searchByStatus(search.getText());
                DefaultTableModel dtm = new DefaultTableModel(data, column);
                table.setModel(dtm);
            }
        });

        editBookingBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                int id = Integer.parseInt(table.getValueAt(index, 0).toString());
                String customerDropDown = table.getValueAt(index, 1).toString();
                String vehicleDropDown = table.getValueAt(index, 2).toString();
                String calender = (String) table.getValueAt(index, 3);
                String price = table.getValueAt(index, 4).toString();
                frame.dispose();
                new UpdateBookingUi(id, customerDropDown, vehicleDropDown, price, Date.valueOf(calender));
            } else {
                JOptionPane.showMessageDialog(frame, "Please Select Row");
            }
        });

        deleteBookingBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                long id = Long.parseLong(table.getValueAt(index, 0).toString());
                String closeStatus = "Close";
                bookingDao.deleteById(closeStatus, id);
                bookingDao.deleteByOnlyId(id);
                dtm.removeRow(index);
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomeUi();
        });

        completeBookingBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                long id = Long.parseLong(table.getValueAt(index, 0).toString());
                String status = "Completed";
                frame.dispose();
                new CompleteBookingUi(id, status);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 30));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setOpaque(true);
        button.setBackground(Color.BLACK);
        return button;
    }
}