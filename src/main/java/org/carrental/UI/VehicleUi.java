package org.carrental.UI;

import org.carrental.Repository.VehicleDao;
import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Services.VehicleService;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VehicleUi {
    private final VehicleDao vehicleDao = new VehicleDao();
    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();
    private final VehicleService vehicleService = new VehicleService();

    public VehicleUi() {
        JFrame frame = new JFrame("Car Rental App - Customer Panel");
        JTextField search = new JTextField(30);
        JPanel tableAndSearchPanel = new JPanel(new BorderLayout());
        JButton activeBtn = createStyledButton("Book");
        JButton editBtn = createStyledButton("Edit");
        JButton deleteBtn = createStyledButton("Delete");
        JButton backBtn = createStyledButton("Back");
        JPanel btnPanel = new JPanel();

        tableAndSearchPanel.setBackground(Color.gray);

        String[][] data = vehicleService.getAllVehiclesForJTable();
        String[] column = {"Id", "Vehicle-Name", "Model", "Brand", "Color", "OwnerId", "BookingStatus"};
        DefaultTableModel dtm = new DefaultTableModel(data, column);
        JTable table = new JTable(dtm);

        JScrollPane sp = new JScrollPane(table);
        tableAndSearchPanel.add(search, BorderLayout.NORTH);
        tableAndSearchPanel.add(sp, BorderLayout.CENTER);

        activeBtn.addActionListener(e -> {
            Integer index = table.getSelectedRow();
            if (table != null && index != null && index >= 0 && index < table.getRowCount()) {
                String checkStatus = (String) table.getValueAt(index, 6);
                if (checkStatus == null || checkStatus.equalsIgnoreCase("") || checkStatus.equalsIgnoreCase("Available") || checkStatus.equalsIgnoreCase("UnActive")) {
                    String status = "Booked";
                    Long id = Long.valueOf((String) table.getValueAt(index, 0));
                    vehicleDao.activate(status, id);
                    frame.dispose();
                    new AddCustomerUi(id);
                } else if (checkStatus.equalsIgnoreCase("Booked")) {
                    JOptionPane.showMessageDialog(frame, "It's already Booked");
                }
            }
        });

        editBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                Long id = Long.valueOf((String) table.getValueAt(index, 0));
                String name = (String) table.getValueAt(index, 1);
                String model = (String) table.getValueAt(index, 2);
                String brand = (String) table.getValueAt(index, 3);
                String color = (String) table.getValueAt(index, 4);
                String ownerIdDropDown = (String) table.getValueAt(index, 5);
                frame.dispose();
                new UpdateVehicleUi(id, name, model, brand, color, ownerIdDropDown);
            } else {
                JOptionPane.showMessageDialog(frame, "Please Select Row");
            }
        });

        deleteBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                Long vId = Long.valueOf((String) table.getValueAt(index, 0));
                String oId = (String) table.getValueAt(index, 5);
                Boolean isPresent = Boolean.TRUE;
                isPresent = vehicleOwnerDao.checkOwnerIsAvailable(oId);
                if (isPresent) {
                    JOptionPane.showMessageDialog(frame, "Can not delete until its owner has an entry");
                } else {
                    vehicleDao.deleteByOnlyId(vId);
                    dtm.removeRow(index);
                }
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomeUi();
        });

        btnPanel.add(activeBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        frame.add(tableAndSearchPanel, BorderLayout.CENTER);
        frame.add(btnPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        search.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
                String[][] data = vehicleService.searchByName(search.getText());
                DefaultTableModel dtm = new DefaultTableModel(data, column);
                table.setModel(dtm);
            }
        });
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setOpaque(true);
        button.setBackground(Color.BLACK);

        // Set custom UI for the button
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(button.getBackground());
                g2.fillRect(0, 0, c.getWidth(), c.getHeight());
                super.paint(g2, c);
                g2.dispose();
            }
        });

        return button;
    }
}