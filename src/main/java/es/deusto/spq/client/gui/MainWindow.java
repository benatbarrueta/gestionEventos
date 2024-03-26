package es.deusto.spq.client.gui;

import javax.swing.*;
import javax.swing.JMenuBar;

import es.deusto.spq.client.Main;

public class MainWindow extends JFrame{
    private static final long serialVersionUID = 1L;

    public MainWindow(Main main) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(false);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setTitle("Main Window");
       
        

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem logOutItem = new JMenuItem("Cerrar Sesion");
        
        this.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(logOutItem);
        

        logOutItem.addActionListener(e -> {
            main.logout();
            this.dispose();
            Main.loginWindow.setVisible(true);
        });
        
    }
}
