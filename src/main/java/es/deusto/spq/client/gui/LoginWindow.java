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
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        

        loginButton = new JButton("Login");
        registerButton = new JButton("Registro");

        formularioPanel.add(usernameLabel);
        formularioPanel.add(usernameField);
        formularioPanel.add(passwordLabel);
        formularioPanel.add(passwordField);

        formularioPanel.setPreferredSize(new Dimension(450, 100));

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
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogoRegistro();
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
        JTextField fechaField = new JTextField();
        JTextField dniField = new JTextField();

        panel.add(new JLabel("Nombre:"));
        panel.add(nombreField);
        panel.add(new JLabel("Apellidos:"));
        panel.add(apellidosField);
        panel.add(new JLabel("Nombre de usuario:"));
        panel.add(nombreUsuarioField);
        panel.add(new JLabel("Contraseña:"));
        panel.add(contrasenyaField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Dirección:"));
        panel.add(direccionField);
        panel.add(new JLabel("Teléfono:"));
        panel.add(telefonoField);
        panel.add(new JLabel("Fecha de nacimiento(yyyy-mm-dd):"));
        panel.add(fechaField);
        panel.add(new JLabel("DNI:"));
        panel.add(dniField);

        panel.setPreferredSize(new Dimension(450, 200));

        int result = JOptionPane.showConfirmDialog(null, panel, "Registro", JOptionPane.OK_CANCEL_OPTION);
    }
}
