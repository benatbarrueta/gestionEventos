package es.deusto.spq.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import es.deusto.spq.client.Main;
import es.deusto.spq.server.jdo.Usuario;


public class EditUserWindow extends JFrame{
    
    public static final long serialVersionUID = 1L;
    public JButton modificarCuenta;
    public JButton eliminarCuenta;
    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem inicioItem;
    public JMenuItem logOutItem;

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
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setTitle("Edit User");

        
        Container cp = this.getContentPane();

        JPanel botonerPanel = new JPanel(new FlowLayout());

        modificarCuenta = new JButton("Modificar Cuenta");
        eliminarCuenta = new JButton("Eliminar Cuenta");

        botonerPanel.add(modificarCuenta);
        botonerPanel.add(eliminarCuenta);

        cp.add(botonerPanel, BorderLayout.SOUTH);

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

        modificarCuenta.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                dialogoEditarCuenta(main);
            }
        });

        eliminarCuenta.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                dialogoEliminarCuenta(main);
            }
        }); 

    }
    public void dialogoEditarCuenta(Main main){
        JPanel usuarioPanel = new JPanel(new GridLayout(0,2));

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String fechaFormateada = formato.format(Main.user.getFechaNacimiento());

        JTextField nombreTextField = new JTextField(Main.user.getNombre());
        JTextField apellidosTextField = new JTextField(Main.user.getApellidos());
        JTextField nombreUsuarioTextField = new JTextField(Main.user.getNombreUsuario());   
        JTextField contrasenyaField = new JPasswordField(Main.user.getContrasenya());
        JTextField emailTextField  = new JTextField(Main.user.getEmail());
        JTextField direccionTextField = new JTextField(Main.user.getDireccion());
        JTextField telefonoTextField = new JTextField(Main.user.getTelefono());
        JTextField fechaNacimientoTextField = new JTextField(fechaFormateada);
        

        usuarioPanel.add(new JLabel("Nombre:"));
        usuarioPanel.add(nombreTextField);
        usuarioPanel.add(new JLabel("Apellidos:"));
        usuarioPanel.add(apellidosTextField);
        usuarioPanel.add(new JLabel("UserName:"));
        usuarioPanel.add(nombreUsuarioTextField);
        usuarioPanel.add(new JLabel("Contraseña:"));
        usuarioPanel.add(contrasenyaField);
        usuarioPanel.add(new JLabel("Email:"));
        usuarioPanel.add(emailTextField);
        usuarioPanel.add(new JLabel("Direccion:"));
        usuarioPanel.add(direccionTextField);
        usuarioPanel.add(new JLabel("Telefono:"));
        usuarioPanel.add(telefonoTextField);
        usuarioPanel.add(new JLabel("Fecha de Nacimiento (AAAA-mm-dd):"));
        usuarioPanel.add(fechaNacimientoTextField);
        

        usuarioPanel.setPreferredSize(new Dimension(350, 200));

        int restult = JOptionPane.showConfirmDialog(null, usuarioPanel, "Editar Cuenta", JOptionPane.OK_CANCEL_OPTION);

        if(restult == JOptionPane.OK_OPTION){
             SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            String nombre = nombreTextField.getText();
            String apellidos = apellidosTextField.getText();
            String nombreUsuario = nombreUsuarioTextField.getText();
            String contrasenya = new String(((JPasswordField) contrasenyaField).getPassword());
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
            


            Usuario usuario = new Usuario(nombre, apellidos, nombreUsuario, contrasenya, email, direccion, telefono, Main.user.getRol(), fecha, null);
            main.editarCuenta(usuario);
        }

    }

    public void dialogoEliminarCuenta(Main main){
        JPanel panel = new JPanel(new GridLayout(1,1));


        panel.add(new JLabel("¿Estás seguro de que quieres eliminar tu cuenta?"));
        
        

        panel.setPreferredSize(new Dimension(300, 100));

        int result = JOptionPane.showConfirmDialog(null, panel, "Eliminar Cuenta", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            main.eliminarCuenta();
            this.setVisible(false);
            Main.loginWindow.setVisible(true);
        }
        
        }


}

    
    


