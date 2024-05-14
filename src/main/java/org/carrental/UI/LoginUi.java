package org.carrental.UI;

import org.carrental.Services.AuthenticationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class LoginUi {

    private final static AuthenticationService authenticationService = new AuthenticationService();

    public LoginUi() {
        // Create the main frame
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        // Create the background panel
        JPanel backgroundPanel = new JPanel();
        backgroundPanel.setLayout(new BorderLayout());

        // Create the title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.DARK_GRAY);
        titlePanel.setPreferredSize(new Dimension(frame.getWidth(), 50));
        JLabel titleLabel = new JLabel("Car Rental App");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);

        // Create the content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);

        // Create the login components
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameTf = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginBtn = new JButton("Login");
        Action loginAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                if (authenticationService.checkLogin(usernameTf.getText(), password)) {
                    frame.dispose();
                    new HomeUi();
                } else {
                    JOptionPane.showMessageDialog(frame, "Wrong credentials");
                }
            }
        };

        // Map the Enter key to the login action for the root pane
        JRootPane rootPane = frame.getRootPane();
        rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "loginAction");
        rootPane.getActionMap().put("loginAction", loginAction);

        loginBtn.addActionListener(loginAction);

        // Add the components to the content panel using GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(usernameTf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPanel.add(loginBtn, gbc);

        // Add the panels to the background panel
        backgroundPanel.add(titlePanel, BorderLayout.NORTH);
        backgroundPanel.add(contentPanel, BorderLayout.CENTER);

        // Add the background panel to the frame
        frame.add(backgroundPanel);
        frame.setVisible(true);
    }
}
