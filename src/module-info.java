/**
 * 
 */
/**
 * 
 */

module info {
	requires java.desktop;
	// CRUCIAL : "opens" permet à l'AppletViewer (via la réflexion) 
    // d'accéder à vos classes d'applet pour les instancier.
    opens TP5 to java.desktop;
    
    // Optionnel : permet aux autres modules d'utiliser votre package
    exports TP5;
}