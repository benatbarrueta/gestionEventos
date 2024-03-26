package es.deusto.spq.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


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

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // TODO: Add your login logic here
                try {
                    URL url = new URL("http://tu-servidor/resource/login");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");

                    String input = "{\"email\":\"" + usernameLabel.getText() + "\",\"contrasenya\":\"" + passwordLabel.getText() + "\"}";

                    OutputStream os = conn.getOutputStream();
                    os.write(input.getBytes());
                    os.flush();

                    if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        System.out.println("Error al iniciar sesión. Código de respuesta: " + conn.getResponseCode());
                    } else {
                        System.out.println("Inicio de sesión exitoso.");
                        // Aquí puedes realizar acciones adicionales, como redirigir a otra ventana, etc.
                    }

                    conn.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

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
