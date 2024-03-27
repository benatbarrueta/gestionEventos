package es.deusto.spq.client.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import es.deusto.spq.client.Main;
import es.deusto.spq.server.jdo.SectoresEvento;

public class EventoWindow extends JFrame{

    public static final long serialVersionUID = 1L;
    public JButton anyadirEvento;
    public JButton eliminarEvento;

    public EventoWindow(Main main) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(false);
        this.setSize(1000, 800);
        this.setLocationRelativeTo(null);
        this.setTitle("Evento");

        Container cp = this.getContentPane();

        JPanel botoneraPanel = new JPanel(new FlowLayout());

        anyadirEvento = new JButton("Añadir Evento");
        eliminarEvento = new JButton("Eliminar Evento");

        botoneraPanel.add(anyadirEvento);
        botoneraPanel.add(eliminarEvento);

        cp.add(botoneraPanel, BorderLayout.SOUTH);

        anyadirEvento.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dialogoNewEvento(main);
            }
        });

    }

    public void dialogoNewEvento(Main main) {
        JPanel panel = new JPanel(new GridLayout(0, 2));

        JTextField nombreField = new JTextField();
        JTextField lugarField = new JTextField();
        JTextField fechaField = new JTextField();
        JTextField horaField = new JTextField();
        JTextField descripcionField = new JTextField();
        JSpinner aforoField = new JSpinner();
        JTextField organizadorField = new JTextField();
        JRadioButton pistaButton = new JRadioButton("PISTA (60€)");
        JRadioButton frontStageButton = new JRadioButton("FRONT STAGE (80€)");
        JRadioButton gradaAltaButton = new JRadioButton("GRADA ALTA (20€)");
        JRadioButton gradaMediaButton = new JRadioButton("GRADA MEDIA (30€)");
        JRadioButton gradaBajaButton = new JRadioButton("GRADA BAJA (40€)");
        JRadioButton VIPButton = new JRadioButton("VIP (100€)");

        panel.add(new JLabel("Nombre"));
        panel.add(nombreField);
        panel.add(new JLabel("Lugar"));
        panel.add(lugarField);
        panel.add(new JLabel("Fecha(yyyy-MM-dd)"));
        panel.add(fechaField);
        panel.add(new JLabel("Hora(HH:mm)"));
        panel.add(horaField);
        panel.add(new JLabel("Descripcion"));
        panel.add(descripcionField);
        panel.add(new JLabel("Aforo"));
        panel.add(aforoField);
        panel.add(new JLabel("Organizador"));
        panel.add(organizadorField);
        panel.add(new JLabel("Sector"));
        panel.add(new JLabel(""));
        panel.add(pistaButton);
        panel.add(frontStageButton);
        panel.add(gradaAltaButton);
        panel.add(gradaMediaButton);
        panel.add(gradaBajaButton);
        panel.add(VIPButton);

        panel.setPreferredSize(new Dimension(350, 200));

        int result = JOptionPane.showConfirmDialog(null, panel, "Nuevo Evento", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd:HH:mm");

            String nombre = nombreField.getText();
            String lugar = lugarField.getText();
            String fecha = fechaField.getText();
            String hora = horaField.getText();

            Date fechaHora = null;
            try {
                String fechaHoraString = fecha + ":" + hora;
                fechaHora = formato.parse(fechaHoraString);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String descripcion = descripcionField.getText();
            int aforo = (int) aforoField.getValue();
            String organizador = organizadorField.getText();
            ArrayList<SectoresEvento> sectores = new ArrayList<SectoresEvento>();
            HashMap<SectoresEvento, Integer> precioSectores = new HashMap<SectoresEvento, Integer>();
            if (pistaButton.isSelected()) {
                sectores.add(SectoresEvento.PISTA);
                precioSectores.put(SectoresEvento.PISTA, 60);
            } else if(frontStageButton.isSelected()) {
                sectores.add(SectoresEvento.FRONT_STAGE);
                precioSectores.put(SectoresEvento.FRONT_STAGE, 80);
            } else if(gradaAltaButton.isSelected()) {
                sectores.add(SectoresEvento.GRADA_ALTA);
                precioSectores.put(SectoresEvento.GRADA_ALTA, 20);
            } else if(gradaMediaButton.isSelected()) {
                sectores.add(SectoresEvento.GRADA_MEDIA);
                precioSectores.put(SectoresEvento.GRADA_MEDIA, 30);
            } else if(gradaBajaButton.isSelected()) {
                sectores.add(SectoresEvento.GRADA_BAJA);
                precioSectores.put(SectoresEvento.GRADA_BAJA, 40);
            } else if(VIPButton.isSelected()) {
                sectores.add(SectoresEvento.VIP);
                precioSectores.put(SectoresEvento.VIP, 100);
            }

            main.newEvento(nombre, lugar, fechaHora, descripcion, aforo, precioSectores, organizador, sectores);
        }
    }
}
