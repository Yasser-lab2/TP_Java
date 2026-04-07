package main7;

import java.awt.*;
import java.awt.event.*;
import java.util.Map;
import javax.swing.*;
import javax.swing.plaf.basic.*;
import javax.swing.table.*;

public class AgenceGUI1 extends JFrame {

    // ── Palette ──────────────────────────────────────────────────
    static final Color BG_DEEP      = new Color(10,  12,  20);
    static final Color BG_CARD      = new Color(18,  22,  36);
    static final Color BG_PANEL     = new Color(24,  29,  46);
    static final Color BG_ROW_ALT   = new Color(28,  34,  54);
    static final Color ACCENT_GOLD  = new Color(212, 175,  55);
    static final Color TEXT_PRIMARY = new Color(240, 240, 255);
    static final Color TEXT_MUTED   = new Color(130, 140, 170);
    static final Color BORDER_SUBTLE= new Color(45,  55,  85);
    static final Color SUCCESS      = new Color( 46, 213, 115);
    static final Color DANGER       = new Color(255,  71,  87);
    static final Color INFO         = new Color( 54, 162, 235);

    static final Font FONT_TITLE  = new Font("Serif",      Font.BOLD,  22);
    static final Font FONT_CARD   = new Font("SansSerif",  Font.BOLD,  13);
    static final Font FONT_BODY   = new Font("Monospaced", Font.PLAIN, 13);
    static final Font FONT_LABEL  = new Font("SansSerif",  Font.PLAIN, 12);
    static final Font FONT_BUTTON = new Font("SansSerif",  Font.BOLD,  12);
    static final Font FONT_SMALL  = new Font("SansSerif",  Font.PLAIN, 11);

    private final Agence agence;

    private JTable carTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> comboVoitures;
    private JComboBox<String> comboCivilite;
    private JTextField txtNom, txtPrenom, txtCin;
    private JTextField txtFiltreMarque, txtFiltreAnnee, txtFiltrePrixMax;
    private JTextField txtNouvelleMarque, txtNouveauModele, txtNouvelleAnnee, txtNouveauPrix;

    private JLabel statusLabel;
    private JLabel statParcValue;
    private JLabel statLoueesValue;
    private JLabel statDispoValue;

    public AgenceGUI1() {
        super("AGENCE PRESTIGE  -  Systeme de Location");
        agence = new Agence();
        initialiserDonnees();

        applyGlobalUIDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 820);
        setMinimumSize(new Dimension(1100, 700));
        getContentPane().setBackground(BG_DEEP);
        setLayout(new BorderLayout());

        add(buildHeader(),    BorderLayout.NORTH);
        add(buildCenter(),    BorderLayout.CENTER);
        add(buildStatusBar(), BorderLayout.SOUTH);
        setJMenuBar(buildMenuBar());

        actualiserComboVoitures();
        refreshTable(null);
        setLocationRelativeTo(null);
    }

    // ── Global UI defaults ────────────────────────────────────────
    private void applyGlobalUIDefaults() {
        UIManager.put("Panel.background",             BG_CARD);
        UIManager.put("Label.foreground",             TEXT_PRIMARY);
        UIManager.put("TextField.background",         BG_PANEL);
        UIManager.put("TextField.foreground",         TEXT_PRIMARY);
        UIManager.put("TextField.caretForeground",    ACCENT_GOLD);
        UIManager.put("ComboBox.background",          BG_PANEL);
        UIManager.put("ComboBox.foreground",          TEXT_PRIMARY);
        UIManager.put("ComboBox.selectionBackground", ACCENT_GOLD);
        UIManager.put("ComboBox.selectionForeground", BG_DEEP);
        UIManager.put("ScrollPane.background",        BG_CARD);
        UIManager.put("Viewport.background",          BG_CARD);
        UIManager.put("TabbedPane.background",        BG_CARD);
        UIManager.put("TabbedPane.foreground",        TEXT_PRIMARY);
        UIManager.put("OptionPane.background",        BG_CARD);
        UIManager.put("OptionPane.messageForeground", TEXT_PRIMARY);
        UIManager.put("Button.background",            BG_PANEL);
        UIManager.put("Button.foreground",            TEXT_PRIMARY);
    }

    // ── Header ────────────────────────────────────────────────────
    private JPanel buildHeader() {
        JPanel header = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setPaint(new GradientPaint(0, 0, new Color(15,18,30), getWidth(), 0, new Color(30,22,10)));
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(ACCENT_GOLD);
                g2.fillRect(0, getHeight()-2, getWidth(), 2);
                g2.dispose();
            }
        };
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 72));
        header.setBorder(BorderFactory.createEmptyBorder(0, 28, 0, 28));

        // Left: logo + title
        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        left.setOpaque(false);
        JLabel logo = new JLabel("*");
        logo.setFont(new Font("Serif", Font.BOLD, 34));
        logo.setForeground(ACCENT_GOLD);
        JPanel titles = new JPanel(new GridLayout(2, 1, 0, 0));
        titles.setOpaque(false);
        JLabel brand = new JLabel("AGENCE PRESTIGE");
        brand.setFont(new Font("Serif", Font.BOLD, 20));
        brand.setForeground(ACCENT_GOLD);
        JLabel sub = new JLabel("Systeme de gestion de location");
        sub.setFont(FONT_SMALL);
        sub.setForeground(TEXT_MUTED);
        titles.add(brand);
        titles.add(sub);
        left.add(logo);
        left.add(titles);

        // Right: stat chips
        JPanel stats = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 0));
        stats.setOpaque(false);
        statParcValue = new JLabel();
        statLoueesValue = new JLabel();
        statDispoValue = new JLabel();
        stats.add(statChip("PARC", statParcValue));
        stats.add(statChip("LOUEES", statLoueesValue));
        stats.add(statChip("DISPO", statDispoValue));

        header.add(left,  BorderLayout.WEST);
        header.add(stats, BorderLayout.EAST);
        return header;
    }

    private JPanel statChip(String label, JLabel valueLabel) {
        JPanel chip = new JPanel(new GridLayout(2, 1, 0, 2));
        chip.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_SUBTLE),
            BorderFactory.createEmptyBorder(6, 14, 6, 14)));
        chip.setBackground(BG_PANEL);
        chip.setOpaque(true);

        JLabel titleLabel = new JLabel(label, SwingConstants.CENTER);
        titleLabel.setFont(FONT_SMALL);
        titleLabel.setForeground(TEXT_MUTED);

        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setFont(FONT_CARD);
        valueLabel.setForeground(ACCENT_GOLD);

        chip.add(titleLabel);
        chip.add(valueLabel);
        return chip;
    }

    // ── Center split ──────────────────────────────────────────────
    private JSplitPane buildCenter() {
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buildLeftPanel(), buildRightPanel());
        split.setDividerLocation(820);
        split.setDividerSize(3);
        split.setBackground(BORDER_SUBTLE);
        split.setBorder(null);
        return split;
    }

    // ── LEFT panel ────────────────────────────────────────────────
    private JPanel buildLeftPanel() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG_DEEP);
        p.add(buildFilterBar(),    BorderLayout.NORTH);
        p.add(buildCarTablePanel(), BorderLayout.CENTER);
        return p;
    }

    private JPanel buildFilterBar() {
        JPanel bar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        bar.setBackground(BG_CARD);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SUBTLE));

        bar.add(filterLabel("Marque"));
        txtFiltreMarque  = filterField(10); bar.add(txtFiltreMarque);
        bar.add(filterLabel("Annee"));
        txtFiltreAnnee   = filterField(7);  bar.add(txtFiltreAnnee);
        bar.add(filterLabel("Prix max (MAD)"));
        txtFiltrePrixMax = filterField(7);  bar.add(txtFiltrePrixMax);

        bar.add(iconButton("Filtrer",       INFO,        e -> appliquerFiltreCombine()));
        bar.add(iconButton("Tout afficher", TEXT_MUTED,  e -> {
            txtFiltreMarque.setText("");
            txtFiltreAnnee.setText("");
            txtFiltrePrixMax.setText("");
            refreshTable(null);
        }));
        bar.add(iconButton("Louees", ACCENT_GOLD, e -> afficherVoituresLouees()));
        return bar;
    }

    private JLabel filterLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_SMALL);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    private JTextField filterField(int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(BG_PANEL);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT_GOLD);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_SUBTLE),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)));
        return f;
    }

    private JPanel buildCarTablePanel() {
        String[] cols = {"#", "Marque", "Modele", "Annee", "Prix/jour (MAD)", "Statut"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        carTable = new JTable(tableModel) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(212, 175, 55, 60));
                    c.setForeground(ACCENT_GOLD);
                } else {
                    c.setBackground(row % 2 == 0 ? BG_CARD : BG_ROW_ALT);
                    c.setForeground(TEXT_PRIMARY);
                    if (col == 5 && c instanceof JLabel) {
                        JLabel lbl = (JLabel) c;
                        if (lbl.getText().contains("Louee")) {
                            lbl.setForeground(DANGER);
                        } else {
                            lbl.setForeground(SUCCESS);
                        }
                    }
                }
                if (c instanceof JComponent) {
                    ((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
                }
                return c;
            }
        };

        carTable.setBackground(BG_CARD);
        carTable.setForeground(TEXT_PRIMARY);
        carTable.setFont(FONT_BODY);
        carTable.setRowHeight(34);
        carTable.setShowGrid(false);
        carTable.setIntercellSpacing(new Dimension(0, 1));
        carTable.setSelectionBackground(new Color(212, 175, 55, 50));
        carTable.setSelectionForeground(ACCENT_GOLD);
        carTable.setFillsViewportHeight(true);

        JTableHeader header = carTable.getTableHeader();
        header.setBackground(BG_DEEP);
        header.setForeground(ACCENT_GOLD);
        header.setFont(FONT_CARD);
        header.setPreferredSize(new Dimension(0, 38));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT_GOLD));
        header.setReorderingAllowed(false);
        ((DefaultTableCellRenderer) header.getDefaultRenderer())
            .setHorizontalAlignment(SwingConstants.LEFT);

        carTable.getColumnModel().getColumn(0).setMaxWidth(40);
        carTable.getColumnModel().getColumn(0).setMinWidth(40);
        carTable.getColumnModel().getColumn(3).setMaxWidth(70);
        carTable.getColumnModel().getColumn(4).setMaxWidth(140);
        carTable.getColumnModel().getColumn(5).setMaxWidth(120);

        JScrollPane scroll = new JScrollPane(carTable);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(BG_CARD);
        scroll.setBackground(BG_DEEP);
        styleScrollBar(scroll.getVerticalScrollBar());

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(BG_DEEP);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    // ── RIGHT panel ───────────────────────────────────────────────
    private JPanel buildRightPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 12));
        p.setBackground(BG_DEEP);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_CARD);
        tabs.setForeground(TEXT_PRIMARY);
        tabs.setFont(FONT_CARD);
        tabs.addTab("Location",           buildLocationTab());
        tabs.addTab("Ajouter voiture",    buildAddCarTab());
        tabs.addTab("Locations en cours", buildActiveRentalsTab());

        p.add(tabs, BorderLayout.CENTER);
        return p;
    }

    private JPanel buildLocationTab() {
        JPanel p = cardPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints g = newGbc();

        sectionTitle(p, g, "Nouvelle location", 0);

        g.gridy = 1; addRow(p, g, "Civilite",  comboCivilite = new JComboBox<>(new String[]{"M.","Mme","Mlle"}));
        g.gridy = 2; addRow(p, g, "Nom",       txtNom    = inputField(14));
        g.gridy = 3; addRow(p, g, "Prenom",    txtPrenom = inputField(14));
        g.gridy = 4; addRow(p, g, "CIN",       txtCin    = inputField(10));
        g.gridy = 5; addRow(p, g, "Vehicule",  comboVoitures = new JComboBox<>());
        styleCombo(comboCivilite);
        styleCombo(comboVoitures);

        g.gridy = 6; g.gridx = 0; g.gridwidth = 2;
        g.insets = new Insets(18, 16, 8, 16);
        JButton btnLouer = actionButton("VALIDER LA LOCATION", SUCCESS);
        btnLouer.addActionListener(e -> louerVoitureAction());
        p.add(btnLouer, g);

        g.gridy = 7; g.insets = new Insets(4, 16, 8, 16);
        JButton btnRendre = actionButton("RESTITUER LE VEHICULE SELECTIONNE", DANGER);
        btnRendre.addActionListener(e -> rendreVoitureAction());
        p.add(btnRendre, g);

        g.gridy = 8; g.weighty = 1;
        p.add(Box.createVerticalGlue(), g);
        return p;
    }

    private JPanel buildAddCarTab() {
        JPanel p = cardPanel();
        p.setLayout(new GridBagLayout());
        GridBagConstraints g = newGbc();

        sectionTitle(p, g, "Ajouter un vehicule", 0);

        g.gridy = 1; addRow(p, g, "Marque",          txtNouvelleMarque = inputField(14));
        g.gridy = 2; addRow(p, g, "Modele",          txtNouveauModele  = inputField(14));
        g.gridy = 3; addRow(p, g, "Annee",           txtNouvelleAnnee  = inputField(8));
        g.gridy = 4; addRow(p, g, "Prix/jour (MAD)", txtNouveauPrix    = inputField(8));

        g.gridy = 5; g.gridx = 0; g.gridwidth = 2;
        g.insets = new Insets(18, 16, 8, 16);
        JButton btnAdd = actionButton("ENREGISTRER LE VEHICULE", INFO);
        btnAdd.addActionListener(e -> ajouterVoitureAction());
        p.add(btnAdd, g);

        g.gridy = 6; g.weighty = 1;
        p.add(Box.createVerticalGlue(), g);
        return p;
    }

    private JPanel buildActiveRentalsTab() {
        JPanel p = cardPanel();
        p.setLayout(new BorderLayout(0, 8));

        JLabel title = new JLabel("Locations actives");
        title.setFont(FONT_TITLE);
        title.setForeground(ACCENT_GOLD);
        title.setBorder(BorderFactory.createEmptyBorder(14, 16, 8, 16));
        p.add(title, BorderLayout.NORTH);

        DefaultTableModel rentModel = new DefaultTableModel(
            new String[]{"Client", "Civilite", "CIN", "Vehicule"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable rentTable = new JTable(rentModel) {
            @Override public Component prepareRenderer(TableCellRenderer r, int row, int col) {
                Component c = super.prepareRenderer(r, row, col);
                c.setBackground(row % 2 == 0 ? BG_CARD : BG_ROW_ALT);
                c.setForeground(TEXT_PRIMARY);
                if (isRowSelected(row)) {
                    c.setBackground(new Color(212, 175, 55, 60));
                    c.setForeground(ACCENT_GOLD);
                }
                return c;
            }
        };
        rentTable.setBackground(BG_CARD);
        rentTable.setForeground(TEXT_PRIMARY);
        rentTable.setFont(FONT_BODY);
        rentTable.setRowHeight(30);
        rentTable.setShowGrid(false);
        rentTable.setIntercellSpacing(new Dimension(0, 1));
        rentTable.getTableHeader().setBackground(BG_DEEP);
        rentTable.getTableHeader().setForeground(ACCENT_GOLD);
        rentTable.getTableHeader().setFont(FONT_CARD);

        JScrollPane sc = new JScrollPane(rentTable);
        sc.setBorder(BorderFactory.createEmptyBorder(0, 8, 8, 8));
        sc.getViewport().setBackground(BG_CARD);
        sc.setBackground(BG_CARD);
        p.add(sc, BorderLayout.CENTER);

        JButton btnRefresh = actionButton("RAFRAICHIR", TEXT_MUTED);
        btnRefresh.addActionListener(e -> {
            rentModel.setRowCount(0);
            for (Map.Entry<Client, Voiture> entry : agence.locations.entrySet()) {
                Client cl  = entry.getKey();
                Voiture v  = entry.getValue();
                rentModel.addRow(new Object[]{
                    cl.getNom() + " " + cl.getPrenom(),
                    cl.getCivilite(),
                    cl.getCin(),
                    v.getMarque() + " " + v.getModel()
                });
            }
            if (rentModel.getRowCount() == 0) {
                rentModel.addRow(new Object[]{"Aucune location active", "", "", ""});
            }
            setStatus("Locations : " + agence.locations.size() + " en cours.");
        });
        btnRefresh.setBorder(BorderFactory.createEmptyBorder(8, 16, 10, 16));
        p.add(btnRefresh, BorderLayout.SOUTH);

        return p;
    }

    // ── Status bar ────────────────────────────────────────────────
    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(BG_DEEP);
        bar.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_SUBTLE));
        bar.setPreferredSize(new Dimension(0, 28));

        statusLabel = new JLabel("  Systeme pret.");
        statusLabel.setFont(FONT_SMALL);
        statusLabel.setForeground(TEXT_MUTED);
        bar.add(statusLabel, BorderLayout.WEST);

        JLabel version = new JLabel("Agence Prestige v2.0  ");
        version.setFont(FONT_SMALL);
        version.setForeground(new Color(60, 70, 100));
        bar.add(version, BorderLayout.EAST);
        return bar;
    }

    // ── Menu bar ──────────────────────────────────────────────────
    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();
        bar.setBackground(BG_CARD);
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SUBTLE));

        bar.add(buildMenu("Voitures",
            menuItem("Afficher tout", e -> {
                txtFiltreMarque.setText("");
                txtFiltreAnnee.setText("");
                txtFiltrePrixMax.setText("");
                refreshTable(null);
            }),
            menuItem("Voitures louees", e -> afficherVoituresLouees()),
            null,
            menuItem("Quitter", e -> System.exit(0))
        ));
        bar.add(buildMenu("Filtres",
            menuItem("Appliquer filtres", e -> appliquerFiltreCombine()),
            menuItem("Reinitialiser",     e -> {
                txtFiltreMarque.setText("");
                txtFiltreAnnee.setText("");
                txtFiltrePrixMax.setText("");
                refreshTable(null);
            })
        ));
        bar.add(buildMenu("Locations",
            menuItem("Louer",     e -> louerVoitureAction()),
            menuItem("Restituer", e -> rendreVoitureAction())
        ));
        return bar;
    }

    private JMenu buildMenu(String name, Object... items) {
        JMenu m = new JMenu(name);
        m.setForeground(TEXT_PRIMARY);
        m.setFont(FONT_CARD);
        for (Object item : items) {
            if (item == null) m.addSeparator();
            else m.add((JMenuItem) item);
        }
        return m;
    }

    private JMenuItem menuItem(String name, ActionListener al) {
        JMenuItem mi = new JMenuItem(name);
        mi.setBackground(BG_CARD);
        mi.setForeground(TEXT_PRIMARY);
        mi.setFont(FONT_LABEL);
        mi.addActionListener(al);
        return mi;
    }

    // ── Business logic ────────────────────────────────────────────

    /**
     * Repopulates the car table. If filter is null, all cars are shown.
     * Uses only the public getters from Voiture: getMarque(), getModel(), getAnnee(), getPrix()
     */
    private void refreshTable(java.util.function.Predicate<Voiture> filter) {
        tableModel.setRowCount(0);
        int idx = 1;
        for (Voiture v : agence.voitures) {
            if (filter != null && !filter.test(v)) continue;
            boolean louee = agence.estLoue(v);
            tableModel.addRow(new Object[]{
                idx++,
                v.getMarque(),
                v.getModel(),
                v.getAnnee(),
                String.format("%,d MAD", v.getPrix()),
                louee ? "Louee" : "Disponible"
            });
        }
        updateStatsChips();
        setStatus("Affichage : " + tableModel.getRowCount() + " vehicule(s).");
    }

    private void updateStatsChips() {
        if (statParcValue == null || statLoueesValue == null || statDispoValue == null) {
            return;
        }
        int parc = agence.voitures.size();
        int louees = agence.locations.size();
        int dispo = parc - louees;
        statParcValue.setText(parc + " vehicules");
        statLoueesValue.setText(louees + " en cours");
        statDispoValue.setText(dispo + " libres");
    }

    private void afficherVoituresLouees() {
        refreshTable(v -> agence.estLoue(v));
        setStatus("Voitures louees : " + agence.locations.size());
    }

    private void appliquerFiltreCombine() {
        String marque = txtFiltreMarque.getText().trim().toLowerCase();
        String anneeS = txtFiltreAnnee.getText().trim();
        String prixS  = txtFiltrePrixMax.getText().trim();
        int annee = 0, prixMax = Integer.MAX_VALUE;
        try {
            if (!anneeS.isEmpty()) annee = Integer.parseInt(anneeS);
        } catch (NumberFormatException ex) {
            showError("Annee invalide."); return;
        }
        try {
            if (!prixS.isEmpty()) prixMax = Integer.parseInt(prixS);
        } catch (NumberFormatException ex) {
            showError("Prix invalide."); return;
        }
        final int fa = annee, fp = prixMax;
        refreshTable(v ->
            (marque.isEmpty() || v.getMarque().toLowerCase().contains(marque)) &&
            (fa == 0 || v.getAnnee() == fa) &&
            v.getPrix() <= fp
        );
    }

    private void louerVoitureAction() {
        String nom    = txtNom.getText().trim();
        String prenom = txtPrenom.getText().trim();
        String cinS   = txtCin.getText().trim();
        int index     = comboVoitures.getSelectedIndex();

        if (nom.isEmpty() || prenom.isEmpty() || cinS.isEmpty() || index == -1) {
            showError("Veuillez remplir tous les champs de location."); return;
        }
        try {
            int cin = Integer.parseInt(cinS);
            Voiture v = agence.voitures.get(index);
            Client client = new Client(nom, prenom, cin, (String) comboCivilite.getSelectedItem());
            agence.loueVoiture(client, v);
            txtNom.setText(""); txtPrenom.setText(""); txtCin.setText("");
            actualiserComboVoitures();
            refreshTable(null);
            showSuccess("Location validee pour " + nom + " " + prenom + ".");
        } catch (NumberFormatException ex) {
            showError("Le CIN doit etre un nombre entier.");
        } catch (Exception ex) {
            showError(ex.getMessage());
        }
    }

    private void ajouterVoitureAction() {
        String marque = txtNouvelleMarque.getText().trim();
        String modele = txtNouveauModele.getText().trim();
        String anneeS = txtNouvelleAnnee.getText().trim();
        String prixS  = txtNouveauPrix.getText().trim();

        if (marque.isEmpty() || modele.isEmpty() || anneeS.isEmpty() || prixS.isEmpty()) {
            showError("Remplissez tous les champs."); return;
        }
        try {
            agence.ajouterVoiture(new Voiture(marque, modele,
                Integer.parseInt(anneeS), Integer.parseInt(prixS)));
            txtNouvelleMarque.setText(""); txtNouveauModele.setText("");
            txtNouvelleAnnee.setText("");  txtNouveauPrix.setText("");
            actualiserComboVoitures();
            refreshTable(null);
            showSuccess("Vehicule " + marque + " " + modele + " ajoute avec succes.");
        } catch (NumberFormatException ex) {
            showError("L'annee et le prix doivent etre des nombres entiers.");
        }
    }

    private void rendreVoitureAction() {
        int index = comboVoitures.getSelectedIndex();
        if (index == -1) { showError("Selectionnez une voiture."); return; }
        Voiture v = agence.voitures.get(index);
        Client found = null;
        for (Map.Entry<Client, Voiture> e : agence.locations.entrySet()) {
            if (e.getValue().equals(v)) { found = e.getKey(); break; }
        }
        if (found == null) {
            showError("Ce vehicule n'est pas actuellement loue."); return;
        }
        agence.rendVoiture(found);
        refreshTable(null);
        showSuccess("Restitution confirmee.");
    }

    private void actualiserComboVoitures() {
        if (comboVoitures == null) return;
        comboVoitures.removeAllItems();
        for (Voiture v : agence.voitures) {
            comboVoitures.addItem(v.getMarque() + " " + v.getModel() + " (" + v.getAnnee() + ")");
        }
        if (comboVoitures.getItemCount() > 0) comboVoitures.setSelectedIndex(0);
    }

    private void initialiserDonnees() {
        agence.ajouterVoiture(new Voiture("Bugatti",  "Chiron",   2024, 5000));
        agence.ajouterVoiture(new Voiture("Renault",  "Clio",     2009,   45));
        agence.ajouterVoiture(new Voiture("Peugeot",  "208",      2015,   60));
        agence.ajouterVoiture(new Voiture("Mercedes", "Classe A", 2020,  150));
        agence.ajouterVoiture(new Voiture("BMW",      "X5",       2022,  380));
        agence.ajouterVoiture(new Voiture("Porsche",  "911",      2023, 1200));
    }

    // ── UI helpers ────────────────────────────────────────────────
    private JPanel cardPanel() {
        JPanel p = new JPanel();
        p.setBackground(BG_CARD);
        p.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, ACCENT_GOLD));
        return p;
    }

    private GridBagConstraints newGbc() {
        GridBagConstraints g = new GridBagConstraints();
        g.fill    = GridBagConstraints.HORIZONTAL;
        g.insets  = new Insets(6, 16, 2, 16);
        g.weightx = 1;
        return g;
    }

    private void sectionTitle(JPanel p, GridBagConstraints g, String text, int row) {
        g.gridy = row; g.gridx = 0; g.gridwidth = 2;
        g.insets = new Insets(16, 16, 4, 16);
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(ACCENT_GOLD);
        lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_SUBTLE));
        p.add(lbl, g);
        g.gridwidth = 1;
        g.insets = new Insets(6, 16, 2, 16);
    }

    private void addRow(JPanel p, GridBagConstraints g, String label, JComponent field) {
        g.gridx = 0; g.weightx = 0;
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        lbl.setPreferredSize(new Dimension(120, 26));
        p.add(lbl, g);
        g.gridx = 1; g.weightx = 1;
        p.add(field, g);
    }

    private JTextField inputField(int cols) {
        JTextField f = new JTextField(cols);
        f.setBackground(BG_PANEL);
        f.setForeground(TEXT_PRIMARY);
        f.setCaretColor(ACCENT_GOLD);
        f.setFont(FONT_BODY);
        f.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_SUBTLE),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        return f;
    }

    private JButton actionButton(String text, Color accent) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(accent.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(accent.getRed(), accent.getGreen(), accent.getBlue(), 40));
                } else {
                    g2.setColor(BG_PANEL);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 6, 6);
                g2.setColor(accent);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 6, 6);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(accent);
        btn.setFont(FONT_BUTTON);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(280, 38));
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        return btn;
    }

    private JButton iconButton(String text, Color color, ActionListener al) {
        JButton btn = new JButton(text);
        btn.setFont(FONT_BUTTON);
        btn.setForeground(color);
        btn.setBackground(BG_PANEL);
        btn.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_SUBTLE),
            BorderFactory.createEmptyBorder(4, 12, 4, 12)));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addActionListener(al);
        return btn;
    }

    private void styleCombo(JComboBox<?> cb) {
        cb.setBackground(BG_PANEL);
        cb.setForeground(TEXT_PRIMARY);
        cb.setFont(FONT_BODY);
        cb.setBorder(BorderFactory.createLineBorder(BORDER_SUBTLE));
    }

    private void styleScrollBar(JScrollBar sb) {
        sb.setBackground(BG_CARD);
        sb.setUI(new BasicScrollBarUI() {
            @Override protected void configureScrollBarColors() {
                thumbColor = new Color(60, 70, 100);
                trackColor = BG_CARD;
            }
            @Override protected JButton createDecreaseButton(int o) { return emptyBtn(); }
            @Override protected JButton createIncreaseButton(int o) { return emptyBtn(); }
            private JButton emptyBtn() {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(0, 0));
                return b;
            }
        });
    }

    private void setStatus(String msg) {
        if (statusLabel != null) statusLabel.setText("  " + msg);
    }

    private void showSuccess(String msg) {
        setStatus(msg);
        JOptionPane.showMessageDialog(this, msg, "Succes", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String msg) {
        setStatus("ERREUR: " + msg);
        JOptionPane.showMessageDialog(this, msg, "Erreur", JOptionPane.ERROR_MESSAGE);
    }

    // ── Entry point ───────────────────────────────────────────────
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(() -> new AgenceGUI1().setVisible(true));
    }
}