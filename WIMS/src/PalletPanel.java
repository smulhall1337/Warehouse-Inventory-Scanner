import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.event.KeyAdapter;

public class PalletPanel extends JPanel {

	private Container itemCont = new Container();
	private Dimension pref = new Dimension(275,300);
	private JTextField txtPalletID;
	private JButton btnAddItem, btnCheck;
	private boolean found;
	
	// TODO Scrolling is messed up ask Paul about it
	
	/**
	 * Create the panel.
	 */
	public PalletPanel() {
		super();
		this.setBounds(10, 50, 275, 300);
		this.setBorder(new TitledBorder(null, "Pallet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(pref);
		
		itemCont.setLayout(new BoxLayout(itemCont, BoxLayout.Y_AXIS));
		
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		this.add(topPanel);
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new BorderLayout());
		this.add(centerPanel);
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());
		this.add(bottomPanel);
		
		//TOP SECTION
		JLabel lblPalletID = new JLabel("Pallet ID: ");
		txtPalletID = new JTextField();
		txtPalletID.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtPalletID.setColumns(15);	
		btnCheck = new JButton("Check");
		btnCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = txtPalletID.getText();
				if (Valid.validItemNumber(input)) {
					try {
						found = SQL_Handler.palletInDB(input);
					} catch (Exception ee) {
						JOptionPane.showMessageDialog(centerPanel,
								"Could not connect the database, \nPlease check the connection and try again");
					}
					if (found) { //if a pallet with that id is already in the data base
						JOptionPane.showInternalMessageDialog(centerPanel,
								"A pallet with that ID already exists, \nPlease try a different ID Number");
						txtPalletID.setText("");
						txtPalletID.requestFocus();
					} 
					else {
						btnAddItem.setEnabled(true);
						btnCheck.setEnabled(false);
						txtPalletID.setEditable(false);
					}//found end
				}//validID end
				else {
					JOptionPane.showInternalMessageDialog(centerPanel,
							"Nothing was entered, \nPlease try an actual ID Number");
				}
			}//actionPerformed end
		});//btnCheck Listener end
		
		topPanel.add(lblPalletID);
		topPanel.add(txtPalletID);
		topPanel.add(btnCheck);
		
		
		//MIDDLE SECTION
		JScrollPane scrollPane = new JScrollPane();
		centerPanel.add(scrollPane, BorderLayout.NORTH);		
		
		//BOTTOM SECTION
		btnAddItem = new JButton("Add Additional Item");
		btnAddItem.setEnabled(false);
		btnAddItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemCont.add(new ItemPanel());				
				scrollPane.setViewportView(itemCont);
			}
		});
		bottomPanel.add(btnAddItem);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtPalletID, scrollPane, topPanel}));
	}
	

}//Class end
