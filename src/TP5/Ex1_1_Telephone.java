package TP5;

import java.awt.*;

/**
 * Exercice 1.1 - Grille de boutons poussoirs (1-9, Bis, 0, Reset)
 * Layout: GridLayout 4x3
 */
public class Ex1_1_Telephone extends Frame {

    public Ex1_1_Telephone() {
        super("Untitled");

        // GridLayout 4 lignes, 3 colonnes
        setLayout(new GridLayout(4, 3));

        // Ligne 1 : 1, 2, 3
        add(new Button("1"));
        add(new Button("2"));
        add(new Button("3"));

        // Ligne 2 : 4, 5, 6
        add(new Button("4"));
        add(new Button("5"));
        add(new Button("6"));

        // Ligne 3 : 7, 8, 9
        add(new Button("7"));
        add(new Button("8"));
        add(new Button("9"));

        // Ligne 4 : Bis, 0, Reset
        add(new Button("Bis"));
        add(new Button("0"));
        add(new Button("Reset"));

        setSize(300, 250);
        setVisible(true);

       
        // Fermeture de la fenêtre
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new Ex1_1_Telephone();
    }
}
