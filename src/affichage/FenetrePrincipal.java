package affichage;

import javax.swing.JFrame;

public class FenetrePrincipal extends JFrame{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FenetrePrincipal() {
		super();
		setTitle("Réservation d'une salle");
		getContentPane().add(new ChoixMenuPrincipalPanel(this));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}





}
