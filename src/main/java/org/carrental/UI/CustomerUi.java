package org.carrental.UI;

import org.carrental.Repository.CustomerDao;
import org.carrental.Services.CustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CustomerUi {
    private final CustomerService customerService = new CustomerService();
    private final CustomerDao customerDao = new CustomerDao();

    public CustomerUi() {
        JFrame frame = new JFrame("Car Rental App - Customer Panel");
        JTextField search = new JTextField(30);
        JPanel tableAndSearchPanel = new JPanel();
        JButton editBtn = createStyledButton("Edit");
        JButton deleteBtn = createStyledButton("Delete");
        JButton backBtn = createStyledButton("Back");
        JPanel btnPanel = new JPanel();
        tableAndSearchPanel.setBackground(Color.gray);

        String[][] data = customerService.getAllActiveCustomerForJTable();
        String column[] = {"Id", "Customer-Name", "Phone-No", "Nic-No", "Address", "Reference-Phone-No", "Status"};
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
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
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
                String[][] data = customerService.searchByName(search.getText());
                DefaultTableModel dtm = new DefaultTableModel(data, column);
                table.setModel(dtm);
            }
        });

        editBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index > -1) {
                Long id = Long.valueOf((String) table.getValueAt(index, 0));
                String name = (String) table.getValueAt(index, 1);
                String phoneNo = (String) table.getValueAt(index, 2);
                String nicNo = (String) table.getValueAt(index, 3);
                String address = (String) table.getValueAt(index, 4);
                String referencePhoneNo = (String) table.getValueAt(index, 5);
                String status = (String) table.getValueAt(index, 6);
                frame.dispose();
                new UpdateCustomerUi(id, name, phoneNo, nicNo, address, referencePhoneNo, status);
            } else {
                JOptionPane.showMessageDialog(frame, "Please Select Row To Edit");
            }
        });

        deleteBtn.addActionListener(e -> {
            int index = table.getSelectedRow();
            if (index >= 0) {
                Long id = Long.valueOf((String) table.getValueAt(index, 0));
                Boolean present = customerDao.checkIfCustomerIdIsPresent(id);
                if (present) {
                    JOptionPane.showMessageDialog(frame, "Customer can't be deleted until he has a booking");
                } else {
                    String status = "Close";
                    customerDao.deleteById(status, id);
                    dtm.removeRow(index);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please Select Row To Delete");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomeUi();
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