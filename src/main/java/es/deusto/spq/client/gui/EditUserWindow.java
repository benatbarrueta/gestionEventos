package es.deusto.spq.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import es.deusto.spq.client.Main;
import es.deusto.spq.server.jdo.Entrada;
import es.deusto.spq.server.jdo.Usuario;

import java.util.List;


public class EditUserWindow extends JFrame{
    
    public static final long serialVersionUID = 1L;
    public JButton modificarCuentaButton;
    public JButton eliminarCuentaButton;
    public JButton volverButton;

    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem inicioItem;
    public JMenuItem logOutItem;

    public JPanel usuarioPanel;

    public JTextField nombreTextField;
    public JTextField apellidosTextField;
    public JTextField nombreUsuarioTextField;
    public JTextField contrasenyaTextField;
    public JTextField emailTextField;
    public JTextField direccionTextField;
    public JTextField telefonoTextField;
    public JTextField fechaNacimientoTextField;
    public JTextField dniTextField;

    public EditUserWindow(Main main){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(false);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        this.setTitle("Edit User");

        
        Container cp = this.getContentPane();

        usuarioPanel = new JPanel(new GridLayout(0,2));

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
                String fechaFormateada = formato.format(Main.user.getFechaNacimiento());

                nombreTextField = new JTextField(Main.user.getNombre());
                apellidosTextField = new JTextField(Main.user.getApellidos());
                nombreUsuarioTextField = new JTextField(Main.user.getNombreUsuario());   
                contrasenyaTextField = new JPasswordField(Main.user.getContrasenya());
                emailTextField  = new JTextField(Main.user.getEmail());
                direccionTextField = new JTextField(Main.user.getDireccion());
                telefonoTextField = new JTextField(Main.user.getTelefono());
                fechaNacimientoTextField = new JTextField(fechaFormateada);

                usuarioPanel.add(new JLabel("Nombre:"));
                usuarioPanel.add(nombreTextField);
                usuarioPanel.add(new JLabel("Apellidos:"));
                usuarioPanel.add(apellidosTextField);
                usuarioPanel.add(new JLabel("UserName:"));
                usuarioPanel.add(nombreUsuarioTextField);
                usuarioPanel.add(new JLabel("Contraseña:"));
                usuarioPanel.add(contrasenyaTextField);
                usuarioPanel.add(new JLabel("Email:"));
                usuarioPanel.add(emailTextField);
                usuarioPanel.add(new JLabel("Direccion:"));
                usuarioPanel.add(direccionTextField);
                usuarioPanel.add(new JLabel("Telefono:"));
                usuarioPanel.add(telefonoTextField);
                usuarioPanel.add(new JLabel("Fecha de Nacimiento (yyyy-mm-dd):"));
                usuarioPanel.add(fechaNacimientoTextField);
            }
        });

        JPanel botoneraPanel = new JPanel(new FlowLayout());
        

        modificarCuentaButton = new JButton("Modificar Cuenta");
        eliminarCuentaButton = new JButton("Eliminar Cuenta");
        volverButton = new JButton("Volver");

        botoneraPanel.add(modificarCuentaButton);
        botoneraPanel.add(eliminarCuentaButton);
        botoneraPanel.add(volverButton);

        cp.add(usuarioPanel, BorderLayout.CENTER);

              

        cp.add(botoneraPanel, BorderLayout.SOUTH);

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        inicioItem = new JMenuItem("Inicio");
        logOutItem = new JMenuItem("Cerrar Sesion");

        this.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(inicioItem);
        menu.add(logOutItem);
        
        inicioItem.addActionListener(e -> {
            Main.editUserWindow.setVisible(false);
            Main.mainWindowClient.setVisible(true);
        });

        logOutItem.addActionListener(e -> {
            main.logout();
            Main.editUserWindow.setVisible(false);
            Main.loginWindow.setVisible(true);
        });

        modificarCuentaButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                dialogoEditarCuenta(main);
            }
        });

        eliminarCuentaButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                dialogoEliminarCuenta(main);
            }
        }); 

        volverButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                Main.editUserWindow.setVisible(false);
                Main.mainWindowClient.setVisible(true);
            }
        });

    }
    public void dialogoEditarCuenta(Main main){
        JPanel confirmar = new JPanel(new GridLayout(0,1));

        confirmar.add(new JLabel("¿Estás seguro de que quieres modificar tu cuenta con los siguientes datos?"));
        confirmar.add(new JLabel(""));
        confirmar.add(new JLabel("Nombre: " + nombreTextField.getText()));
        confirmar.add(new JLabel("Apellidos: " + apellidosTextField.getText()));
        confirmar.add(new JLabel("Nombre de Usuario: " + nombreUsuarioTextField.getText()));
        confirmar.add(new JLabel("Contraseña: " + new String(((JPasswordField) contrasenyaTextField).getPassword())));
        confirmar.add(new JLabel("Email: " + emailTextField.getText()));
        confirmar.add(new JLabel("Direccion: " + direccionTextField.getText()));
        confirmar.add(new JLabel("Telefono: " + telefonoTextField.getText()));
        confirmar.add(new JLabel("Fecha de Nacimiento: " + fechaNacimientoTextField.getText()));

        confirmar.setPreferredSize(new Dimension(450, 170));

        int result = JOptionPane.showConfirmDialog(null, confirmar, "Aceptar cambios", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String nombre = nombreTextField.getText();
            String apellidos = apellidosTextField.getText();
            String nombreUsuario = nombreUsuarioTextField.getText();
            String contrasenya = new String(((JPasswordField) contrasenyaTextField).getPassword());
            String email = emailTextField.getText();
            String direccion = direccionTextField.getText();
            String telefono = telefonoTextField.getText();
            String f = fechaNacimientoTextField.getText();
            Date fecha = null;
            try {
                fecha = format.parse(f);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            


            Usuario usuario = new Usuario(nombre, apellidos, nombreUsuario, contrasenya, email, direccion, telefono, Main.user.getRol(), fecha, Main.user.getDni());
            main.editarUsuario(usuario);
        }

    }

    public void dialogoEliminarCuenta(Main main){
        JPanel panel = new JPanel(new GridLayout(1,1));

        panel.add(new JLabel("¿Estás seguro de que quieres eliminar tu cuenta?"));
        
        panel.setPreferredSize(new Dimension(300, 100));

        int result = JOptionPane.showConfirmDialog(null, panel, "Eliminar Cuenta", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            List<Entrada> entradas = main.getEntradas();
                for(Entrada entrada : entradas){
                    if(entrada.getUsuario().getDni().equals(Main.user.getDni())){
                        main.eliminarEntrada(entrada);
                    }
                }
            main.eliminarCuenta();
            this.setVisible(false);
            Main.loginWindow.setVisible(true);
        }
        
        }


}

    
    


