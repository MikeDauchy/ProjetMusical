package affichage.informationsClient.listCellRenderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import donnees.Client;
import donnees.reservations.Reservation;

public class ReservationsListCellRenderer implements ListCellRenderer{


	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list,((Reservation)value).getIdReservation(), index,
				isSelected, cellHasFocus);
		return renderer;
	}

}