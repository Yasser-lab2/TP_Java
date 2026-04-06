package TP5;
import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;

/**
 * Exercice 1.4 - Application Téléphone transformée en Applet
 *
 * Pour lancer : utiliser appletviewer TelephoneApplet.html
 * ou un navigateur compatible Java.
 *
 * HTML d'intégration :
 * <applet code="Ex1_4_TelephoneApplet.class" width="300" height="280"></applet>
 */
public class Ex1_4_TelephoneApplet extends Applet implements ActionListener {

    private TextField display;
    private String lastSaisie = "";

    @Override
    public void init() {
        setLayout(new BorderLayout());

        display = new TextField();
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        Panel panel = new Panel(new GridLayout(4, 3));

        String[] labels = {"1","2","3","4","5","6","7","8","9","Bis","0","Reset"};

        for (String label : labels) {
            Button btn = new Button(label);
            btn.addActionListener(this);
            panel.add(btn);
        }

        add(panel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        switch (cmd) {
            case "Reset":
                lastSaisie = display.getText();
                display.setText("");
                break;

            case "Bis":
                display.setText(lastSaisie);
                break;

            default:
                display.setText(display.getText() + cmd);
                break;
        }
    }
}
