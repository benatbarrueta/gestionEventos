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

    
    


