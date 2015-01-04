package affichage;

import java.awt.BorderLayout;
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

import affichage.consulter_reservations.DialogueConsulterReservation;
import affichage.informationsClient.jDialog.DialogueInformationsClient;

public class ChoixMenuPrincipalPanel extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel labelTitre = new JLabel("Menu");

	private JFrame frame;

	private JDialog dialogConsulterReservation;
	private JFrame dialogEditerReservation;
	private JDialog dialogInformationsClient;


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

	public ChoixMenuPrincipalPanel(JFrame frame) throws Exception {
		super();
		this.frame = frame;
		dialogConsulterReservation = new JDialog(frame, "Consulter Reservation", true);
		dialogConsulterReservation.getContentPane().add(new DialogueConsulterReservation(dialogConsulterReservation), BorderLayout.CENTER);
		dialogConsulterReservation.setLocationRelativeTo(frame);
		dialogConsulterReservation.pack();

		dialogInformationsClient = new JDialog (frame, "Informations client",true);
		dialogInformationsClient.getContentPane().add(new DialogueInformationsClient(dialogInformationsClient), BorderLayout.CENTER);
		dialogInformationsClient.setLocationRelativeTo(frame);
		dialogInformationsClient.pack();

		boutonConsulterReservation.addActionListener(this);
		boutonInformationsClient.addActionListener(this);

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

