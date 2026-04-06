package TP5;
import java.awt.*;
import java.awt.event.*;

/**
 * Classe Dessin - dérivée de Canvas
 *
 * Gère le dessin d'une forme (Rectangle ou Ellipse) dont :
 * - Le type, la couleur et le remplissage sont lus depuis l'objet Planche
 * - La dimension est définie par un tracé souris bouton enfoncé
 *   (mousePressed → mouseDragged)
 * - Chaque nouvelle forme efface la précédente (repaint)
 */
public class Dessin extends Canvas implements MouseListener, MouseMotionListener {

    private Planche planche; // référence vers la fenêtre principale

    // Coordonnées de la forme courante
    private int x, y, largeur, hauteur;
    private int startX, startY;

    // Propriétés de la forme au moment du clic
    private String forme;
    private Color  couleur;
    private boolean remplissage;

    // Indique si une forme doit être dessinée
    private boolean dessiner = false;

    public Dessin(Planche planche) {
        this.planche = planche;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    // ── Dessin ────────────────────────────────────────────────

    @Override
    public void paint(Graphics g) {
        if (!dessiner) return;

        g.setColor(couleur);

        // Normaliser les coordonnées (dessin dans n'importe quel sens)
        int rx = Math.min(x, startX);
        int ry = Math.min(y, startY);
        int rw = Math.abs(largeur);
        int rh = Math.abs(hauteur);

        if (forme.equals("Rectangle")) {
            if (remplissage) g.fillRect(rx, ry, rw, rh);
            else             g.drawRect(rx, ry, rw, rh);
        } else { // Ellipse
            if (remplissage) g.fillOval(rx, ry, rw, rh);
            else             g.drawOval(rx, ry, rw, rh);
        }
    }

    // ── Événements souris ────────────────────────────────────

    @Override
    public void mousePressed(MouseEvent e) {
        // Capture le point de départ et les options courantes
        startX = e.getX();
        startY = e.getY();
        x = startX;
        y = startY;
        largeur = 0;
        hauteur = 0;

        // Lire les options depuis Planche au moment du clic
        forme       = planche.getForme();
        couleur     = planche.getCouleur();
        remplissage = planche.isRemplissage();

        dessiner = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // Mettre à jour la taille de la forme pendant le glissement
        x = e.getX();
        y = e.getY();
        largeur = x - startX;
        hauteur = y - startY;
        dessiner = true;
        repaint(); // redessine → efface l'ancienne forme et trace la nouvelle
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // La forme est définitivement tracée à la fin du glissement
    }

    // ── Méthodes MouseListener non utilisées ─────────────────
    @Override public void mouseClicked(MouseEvent e)  {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}
    @Override public void mouseMoved(MouseEvent e)    {}
}
