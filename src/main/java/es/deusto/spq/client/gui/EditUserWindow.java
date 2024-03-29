package es.deusto.spq.client.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import es.deusto.spq.client.Main;


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

        JPanel UsuarioPanel = new JPanel(new GridLayout(0,2));

        

        JTextField nombreTextField = new JTextField();
        JTextField apellidosTextField = new JTextField();
        JTextField nombreUsuarioTextField = new JTextField();   
        JTextField contrasenyaTextField = new JTextField();
        JTextField emailTextField  = new JTextField();
        JTextField direccionTextField = new JTextField();
        JTextField telefonoTextField = new JTextField();
        JTextField fechaNacimientoTextField = new JTextField();
        JTextField dniTextField = new JTextField();

        UsuarioPanel.add(new JLabel("Nombre:"));
        UsuarioPanel.add(nombreTextField);
        UsuarioPanel.add(new JLabel("Apellidos:"));
        UsuarioPanel.add(apellidosTextField);
        UsuarioPanel.add(new JLabel("UserName:"));
        UsuarioPanel.add(nombreUsuarioTextField);
        UsuarioPanel.add(new JLabel("Contraseña:"));
        UsuarioPanel.add(contrasenyaTextField);
        UsuarioPanel.add(new JLabel("Email:"));
        UsuarioPanel.add(emailTextField);
        UsuarioPanel.add(new JLabel("Direccion:"));
        UsuarioPanel.add(direccionTextField);
        UsuarioPanel.add(new JLabel("Telefono:"));
        UsuarioPanel.add(telefonoTextField);
        UsuarioPanel.add(new JLabel("Fecha de Nacimiento (AAAA-mm-dd):"));
        UsuarioPanel.add(fechaNacimientoTextField);
        UsuarioPanel.add(new JLabel("DNI:"));
        UsuarioPanel.add(dniTextField);

        cp.add(UsuarioPanel, BorderLayout.CENTER);




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
            Main.eventoWindow.setVisible(false);
            Main.loginWindow.setVisible(true);
        });

        modificarCuenta.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                Main.editUserWindow.setVisible(false);
                Main.editUserWindow.dispose();
                Main.mainWindowClient.setVisible(true);
            }
        });

        eliminarCuenta.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                dialogoEliminarCuenta(main);
            }
        }); 

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

    
    


