package affichage.informationsClient.listCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.sql.SQLException;
import java.text.DateFormat;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import donnees.Client;
import donnees.reservations.Reservation;
import exceptions.accesAuDonnees.ObjetInconnu;

public class ReservationsListCellRenderer implements ListCellRenderer{

	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);
		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list,dateFormat.format(((Reservation)value).getDateDebut()), index,
				isSelected, cellHasFocus);

		try {
			if (!((Reservation)value).getFacture().isEstPaye()) renderer.setForeground(Color.blue);
			else if((((Reservation)value).getFacture().isEstPaye())){
				renderer.setForeground(Color.green);
			}
		} catch (ObjetInconnu | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return renderer;
	}

}
