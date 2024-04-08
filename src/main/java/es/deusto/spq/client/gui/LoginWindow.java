package es.deusto.spq.client.gui;

import javax.swing.*;

import es.deusto.spq.client.Main;
import es.deusto.spq.server.jdo.TipoUsuario;

import java.awt.*;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class LoginWindow extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;
    private JLabel msgError;
    private JPanel error;

    public LoginWindow(Main exampleClient) {
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

        msgError = new JLabel("");
        msgError.setForeground(Color.RED);

        error = new JPanel(new FlowLayout());

        formularioPanel.add(usernameLabel);
        formularioPanel.add(usernameField);
        formularioPanel.add(passwordLabel);
        formularioPanel.add(passwordField);

        botoneraPanel.add(loginButton);
        botoneraPanel.add(registerButton);

        contentPane.add(formularioPanel, BorderLayout.CENTER);
        contentPane.add(botoneraPanel, BorderLayout.SOUTH);

        error.add(msgError);

        add(error, BorderLayout.NORTH);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                msgError.setText("");

                if(username.equals("") && password.equals("")){
                    msgError.setText("Introduce un usuario y contraseña");
                } else if (username.equals("") && !password.equals("")){
                    msgError.setText("Introduce un usuario");
                } else if (!username.equals("") && password.equals("")){
                    msgError.setText("Introduce una contraseña");
                } else {
                    if (exampleClient.loginUsuario(username, password).equals("CLIENTE".toString())) {
                        Main.mainWindowClient.setVisible(true);
                        Main.loginWindow.setVisible(false);
                        usernameField.setText("");
                        passwordField.setText("");
                    } else if (exampleClient.loginUsuario(username, password).equals("GERENTE".toString())) {
                        System.out.println(exampleClient.loginUsuario(username, password));
                        Main.mainWindowWorker.setVisible(true);
                        Main.loginWindow.setVisible(false);
                        usernameField.setText("");
                        passwordField.setText("");
                    } else {
                        msgError.setText("Usuario o contraseña incorrectos");
                        usernameField.setText("");
                        passwordField.setText("");
                    }
                }
            }
        });
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogoRegistro(exampleClient);
            }
        });

        passwordField.addKeyListener(new KeyListener(){

            @Override
            public void keyPressed(final java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    loginButton.doClick(); // Simular un clic en el botón de inicio de sesión
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                    
            }

            @Override
            public void keyTyped(java.awt.event.KeyEvent e) {
                
            }
        });
    }


    public void dialogoRegistro(Main exampleClient) {
        
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

        if (result == JOptionPane.OK_OPTION){
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");

            String nombre = nombreField.getText();
            String apellidos = apellidosField.getText();
            String nombreUsuario = nombreUsuarioField.getText();
            String contrasenya = new String(contrasenyaField.getPassword());
            String email = emailField.getText();
            String direccion = direccionField.getText();
            String telefono = telefonoField.getText();
            String f = fechaField.getText();
            Date fecha = null;
            try {
                fecha = formato.parse(f);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String dni = dniField.getText();

            exampleClient.registroUsuario(nombre, apellidos, nombreUsuario, contrasenya, email, direccion, telefono, TipoUsuario.CLIENTE, fecha, dni);
        }
    }
}
