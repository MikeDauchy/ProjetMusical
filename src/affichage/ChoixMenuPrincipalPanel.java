package affichage;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ChoixMenuPrincipalPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel labelTitre = new JLabel("Menu");

	private JFrame frame;

	private JDialog dialogConsulterReservation;
	private JFrame dialogEditerReservation;
	private JFrame dialogInformationsClient;


	private JButton boutonConsulterReservation = new JButton("Consulter réservation");
	private JButton boutonEditerReservation = new JButton("Editer réservation");
	private JButton boutonInformationsClient = new JButton(
			"Informations client");

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o= e.getSource();
		if(o ==boutonConsulterReservation ) {
			dialogConsulterReservation.setVisible(true);
		} else if(o== boutonEditerReservation) {
			dialogEditerReservation.setVisible(true);
		} else if(o== boutonInformationsClient) {
			dialogInformationsClient.setVisible(true);
		}else {
			return;
		}
	}

	public ChoixMenuPrincipalPanel(JFrame frame) {
		super();
		this.frame = frame;
		dialogConsulterReservation = new JDialog(frame, "Consulter Reservation", true);
		dialogConsulterReservation.getContentPane().add(new DialogueConsulterReservation(dialogConsulterReservation));
		dialogConsulterReservation.setLocationRelativeTo(frame);
		dialogConsulterReservation.pack();

		boutonConsulterReservation.addActionListener(this);

		JPanel boutonPanel = new JPanel();
		boutonPanel.add(boutonConsulterReservation);
		boutonPanel.add(boutonEditerReservation);
		boutonPanel.add(boutonInformationsClient);



		setLayout( new BoxLayout(this, BoxLayout.Y_AXIS));

		labelTitre.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(labelTitre);
		add(boutonPanel);

		setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

		labelTitre.setForeground(Color.blue);
	}


}
