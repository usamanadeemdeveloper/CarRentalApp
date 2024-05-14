package org.carrental.UI;

import org.carrental.Repository.VehicleOwnerDao;
import org.carrental.Services.VehicleOwnerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class VehicleOwnerUi {
    private final VehicleOwnerDao vehicleOwnerDao = new VehicleOwnerDao();
    private final VehicleOwnerService vehicleOwnerService = new VehicleOwnerService();

    public VehicleOwnerUi() {
        JFrame frame = new JFrame("Car Rental App - Vehicle Owner Panel");
        JTextField search = new JTextField(30);
        JPanel tableAndSearchPanel = new JPanel();
        JButton addBtn = createStyledButton("Add");
        JButton editBtn = createStyledButton("Edit");
        JButton deleteBtn = createStyledButton("Delete");
        JButton backBtn = createStyledButton("Back");
        JPanel btnPanel = new JPanel();
        tableAndSearchPanel.setBackground(Color.GRAY);

        String[][] data = vehicleOwnerService.getAllCustomerForJTable();
        String[] column = {"Id", "Owner-Name", "PhoneNo", "NicNo", "Address", "Commission"};
        DefaultTableModel dtm = new DefaultTableModel(data, column);
        JTable table = new JTable(dtm);
        table.setBounds(30, 40, 200, 300);
        tableAndSearchPanel.setLayout(new GridBagLayout());

        JScrollPane sp = new JScrollPane(table);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        tableAndSearchPanel.add(search, gbc);

        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        tableAndSearchPanel.add(sp, gbc);

        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        gbc.gridy = 2;
        tableAndSearchPanel.add(btnPanel, gbc);

        addBtn.addActionListener(e -> {
            frame.dispose();
            new AddVehicleOwnerUi();
        });

        editBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                Long id = Long.valueOf((String) table.getValueAt(index, 0));
                String ownerName = (String) table.getValueAt(index, 1);
                String phoneNo = (String) table.getValueAt(index, 2);
                String nicNo = (String) table.getValueAt(index, 3);
                String address = (String) table.getValueAt(index, 4);
                Float commission = Float.valueOf((String) table.getValueAt(index, 5));
                frame.dispose();
                new UpdateVehicleOwnerUi(id, ownerName, phoneNo, nicNo, address, commission);
            } else {
                JOptionPane.showMessageDialog(frame, "Please Select Row");
            }
        });

        deleteBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                Long id = Long.valueOf((String) table.getValueAt(index, 0));
                vehicleOwnerDao.deleteByOnlyId(id);
                dtm.removeRow(index);
            }
        });

        vehicleOwnerDao.setForeignKeyTo1();

        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomeUi();
        });

        frame.add(tableAndSearchPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                String[][] data = vehicleOwnerService.searchByName(search.getText());
                DefaultTableModel dtm = new DefaultTableModel(data, column);
                table.setModel(dtm);
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