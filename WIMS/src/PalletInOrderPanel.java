import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;

public class PalletInOrderPanel extends JPanel {

	private String[][] allPalletsList;
	private Container palletCont = new Container();
	private Dimension pref = new Dimension(325,700);
	private JButton btnAddPallet;
	
	/**
	 * Create the panel.
	 * @param OW 
	 */
	public PalletInOrderPanel() {
		super();
		this.setBounds(10, 50, 325, 700);
		this.setBorder(new TitledBorder(null, "Pallets In Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setPreferredSize(pref);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		palletCont.setLayout(new BoxLayout(palletCont, BoxLayout.Y_AXIS));
		palletCont.setSize(325, 700);
		
		//JPanel topPanel = new JPanel();
		//topPanel.setLayout(new FlowLayout());
		//this.add(topPanel);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		this.add(centerPanel);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		this.add(bottomPanel);
		
		JScrollPane scrollPane = new JScrollPane();		
		centerPanel.add(scrollPane);
		
		btnAddPallet = new JButton("Add Additional Pallet");
		btnAddPallet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				palletCont.add(new PalletPanel());
				scrollPane.setViewportView(palletCont);
			}
		});
		bottomPanel.add(btnAddPallet);
	}
	
	
	

}
