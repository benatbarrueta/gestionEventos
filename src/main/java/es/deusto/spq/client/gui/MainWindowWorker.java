package es.deusto.spq.client.gui;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import es.deusto.spq.client.Main;

public class MainWindowWorker extends JFrame{

    private static final long serialVersionUID = 1L;

    public MainWindowWorker(Main main) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(false);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setTitle("Worker window");
       
        

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        JMenuItem logOutItem = new JMenuItem("Cerrar Sesion");
        JMenuItem verEventosItem = new JMenuItem("Ver Eventos");
        
        this.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(verEventosItem);
        menu.add(logOutItem);
        
        verEventosItem.addActionListener(e -> {
            this.dispose();
            Main.eventoWindow.setVisible(true);
        });

        logOutItem.addActionListener(e -> {
            main.logout();
            this.dispose();
            Main.loginWindow.setVisible(true);
        });
    }
}