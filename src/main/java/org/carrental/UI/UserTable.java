package org.carrental.UI;

import org.carrental.Services.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserTable {

    private final UserService userService = new UserService();
    public UserTable() {
        JFrame frame = new JFrame("Car Rental App - Customer Panel");
        JTextField search = new JTextField(30);
        JPanel tableAndSearchPanel = new JPanel();
        JButton editBtn = new JButton("Edit");
        JButton deleteBtn = new JButton("Delete");
        JButton backBtn = new JButton("Back");
        JPanel btnPanel = new JPanel();
        tableAndSearchPanel.setBackground(Color.gray);


        String[][] data = userService.getAllForJTable();

        String column[] = {"Id","User-Name"};

        DefaultTableModel dtm = new DefaultTableModel(data,column);

        JTable table = new JTable(dtm);

        table.setBounds(30,40,200,300);
        tableAndSearchPanel.setLayout(new BorderLayout());

        JScrollPane sp = new JScrollPane(table);

        frame.setLayout(new FlowLayout(FlowLayout.LEFT));
        tableAndSearchPanel.add(search,BorderLayout.NORTH);
        tableAndSearchPanel.add(sp);
        btnPanel.add(editBtn);
        btnPanel.add(deleteBtn);
        btnPanel.add(backBtn);

        backBtn.addActionListener(e -> {
            frame.dispose();
            new HomeUi();
        });

        tableAndSearchPanel.add(btnPanel,BorderLayout.SOUTH);
        frame.add(tableAndSearchPanel);
        frame.setSize(2000,750);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

    }
}
