package main7;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.util.Iterator;
import java.util.Map;

public class AgenceGUI extends JFrame {

    private Agence agence;
    
    // Composants de l'interface
    private JTextArea zoneAffichage;
    private JComboBox<String> comboVoitures; 
    private JTextField txtPrixMax, txtNom, txtPrenom, txtCin;

    public AgenceGUI() {
        super("Agence de Location Premium - Bugatti Edition");
        
        agence = new Agence();
        initialiserDonnees();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        
        // Création du panneau principal avec recherche d'image robuste
        BackgroundPanel mainPanel = new BackgroundPanel("bugatti-chiron-super-sport-hommage-t50s.jpg");
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // NOUVEAU : Opacité réduite à 120 (sur 255) pour laisser bien passer l'image
        Color panneauTransparent = new Color(255, 255, 255, 120); 

        // --- ZONE HAUT (Filtres et Affichage) ---
        JPanel panelNord = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelNord.setBackground(panneauTransparent);
        panelNord.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), 
                "Recherche et Filtres", 
                TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 12), Color.BLACK));
        
        JButton btnToutes = new JButton("Toutes les voitures");
        btnToutes.setBackground(Color.DARK_GRAY);
        btnToutes.setForeground(Color.WHITE);
        
        panelNord.add(btnToutes);
        panelNord.add(new JLabel("Prix Max :"));
        txtPrixMax = new JTextField(7);
        panelNord.add(txtPrixMax);
        
        JButton btnFiltrerPrix = new JButton("Filtrer par prix");
        panelNord.add(btnFiltrerPrix);

        mainPanel.add(panelNord, BorderLayout.NORTH);

        // --- ZONE CENTRE (Affichage des résultats) ---
        // NOUVEAU : Création sécurisée pour éviter les bugs de superposition de texte Swing
        zoneAffichage = new JTextArea() {
            @Override
            protected void paintComponent(Graphics g) {
                // Fond semi-transparent peint manuellement
                g.setColor(new Color(255, 255, 255, 130)); 
                g.fillRect(0, 0, getWidth(), getHeight());
                super.paintComponent(g);
            }
        };
        zoneAffichage.setOpaque(false); // Indispensable avec le paintComponent personnalisé
        zoneAffichage.setEditable(false);
        zoneAffichage.setFont(new Font("Consolas", Font.BOLD, 14));
        zoneAffichage.setForeground(new Color(20, 20, 20)); // Texte un peu plus foncé pour contraster
        zoneAffichage.setLineWrap(false); 

        JScrollPane scrollZone = new JScrollPane(zoneAffichage);
        scrollZone.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), 
                "Résultats", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 12), Color.BLACK));
        scrollZone.setOpaque(false);
        scrollZone.getViewport().setOpaque(false);
        mainPanel.add(scrollZone, BorderLayout.CENTER);

        // --- ZONE BAS (Gestion des Locations) ---
        JPanel panelSud = new JPanel(new GridLayout(2, 1, 10, 10));
        panelSud.setBackground(panneauTransparent);
        panelSud.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY), 
                "Gestion des Locations", TitledBorder.LEFT, TitledBorder.TOP, 
                new Font("Arial", Font.BOLD, 12), Color.BLACK));

        // Ligne 1 : Louer
        JPanel panelLocation = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLocation.setOpaque(false);
        
        panelLocation.add(new JLabel("Nom:"));
        txtNom = new JTextField(7);
        panelLocation.add(txtNom);
        
        panelLocation.add(new JLabel("Prénom:"));
        txtPrenom = new JTextField(7);
        panelLocation.add(txtPrenom);
        
        panelLocation.add(new JLabel("CIN:"));
        txtCin = new JTextField(6);
        panelLocation.add(txtCin);
        
        panelLocation.add(new JLabel("Voiture:"));
        comboVoitures = new JComboBox<>();
        actualiserComboVoitures();
        panelLocation.add(comboVoitures);
        
        JButton btnLouer = new JButton("Louer ce véhicule");
        btnLouer.setBackground(new Color(46, 204, 113));
        btnLouer.setForeground(Color.WHITE);
        panelLocation.add(btnLouer);
        
        panelSud.add(panelLocation);

        // Ligne 2 : Restituer / Voir
        JPanel panelRestitution = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRestitution.setOpaque(false);
        
        JButton btnAfficherLocations = new JButton("Voir les locations en cours");
        panelRestitution.add(btnAfficherLocations);
        
        panelRestitution.add(new JLabel("  |  Rendre une voiture (Saisir CIN au-dessus):"));
        
        JButton btnRendre = new JButton("Restituer");
        btnRendre.setBackground(new Color(231, 76, 60));
        btnRendre.setForeground(Color.WHITE);
        panelRestitution.add(btnRendre);
        
        panelSud.add(panelRestitution);

        mainPanel.add(panelSud, BorderLayout.SOUTH);

        JScrollPane pageGlobaleScroll = new JScrollPane(mainPanel);
        pageGlobaleScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        pageGlobaleScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        setContentPane(pageGlobaleScroll);

        // --- GESTION DES ÉVÉNEMENTS ---
        btnToutes.addActionListener(e -> afficherToutesLesVoitures());

        btnFiltrerPrix.addActionListener(e -> {
            try {
                int max = Integer.parseInt(txtPrixMax.getText());
                afficherSelection(new CriterePrix(max));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Veuillez entrer un prix valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLouer.addActionListener(e -> louerVoitureAction());
        btnAfficherLocations.addActionListener(e -> afficherLocationsAction());
        btnRendre.addActionListener(e -> rendreVoitureAction());

        afficherToutesLesVoitures();
        setLocationRelativeTo(null);
    }

    // --- CLASSE INTERNE POUR L'IMAGE DE FOND ---
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

    // --- LOGIQUE METIER ---

    private void initialiserDonnees() {
        agence.ajouterVoiture(new Voiture("Bugatti", "Chiron", 2024, 5000));
        agence.ajouterVoiture(new Voiture("Renault", "Clio", 2009, 45));
        agence.ajouterVoiture(new Voiture("Peugeot", "208", 2015, 60));
        agence.ajouterVoiture(new Voiture("Mercedes", "Classe A", 2020, 150));
    }

    private void actualiserComboVoitures() {
        comboVoitures.removeAllItems();
        for (Voiture v : agence.voitures) {
            comboVoitures.addItem(v.toString());
        }
    }

    private void afficherToutesLesVoitures() {
        zoneAffichage.setText("\n  --- TOUTES LES VOITURES DE L'AGENCE ---\n\n");
        for (Voiture v : agence.voitures) {
            zoneAffichage.append("  " + v.toString() + "\n");
        }
    }

    private void afficherSelection(Critere c) {
        zoneAffichage.setText("\n  --- RÉSULTAT DU FILTRE ---\n\n");
        
        Iterator<Voiture> it = agence.selectionne(c);
        boolean trouve = false;
        
        while (it.hasNext()) {
            zoneAffichage.append("  " + it.next().toString() + "\n");
            trouve = true;
        }
        if (!trouve) zoneAffichage.append("  Aucune voiture ne correspond.\n");
    }

    private void louerVoitureAction() {
        try {
            String nom = txtNom.getText().trim();
            String prenom = txtPrenom.getText().trim();
            int cin = Integer.parseInt(txtCin.getText().trim());
            int indexVoiture = comboVoitures.getSelectedIndex();

            if (nom.isEmpty() || prenom.isEmpty() || indexVoiture == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.", "Attention", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Voiture v = agence.voitures.get(indexVoiture);
            Client nouveauClient = new Client(); 
            
            agence.loueVoiture(nouveauClient, v);
            
            JOptionPane.showMessageDialog(this, "Location validée !", "Succès", JOptionPane.INFORMATION_MESSAGE);
            txtNom.setText(""); txtPrenom.setText(""); txtCin.setText("");
            afficherLocationsAction();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Le CIN doit être un nombre.", "Erreur", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void afficherLocationsAction() {
        zoneAffichage.setText("\n  --- LOCATIONS EN COURS ---\n\n");
        
        if (agence.locations.isEmpty()) {
            zoneAffichage.append("  Aucune location active.\n");
            return;
        }
        
        for (Map.Entry<Client, Voiture> entry : agence.locations.entrySet()) {
            Voiture v = entry.getValue();
            zoneAffichage.append("  Client a loué => " + v.toString() + "\n\n");
        }
    }

    private void rendreVoitureAction() {
        try {
            int indexVoiture = comboVoitures.getSelectedIndex();
            
            if (indexVoiture == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner la voiture à rendre.", "Attention", JOptionPane.WARNING_MESSAGE);
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
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            AgenceGUI gui = new AgenceGUI();
            gui.setVisible(true);
        });
    }
}