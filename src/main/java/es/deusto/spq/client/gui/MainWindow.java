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
        this.setLayout(new FLowLayout());

        JMenuBar menuBar = new JMenuBar
        JMenu menu = new JMenu("Menu");
        JMenuItem menuItem = new JMenuItem("Cerrar Sesion");
        
        this.setJMenuBar(menuBar);
        menorBar.add(menu);
        menu.add(menuItem);
        
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.logout();
                this.setVisible(false);
                main.loginWindow.setVisible(true);
            }
        });
    }
}
