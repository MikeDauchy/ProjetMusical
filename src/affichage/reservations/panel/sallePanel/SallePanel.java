package affichage.reservations.panel.sallePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SallePanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private JButton salleButton;
	private JButton supprimerSalleButton;
	
	public SallePanel(){
		super();
		this.addMouseListener(new Listner(this));
		this.setLayout(new  BorderLayout());
		
		salleButton = new JButton("Infos");
		supprimerSalleButton = new JButton("X");
		salleButton.setVisible(false);
		supprimerSalleButton.setVisible(false);
		supprimerSalleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salleButton.setVisible(false);
				supprimerSalleButton.setVisible(false);
			}
		});
		this.add(salleButton, BorderLayout.CENTER);
		this.add(supprimerSalleButton, BorderLayout.NORTH);
		
	}
	
	class Listner implements MouseListener{	
		
		SallePanel p;
		
		public Listner(SallePanel p) {
			super();
			this.p = p;
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			this.p.salleButton.setVisible(true);
			this.p.supprimerSalleButton.setVisible(true);
			p.validate();
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
