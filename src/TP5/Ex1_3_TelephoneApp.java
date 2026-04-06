package TP5;
import java.awt.*;
import java.awt.event.*;

/**
 * Exercice 1.3 - Application Téléphone avec gestion des événements
 *
 * - Clic sur chiffre  → le chiffre s'affiche dans le TextField
 * - Clic sur Reset    → efface le TextField
 * - Clic sur Bis      → réaffiche les chiffres précédemment saisis
 */
public class Ex1_3_TelephoneApp extends Frame implements ActionListener {

    private TextField display;
    private String lastSaisie = ""; // mémorise la dernière saisie pour Bis

    public Ex1_3_TelephoneApp() {
        super("Téléphone");

        setLayout(new BorderLayout());

        // Zone d'affichage en haut
        display = new TextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Panel de boutons
        Panel panel = new Panel(new GridLayout(4, 3));

        String[] labels = {"1","2","3","4","5","6","7","8","9","Bis","0","Reset"};

        for (String label : labels) {
            Button btn = new Button(label);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);

        setSize(300, 280);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Reset":
                // Sauvegarder avant d'effacer (pour Bis)
                lastSaisie = display.getText();
                display.setText("");
                break;

            case "Bis":
                // Réafficher la dernière saisie
                display.setText(lastSaisie);
                break;

            default:
                // C'est un chiffre → on l'ajoute à l'affichage
                display.setText(display.getText() + cmd);
                break;
        }
    }

    public static void main(String[] args) {
        new Ex1_3_TelephoneApp();
    }
}
