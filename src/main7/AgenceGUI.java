package main7;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class AgenceGUI extends JFrame {

    private final Agence agence;

    private JTextArea zoneAffichage;
    private JComboBox<String> comboVoitures;
    private JComboBox<String> comboCivilite;

    private JTextField txtNom;
    private JTextField txtPrenom;
    private JTextField txtCin;

    private JTextField txtFiltreMarque;
    private JTextField txtFiltreAnnee;
    private JTextField txtFiltrePrixMax;

    private JTextField txtNouvelleMarque;
    private JTextField txtNouveauModele;
    private JTextField txtNouvelleAnnee;
    private JTextField txtNouveauPrix;

    public AgenceGUI() {
        super("Agence de Location - Gestion des voitures");

        agence = new Agence();
        initialiserDonnees();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1180, 760);
        setJMenuBar(creerBarreMenu());

        BackgroundPanel mainPanel = new BackgroundPanel("bugatti-chiron-super-sport-hommage-t50s.jpg");
        mainPanel.setLayout(new BorderLayout(12, 12));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        Color panneauTransparent = new Color(255, 255, 255, 135);

        mainPanel.add(creerPanelFiltres(panneauTransparent), BorderLayout.NORTH);

        zoneAffichage = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(255, 255, 255, 130));
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        zoneAffichage.setOpaque(false);
        zoneAffichage.setEditable(false);
        zoneAffichage.setFont(new Font("Consolas", Font.PLAIN, 14));
        zoneAffichage.setForeground(new Color(20, 20, 20));
        zoneAffichage.setLineWrap(false);

        JScrollPane scrollZone = new JScrollPane(zoneAffichage);
        scrollZone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Résultats",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                Color.BLACK));
        scrollZone.setOpaque(false);
        scrollZone.getViewport().setOpaque(false);
        mainPanel.add(scrollZone, BorderLayout.CENTER);

        JTabbedPane onglets = new JTabbedPane();
        onglets.setFont(new Font("Arial", Font.BOLD, 12));
        onglets.addTab("Location", creerPanelLocation(panneauTransparent));
        onglets.addTab("Ajouter une voiture", creerPanelAjoutVoiture(panneauTransparent));
        mainPanel.add(onglets, BorderLayout.SOUTH);

        JScrollPane pageGlobaleScroll = new JScrollPane(mainPanel);
        pageGlobaleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pageGlobaleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setContentPane(pageGlobaleScroll);

        actualiserComboVoitures();
        afficherToutesLesVoitures();
        setLocationRelativeTo(null);
    }

    private JMenuBar creerBarreMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuVoitures = new JMenu("Voitures");
        JMenuItem itemToutes = new JMenuItem("Afficher toutes");
        JMenuItem itemLouees = new JMenuItem("Afficher les voitures louées");
        JMenuItem itemAjouter = new JMenuItem("Ajouter une voiture");
        JMenuItem itemQuitter = new JMenuItem("Quitter");

        itemToutes.addActionListener(e -> afficherToutesLesVoitures());
        itemLouees.addActionListener(e -> afficherVoituresLoueesAction());
        itemAjouter.addActionListener(e -> ajouterVoitureAction());
        itemQuitter.addActionListener(e -> dispose());

        menuVoitures.add(itemToutes);
        menuVoitures.add(itemLouees);
        menuVoitures.add(itemAjouter);
        menuVoitures.addSeparator();
        menuVoitures.add(itemQuitter);

        JMenu menuFiltres = new JMenu("Filtres");
        JMenuItem itemFiltrer = new JMenuItem("Appliquer le filtre combiné");
        JMenuItem itemEffacer = new JMenuItem("Réinitialiser les filtres");

        itemFiltrer.addActionListener(e -> appliquerFiltreCombine());
        itemEffacer.addActionListener(e -> {
            txtFiltreMarque.setText("");
            txtFiltreAnnee.setText("");
            txtFiltrePrixMax.setText("");
            afficherToutesLesVoitures();
        });

        menuFiltres.add(itemFiltrer);
        menuFiltres.add(itemEffacer);

        JMenu menuLocations = new JMenu("Locations");
        JMenuItem itemVoirLocations = new JMenuItem("Voir les locations en cours");
        JMenuItem itemLouer = new JMenuItem("Louer la voiture sélectionnée");
        JMenuItem itemRestituer = new JMenuItem("Restituer la voiture sélectionnée");

        itemVoirLocations.addActionListener(e -> afficherLocationsAction());
        itemLouer.addActionListener(e -> louerVoitureAction());
        itemRestituer.addActionListener(e -> rendreVoitureAction());

        menuLocations.add(itemVoirLocations);
        menuLocations.add(itemLouer);
        menuLocations.add(itemRestituer);

        menuBar.add(menuVoitures);
        menuBar.add(menuFiltres);
        menuBar.add(menuLocations);
        return menuBar;
    }

    private JPanel creerPanelFiltres(Color fond) {
        JPanel panelNord = new JPanel(new GridBagLayout());
        panelNord.setBackground(fond);
        panelNord.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Recherche et filtres",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                Color.BLACK));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        panelNord.add(new JLabel("Marque :"), gbc);
        gbc.gridx = 1;
        txtFiltreMarque = new JTextField(10);
        panelNord.add(txtFiltreMarque, gbc);

        gbc.gridx = 2;
        panelNord.add(new JLabel("Année :"), gbc);
        gbc.gridx = 3;
        txtFiltreAnnee = new JTextField(7);
        panelNord.add(txtFiltreAnnee, gbc);

        gbc.gridx = 4;
        panelNord.add(new JLabel("Prix max :"), gbc);
        gbc.gridx = 5;
        txtFiltrePrixMax = new JTextField(7);
        panelNord.add(txtFiltrePrixMax, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        JButton btnToutes = new JButton("Toutes les voitures");
        btnToutes.addActionListener(e -> afficherToutesLesVoitures());
        panelNord.add(btnToutes, gbc);

        gbc.gridx = 1;
        JButton btnFiltrer = new JButton("Filtrer");
        btnFiltrer.addActionListener(e -> appliquerFiltreCombine());
        panelNord.add(btnFiltrer, gbc);

        gbc.gridx = 2;
        JButton btnLouees = new JButton("Voitures louées");
        btnLouees.addActionListener(e -> afficherVoituresLoueesAction());
        panelNord.add(btnLouees, gbc);

        gbc.gridx = 3;
        JButton btnLocations = new JButton("Locations en cours");
        btnLocations.addActionListener(e -> afficherLocationsAction());
        panelNord.add(btnLocations, gbc);

        gbc.gridx = 4;
        JButton btnEffacer = new JButton("Réinitialiser");
        btnEffacer.addActionListener(e -> {
            txtFiltreMarque.setText("");
            txtFiltreAnnee.setText("");
            txtFiltrePrixMax.setText("");
            afficherToutesLesVoitures();
        });
        panelNord.add(btnEffacer, gbc);

        return panelNord;
    }

    private JPanel creerPanelLocation(Color fond) {
        JPanel panelSud = new JPanel(new GridLayout(2, 1, 10, 10));
        panelSud.setBackground(fond);
        panelSud.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Gestion des locations",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                Color.BLACK));

        JPanel panelLocation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLocation.setOpaque(false);

        panelLocation.add(new JLabel("Civilité :"));
        comboCivilite = new JComboBox<>(new String[] {"M.", "Mme", "Mlle"});
        panelLocation.add(comboCivilite);

        panelLocation.add(new JLabel("Nom :"));
        txtNom = new JTextField(8);
        panelLocation.add(txtNom);

        panelLocation.add(new JLabel("Prénom :"));
        txtPrenom = new JTextField(8);
        panelLocation.add(txtPrenom);

        panelLocation.add(new JLabel("CIN :"));
        txtCin = new JTextField(7);
        panelLocation.add(txtCin);

        panelLocation.add(new JLabel("Voiture :"));
        comboVoitures = new JComboBox<>();
        panelLocation.add(comboVoitures);

        JButton btnLouer = new JButton("Louer");
        btnLouer.setBackground(new Color(46, 204, 113));
        btnLouer.setForeground(Color.BLACK);
        btnLouer.addActionListener(e -> louerVoitureAction());
        panelLocation.add(btnLouer);

        panelSud.add(panelLocation);

        JPanel panelActions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelActions.setOpaque(false);

        JButton btnAfficherLocations = new JButton("Voir les locations en cours");
        btnAfficherLocations.addActionListener(e -> afficherLocationsAction());
        panelActions.add(btnAfficherLocations);

        JButton btnVoituresLouees = new JButton("Voir les voitures louées");
        btnVoituresLouees.addActionListener(e -> afficherVoituresLoueesAction());
        panelActions.add(btnVoituresLouees);

        JButton btnRendre = new JButton("Restituer la voiture sélectionnée");
        btnRendre.setBackground(new Color(231, 76, 60));
        btnRendre.setForeground(Color.BLACK);
        btnRendre.addActionListener(e -> rendreVoitureAction());
        panelActions.add(btnRendre);

        panelSud.add(panelActions);
        return panelSud;
    }

    private JPanel creerPanelAjoutVoiture(Color fond) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(fond);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY),
                "Ajouter une voiture",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 12),
                Color.BLACK));

        panel.add(new JLabel("Marque :"));
        txtNouvelleMarque = new JTextField(10);
        panel.add(txtNouvelleMarque);

        panel.add(new JLabel("Modèle :"));
        txtNouveauModele = new JTextField(10);
        panel.add(txtNouveauModele);

        panel.add(new JLabel("Année :"));
        txtNouvelleAnnee = new JTextField(6);
        panel.add(txtNouvelleAnnee);

        panel.add(new JLabel("Prix :"));
        txtNouveauPrix = new JTextField(7);
        panel.add(txtNouveauPrix);

        JButton btnAjouter = new JButton("Ajouter et afficher");
        btnAjouter.setBackground(new Color(52, 152, 219));
        btnAjouter.setForeground(Color.BLACK);
        btnAjouter.addActionListener(e -> ajouterVoitureAction());
        panel.add(btnAjouter);

        return panel;
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imageName) {
            try {
                File file = new File(imageName);
                if (file.exists()) {
                    backgroundImage = ImageIO.read(file);
                } else {
                    URL imageUrl = getClass().getResource(imageName);
                    if (imageUrl != null) {
                        backgroundImage = ImageIO.read(imageUrl);
                    } else {
                        System.err.println("Image introuvable : " + imageName);
                        setBackground(new Color(44, 62, 80));
                    }
                }
            } catch (IOException e) {
                System.err.println("Erreur de lecture de l'image : " + e.getMessage());
                setBackground(new Color(44, 62, 80));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    private void initialiserDonnees() {
        agence.ajouterVoiture(new Voiture("Bugatti", "Chiron", 2024, 5000));
        agence.ajouterVoiture(new Voiture("Renault", "Clio", 2009, 45));
        agence.ajouterVoiture(new Voiture("Peugeot", "208", 2015, 60));
        agence.ajouterVoiture(new Voiture("Mercedes", "Classe A", 2020, 150));
    }

    private void actualiserComboVoitures() {
        if (comboVoitures == null) {
            return;
        }
        comboVoitures.removeAllItems();
        for (Voiture voiture : agence.voitures) {
            comboVoitures.addItem(voiture.toString());
        }
        if (comboVoitures.getItemCount() > 0) {
            comboVoitures.setSelectedIndex(0);
        }
    }

    private void afficherToutesLesVoitures() {
        zoneAffichage.setText("\n  --- TOUTES LES VOITURES DE L'AGENCE ---\n\n");
        if (agence.voitures.isEmpty()) {
            zoneAffichage.append("  Aucune voiture disponible.\n");
            return;
        }
        for (Voiture voiture : agence.voitures) {
            zoneAffichage.append("  " + voiture + "\n");
        }
    }

    private void afficherSelection(Critere critere) {
        zoneAffichage.setText("\n  --- RÉSULTAT DU FILTRE ---\n\n");

        Iterator<Voiture> it = agence.selectionne(critere);
        boolean trouve = false;

        while (it.hasNext()) {
            zoneAffichage.append("  " + it.next() + "\n");
            trouve = true;
        }
        if (!trouve) {
            zoneAffichage.append("  Aucune voiture ne correspond.\n");
        }
    }

    private void appliquerFiltreCombine() {
        InterCritere critere = new InterCritere();
        boolean aAuMoinsUnCritere = false;

        String marque = txtFiltreMarque.getText().trim();
        if (!marque.isEmpty()) {
            critere.addCritere(new CritereMarque(marque));
            aAuMoinsUnCritere = true;
        }

        String anneeTexte = txtFiltreAnnee.getText().trim();
        if (!anneeTexte.isEmpty()) {
            try {
                critere.addCritere(new CritereAnnee(Integer.parseInt(anneeTexte)));
                aAuMoinsUnCritere = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'année doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        String prixTexte = txtFiltrePrixMax.getText().trim();
        if (!prixTexte.isEmpty()) {
            try {
                critere.addCritere(new CriterePrix(Integer.parseInt(prixTexte)));
                aAuMoinsUnCritere = true;
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Le prix max doit être un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        if (!aAuMoinsUnCritere) {
            afficherToutesLesVoitures();
            return;
        }

        afficherSelection(critere);
    }

    private void ajouterVoitureAction() {
        try {
            String marque = txtNouvelleMarque.getText().trim();
            String modele = txtNouveauModele.getText().trim();
            String anneeTexte = txtNouvelleAnnee.getText().trim();
            String prixTexte = txtNouveauPrix.getText().trim();

            if (marque.isEmpty() || modele.isEmpty() || anneeTexte.isEmpty() || prixTexte.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs de la voiture.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int annee = Integer.parseInt(anneeTexte);
            int prix = Integer.parseInt(prixTexte);

            agence.ajouterVoiture(new Voiture(marque, modele, annee, prix));
            actualiserComboVoitures();
            afficherToutesLesVoitures();

            txtNouvelleMarque.setText("");
            txtNouveauModele.setText("");
            txtNouvelleAnnee.setText("");
            txtNouveauPrix.setText("");

            JOptionPane.showMessageDialog(this, "Voiture ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "L'année et le prix doivent être des nombres.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void louerVoitureAction() {
        try {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            String cinTexte = txtCin.getText().trim();
            int indexVoiture = comboVoitures.getSelectedIndex();

            if (nom.isEmpty() || prenom.isEmpty() || cinTexte.isEmpty() || indexVoiture == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs de location.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int cin = Integer.parseInt(cinTexte);
            String civilite = (String) comboCivilite.getSelectedItem();
            Voiture voitureSelectionnee = agence.voitures.get(indexVoiture);
            Client nouveauClient = new Client(nom, prenom, cin, civilite);

            agence.loueVoiture(nouveauClient, voitureSelectionnee);

            JOptionPane.showMessageDialog(this, "Location validée.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            txtNom.setText("");
            txtPrenom.setText("");
            txtCin.setText("");
            afficherLocationsAction();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le CIN doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherVoituresLoueesAction() {
        zoneAffichage.setText("\n  --- VOITURES LOUÉES ---\n\n");

        if (agence.locations.isEmpty()) {
            zoneAffichage.append("  Aucune voiture n'est actuellement louée.\n");
            return;
        }

        for (Voiture voiture : agence.locations.values()) {
            zoneAffichage.append("  " + voiture + "\n");
        }
    }

    private void afficherLocationsAction() {
        zoneAffichage.setText("\n  --- LOCATIONS EN COURS ---\n\n");

        if (agence.locations.isEmpty()) {
            zoneAffichage.append("  Aucune location active.\n");
            return;
        }

        for (Map.Entry<Client, Voiture> entry : agence.locations.entrySet()) {
            zoneAffichage.append("  " + entry.getKey() + " -> " + entry.getValue() + "\n\n");
        }
    }

    private void rendreVoitureAction() {
        try {
            int indexVoiture = comboVoitures.getSelectedIndex();

            if (indexVoiture == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner une voiture.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Voiture voitureARendre = agence.voitures.get(indexVoiture);
            Client clientTrouve = null;

            for (Map.Entry<Client, Voiture> entry : agence.locations.entrySet()) {
                if (entry.getValue().equals(voitureARendre)) {
                    clientTrouve = entry.getKey();
                    break;
                }
            }

            if (clientTrouve != null) {
                agence.rendVoiture(clientTrouve);
                JOptionPane.showMessageDialog(this, "Restitution confirmée.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                afficherLocationsAction();
            } else {
                JOptionPane.showMessageDialog(this, "Cette voiture n'est pas actuellement louée.", "Introuvable", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la restitution.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            AgenceGUI gui = new AgenceGUI();
            gui.setVisible(true);
        });
    }
}