package TP5;

import java.awt.*;
import java.awt.event.*;

/**
 * Exercice 2 - Interface de l'application de dessin : Planche.java
 *
 * Constituée de :
 * - Une fenêtre indépendante (Frame)
 * - Un menu Fichier avec option Fin (quitter)
 * - Une zone centrale (Canvas via classe Dessin) pour dessiner
 * - Une zone d'outils en bas :
 *     * Choix Rectangle ou Ellipse (Choice déroulant)
 *     * Choix de couleur (CheckboxGroup = boutons radio)
 *     * Choix Remplissage oui/non (Checkbox)
 */
public class Planche extends Frame implements ActionListener, ItemListener {

    // Composants de choix
    private Choice choixForme;
    private CheckboxGroup groupeCouleur;
    private Checkbox cbRouge, cbVert, cbBleu;
    private Checkbox cbRemplissage;

    // Zone de dessin
    private Dessin dessin;

    public Planche() {
        super("Application de Dessin");

        // ── Menu ──────────────────────────────────────────────
        MenuBar menuBar = new MenuBar();
        Menu menuFichier = new Menu("Fichier");

        MenuItem itemFin = new MenuItem("Fin");
        itemFin.addActionListener(this);
        menuFichier.add(itemFin);

        menuBar.add(menuFichier);
        setMenuBar(menuBar);

        // ── Layout principal ──────────────────────────────────
        setLayout(new BorderLayout());

        // ── Zone de dessin (Centre) ───────────────────────────
        dessin = new Dessin(this);
        dessin.setBackground(Color.WHITE);
        add(dessin, BorderLayout.CENTER);

        // ── Zone d'outils (Bas) ───────────────────────────────
        Panel panelOutils = new Panel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelOutils.setBackground(new Color(220, 220, 220));

        // -- Choix de la forme (menu déroulant) --
        panelOutils.add(new Label("Forme :"));
        choixForme = new Choice();
        choixForme.add("Rectangle");
        choixForme.add("Ellipse");
        choixForme.addItemListener(this);
        panelOutils.add(choixForme);

        // -- Choix de la couleur (radio buttons) --
        panelOutils.add(new Label("  Couleur :"));
        groupeCouleur = new CheckboxGroup();
        cbRouge = new Checkbox("Rouge", groupeCouleur, true);
        cbVert  = new Checkbox("Vert",  groupeCouleur, false);
        cbBleu  = new Checkbox("Bleu",  groupeCouleur, false);
        cbRouge.addItemListener(this);
        cbVert.addItemListener(this);
        cbBleu.addItemListener(this);
        panelOutils.add(cbRouge);
        panelOutils.add(cbVert);
        panelOutils.add(cbBleu);

        // -- Remplissage (checkbox simple) --
        panelOutils.add(new Label("  "));
        cbRemplissage = new Checkbox("Remplissage");
        cbRemplissage.addItemListener(this);
        panelOutils.add(cbRemplissage);

        add(panelOutils, BorderLayout.SOUTH);

        // ── Taille et affichage ───────────────────────────────
        setSize(600, 450);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    // ── Accesseurs pour la classe Dessin ─────────────────────

    public String getForme() {
        return choixForme.getSelectedItem(); // "Rectangle" ou "Ellipse"
    }

    public Color getCouleur() {
        if (groupeCouleur.getSelectedCheckbox() == cbVert)  return Color.GREEN;
        if (groupeCouleur.getSelectedCheckbox() == cbBleu)  return Color.BLUE;
        return Color.RED; // par défaut
    }

    public boolean isRemplissage() {
        return cbRemplissage.getState();
    }

    // ── Gestion des événements ────────────────────────────────

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Fin")) {
            System.exit(0);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Pas d'action immédiate nécessaire : Dessin lit les valeurs à la création
    }

    // ── Main ──────────────────────────────────────────────────

    public static void main(String[] args) {
        new Planche();
    }
}
