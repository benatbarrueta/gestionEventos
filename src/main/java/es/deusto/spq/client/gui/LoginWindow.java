package es.deusto.spq.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(false);
        this.setSize(400, 300);
        this.setLocationRelativeTo(null);
        this.setTitle("Login");
        
        Container contentPane = this.getContentPane();

        JPanel formularioPanel = new JPanel(new GridLayout(2, 2));
        JPanel botoneraPanel = new JPanel(new FlowLayout());

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 20));
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));

        

        loginButton = new JButton("Login");
        registerButton = new JButton("Registro");

        formularioPanel.add(usernameLabel);
        formularioPanel.add(usernameField);
        formularioPanel.add(passwordLabel);
        formularioPanel.add(passwordField);

        botoneraPanel.add(loginButton);
        botoneraPanel.add(registerButton);

        contentPane.add(formularioPanel, BorderLayout.CENTER);
        contentPane.add(botoneraPanel, BorderLayout.SOUTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // TODO: Add your login logic here

                // For now, let's just display the entered username and password
                JOptionPane.showMessageDialog(LoginWindow.this, "Username: " + username + "\nPassword: " + password);
            }
        });
    }

    public void dialogoRegistro() {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField nombreField = new JTextField();
        JTextField apellidosField = new JTextField();
        JTextField nombreUsuarioField = new JTextField();
        JPasswordField contrasenyaField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField direccionField = new JTextField();
        JTextField telefonoField = new JTextField();
        JTextField dniField = new JTextField();
    }
}
