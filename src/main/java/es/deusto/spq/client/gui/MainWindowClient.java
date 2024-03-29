package es.deusto.spq.client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.List;

import javax.swing.*;

import es.deusto.spq.client.Main;
import es.deusto.spq.server.jdo.Entrada;
import es.deusto.spq.server.jdo.Evento;
import es.deusto.spq.server.jdo.SectoresEvento;

public class MainWindowClient extends JFrame{
    private static final long serialVersionUID = 1L;
    
    public JMenuBar menuBar;
    public JMenu menu;
    public JMenuItem logOutItem;
    public JList<String> eventoList;
    public static DefaultListModel<String> eventoListModel;
    public JList<String> entradasList;
    public DefaultListModel<String> entradasListModel;

    public MainWindowClient(Main main) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(false);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setTitle("Client window");

        Container cp = this.getContentPane();

        JPanel principal = new JPanel(new GridLayout(1, 2));

        JPanel eventoPanel = new JPanel(new BorderLayout());
        JPanel entradasPanel = new JPanel(new BorderLayout());

        JPanel tituloEventoPanel = new JPanel(new FlowLayout());
        JPanel tituloEntradasPanel = new JPanel(new FlowLayout());

        JPanel panelNorte = new JPanel(new GridLayout(1, 2));

        tituloEventoPanel.add(new JLabel("EVENTOS DISPONIBLES"));
        tituloEntradasPanel.add(new JLabel("MIS ENTRADAS"), BorderLayout.NORTH);

        panelNorte.add(tituloEventoPanel);
        panelNorte.add(tituloEntradasPanel);

        eventoListModel = new DefaultListModel<>();
        eventoList = new JList<>(eventoListModel);

        JScrollPane scrollPane = new JScrollPane(eventoList);
        eventoPanel.add(scrollPane, BorderLayout.CENTER);

        entradasListModel = new DefaultListModel<>();
        entradasList = new JList<>(entradasListModel);

        JScrollPane scrollPaneEntradas = new JScrollPane(entradasList);
        entradasPanel.add(scrollPaneEntradas, BorderLayout.CENTER);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {

                List<Evento> eventos = main.getEventos();
                if(eventos != null && eventos.size() > 0){
                    for (Evento evento : eventos) {
                        eventoListModel.addElement(evento.toStringCorto());
                    }
                } else {;
                    eventoListModel.addElement("No hay eventos disponibles");
                }

                List<Entrada> entradas = main.getEntradas();

                if(entradas != null && entradas.size() > 0){
                    for (Entrada entrada : entradas) {
                        if(entrada.getUsuario().getDni().equals(Main.user.getDni())) {
                            entradasListModel.addElement(entrada.toStringCorto());   
                        }
                    }
                }
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
                eventoListModel.removeAllElements();
                entradasListModel.removeAllElements();
            }
        });

        JPanel botonera = new JPanel(new FlowLayout());
        JButton comprarEntrada = new JButton("Comprar Entrada");
        JButton eliminarEntrada = new JButton("Eliminar Entrada");

        botonera.add(comprarEntrada);
        botonera.add(eliminarEntrada);

        principal.add(eventoPanel);
        principal.add(entradasPanel);

        cp.add(panelNorte, BorderLayout.NORTH);
        cp.add(principal, BorderLayout.CENTER);
        cp.add(botonera, BorderLayout.SOUTH);
        
        comprarEntrada.addActionListener(e -> {
            dialogoNewEntrada(main);
        });

        eliminarEntrada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int index = entradasList.getSelectedIndex();
                if (index != -1) {
                    String entrada = entradasListModel.getElementAt(index);
                    String[] parts = entrada.split(" ");
                    String id = parts[3];
                    List<Entrada> entradas = main.getEntradas();
                    for (Entrada entrad : entradas) {
                        if (entrad.getId() == Integer.parseInt(id)) {
                            main.eliminarEntrada(entrad);
                            entradasListModel.remove(index);
                        }
                    }
                }
                
            }
        
        });

        menuBar = new JMenuBar();
        menu = new JMenu("Menu");
        logOutItem = new JMenuItem("Cerrar Sesion");
        
        this.setJMenuBar(menuBar);
        menuBar.add(menu);
        menu.add(logOutItem);
        

        logOutItem.addActionListener(e -> {
            main.logout();
            this.dispose();
            Main.loginWindow.setVisible(true);
        });
        
    }

    public void dialogoNewEntrada(Main main){

        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField idEvento = new JTextField();
        JSpinner numeroEntradas = new JSpinner();
        JComboBox<SectoresEvento> sectorComboBox = new JComboBox<>(SectoresEvento.values());

        panel.add(new JLabel("ID del evento: "));
        panel.add(idEvento);
        panel.add(new JLabel("Numero de entradas: "));
        panel.add(numeroEntradas);
        panel.add(new JLabel("Sector: "));
        panel.add(sectorComboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Comprar Entrada", JOptionPane.OK_CANCEL_OPTION);

        if(result == JOptionPane.OK_OPTION){
            List<Evento> eventos = main.getEventos();

            for(Evento evento: eventos) {
                if(evento.getId() == Integer.parseInt(idEvento.getText())) {
                    for (int i = 0; i < (int) numeroEntradas.getValue(); i++) {
                        SectoresEvento seleccionado = (SectoresEvento) sectorComboBox.getSelectedItem();

                        int aforo = evento.getAforo() - (int) numeroEntradas.getValue();
                        evento.setAforo(aforo);

                        int numEntradas = evento.getEntradasSector().get(sectorComboBox.getSelectedItem()) - (int) numeroEntradas.getValue();
                        evento.getEntradasSector().remove(seleccionado);
                        evento.getEntradasSector().put(seleccionado, numEntradas);

                        main.comprarEntrada(evento, Main.user, seleccionado, evento.getPrecioSector().get(seleccionado));
                        entradasListModel.addElement(main.getEntradas().get(main.getEntradas().size() - 1).toStringCorto());
                    }
                }
            }
        }
    }
}
