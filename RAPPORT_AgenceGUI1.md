# Rapport de Conception - Interface AgenceGUI1

## 1. Objectif de l'interface

L'interface `AgenceGUI1` a ete construite pour fournir une application desktop Swing moderne permettant de:
- visualiser le parc de voitures,
- filtrer les vehicules,
- enregistrer une location,
- restituer un vehicule,
- ajouter une nouvelle voiture,
- suivre les statistiques globales (parc, louees, disponibles).

Le but est de separer clairement la presentation UI et les actions metier sur l'objet `Agence`.

## 2. Approche de construction

La creation de l'interface suit une approche modulaire:

1. Initialiser les donnees metier (`Agence` + donnees de demo).
2. Definir un theme visuel centralise (couleurs, polices, UI defaults).
3. Construire la fenetre principale par zones (Nord, Centre, Sud).
4. Construire chaque zone via des methodes dediees (`buildHeader`, `buildCenter`, etc.).
5. Brancher les actions utilisateur sur les methodes metier (`louerVoitureAction`, `ajouterVoitureAction`, ...).
6. Mettre a jour les vues apres chaque action (`refreshTable`, `actualiserComboVoitures`, `updateStatsChips`).

Cette decomposition rend le code plus lisible, evolutif et testable.

## 3. Structure globale de la fenetre

La fenetre principale herite de `JFrame` et utilise un `BorderLayout`:

- **NORTH**: header (branding + statistiques).
- **CENTER**: zone principale avec un `JSplitPane` horizontal.
  - gauche: filtres + table des voitures,
  - droite: onglets d'actions.
- **SOUTH**: barre de statut.

## 4. Theme visuel

Le style est defini par:
- une palette sombre (`BG_DEEP`, `BG_CARD`, `BG_PANEL`),
- une couleur d'accent or (`ACCENT_GOLD`),
- des couleurs fonctionnelles (`SUCCESS`, `DANGER`, `INFO`),
- des polices specialisees selon le contexte (`FONT_TITLE`, `FONT_BODY`, etc.).

La methode `applyGlobalUIDefaults()` configure les couleurs globales Swing (`UIManager`) pour harmoniser les composants natifs (panel, combobox, boutons, tabs, etc.).

## 5. Layouts utilises

Les layouts utilises ont ete choisis selon la nature du contenu:

- `BorderLayout`: structure principale et conteneurs majeurs.
- `FlowLayout`: alignement horizontal simple (barre de filtres, header gauche/droite).
- `GridLayout`: composition verticale des chips de stats (titre + valeur).
- `GridBagLayout`: formulaires de saisie (onglets Location et Ajouter voiture).
- `JSplitPane`: separation redimensionnable gauche/droite.

## 6. Panels et zones fonctionnelles

### 6.1 Header (`buildHeader`)

Le header est un `JPanel` avec `paintComponent` surcharge pour dessiner:
- un degrade horizontal,
- une ligne d'accent en bas.

Il contient:
- **zone gauche**: logo + titres,
- **zone droite**: chips de statistiques construites par `statChip(...)`.

### 6.2 Zone gauche (`buildLeftPanel`)

Composee de:
- `buildFilterBar()`: filtres marque/annee/prix + boutons d'action,
- `buildCarTablePanel()`: table principale des vehicules dans un `JScrollPane`.

La table (`JTable`) utilise:
- un `DefaultTableModel` non editable,
- un renderer personnalise pour alterner les lignes,
- un statut colore (disponible/louee),
- un style d'en-tete personnalise.

### 6.3 Zone droite (`buildRightPanel`)

Utilise un `JTabbedPane` avec 3 onglets:

1. **Location** (`buildLocationTab`)
   - formulaire client + vehicule,
   - bouton louer,
   - bouton restituer.

2. **Ajouter voiture** (`buildAddCarTab`)
   - formulaire vehicule,
   - bouton d'enregistrement.

3. **Locations en cours** (`buildActiveRentalsTab`)
   - table des locations,
   - bouton de rafraichissement.

### 6.4 Barre de statut (`buildStatusBar`)

Affiche:
- l'etat courant de l'application (message utilisateur),
- la version de l'application.

## 7. Logique metier connectee a l'UI

Les actions sont centralisees dans des methodes dediees:

- `ajouterVoitureAction()`
- `louerVoitureAction()`
- `rendreVoitureAction()`
- `appliquerFiltreCombine()`
- `afficherVoituresLouees()`

Chaque action valide les entrees, execute l'operation sur `Agence`, puis met a jour l'interface.

## 8. Synchronisation des vues

La coherence UI est assuree par 3 methodes cle:

- `refreshTable(...)`: recharge la table principale selon filtre.
- `actualiserComboVoitures()`: met a jour la liste de vehicules selectionnables.
- `updateStatsChips()`: met a jour les stats du header (PARC, LOUEES, DISPO).

Ainsi, apres un ajout, une location ou une restitution, toutes les zones importantes sont synchronisees.

## 9. Helpers de style et reutilisabilite

Pour eviter la duplication, des methodes utilitaires sont utilisees:

- `cardPanel()`
- `newGbc()`
- `sectionTitle(...)`
- `addRow(...)`
- `inputField(...)`
- `actionButton(...)`
- `iconButton(...)`
- `styleCombo(...)`
- `styleScrollBar(...)`
- `setStatus(...)`, `showSuccess(...)`, `showError(...)`

Ce design facilite les evolutions futures sans casser l'architecture.

## 10. Sequence de demarrage

La methode `main(...)`:
1. applique le Look and Feel,
2. lance l'interface sur l'EDT via `SwingUtilities.invokeLater(...)`.

Le constructeur:
1. prepare l'etat metier,
2. construit tous les composants,
3. initialise les donnees visuelles,
4. centre la fenetre.

## 11. Conclusion

`AgenceGUI1` a ete creee comme une interface Swing modulaire, thematisee et orientee workflow.

Les choix techniques (decoupage en builders, layouts adaptes, helpers de style, refresh centralise) permettent:
- une bonne lisibilite,
- une maintenance simple,
- une experience utilisateur fluide,
- une mise a jour coherente des donnees affichees.
