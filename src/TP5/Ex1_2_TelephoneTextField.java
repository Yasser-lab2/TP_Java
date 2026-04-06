package TP5;

import java.awt.*;

/**
 * Exercice 1.2 - TextField en haut (NORTH) + grille de boutons (CENTER)
 * BorderLayout : TextField au Nord, Panel de boutons au Centre
 */
public class Ex1_2_TelephoneTextField extends Frame {

    public Ex1_2_TelephoneTextField() {
        super("Untitled");

        // BorderLayout par défaut sur Frame
        setLayout(new BorderLayout());

        // Zone de saisie en haut (NORTH)
        TextField tf = new TextField();
        add(tf, BorderLayout.NORTH);

        // Panel contenant les boutons en grille
        Panel panelBoutons = new Panel();
        panelBoutons.setLayout(new GridLayout(4, 3));

        panelBoutons.add(new Button("1"));
        panelBoutons.add(new Button("2"));
        panelBoutons.add(new Button("3"));

        panelBoutons.add(new Button("4"));
        panelBoutons.add(new Button("5"));
        panelBoutons.add(new Button("6"));

        panelBoutons.add(new Button("7"));
        panelBoutons.add(new Button("8"));
        panelBoutons.add(new Button("9"));

        panelBoutons.add(new Button("Bis"));
        panelBoutons.add(new Button("0"));
        panelBoutons.add(new Button("Reset"));

        add(panelBoutons, BorderLayout.CENTER);

        setSize(300, 280);
        setVisible(true);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Ex1_2_TelephoneTextField();
    }
}
