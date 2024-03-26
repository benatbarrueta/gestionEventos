package es.deusto.spq.client.gui;

import javax.swing.JFrame;

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
    }
}
