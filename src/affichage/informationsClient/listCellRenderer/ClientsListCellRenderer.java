package affichage.informationsClient.listCellRenderer;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import donnees.Client;

public class ClientsListCellRenderer implements ListCellRenderer{


	protected DefaultListCellRenderer defaultRenderer = new DefaultListCellRenderer();

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JLabel renderer = (JLabel) defaultRenderer.getListCellRendererComponent(list,((Client)value).getNom(), index,
				isSelected, cellHasFocus);
		return renderer;
	}


}
