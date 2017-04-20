import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class ItemPanel extends JPanel{
	private JTextField txtItemNumber;
	private JTextField txtItemQuantity;
	private Dimension pref = new Dimension(175,100);

	public ItemPanel(){
		super();
		this.setBounds(10, 50, 175, 100);
		this.setBorder(new TitledBorder(null, "Item", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setPreferredSize(pref);
		this.setLayout(null);
		createContents();
	}
	
	private void createContents() {
		JLabel lblItemId = new JLabel("Item ID:");
		lblItemId.setBounds(6, 22, 51, 14);
		this.add(lblItemId);
		
		txtItemNumber = new JTextField();
		txtItemNumber.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtItemNumber.setBounds(60, 19, 105, 20);
		this.add(txtItemNumber);
		txtItemNumber.setColumns(10);
		
		txtItemQuantity = new JTextField();
		txtItemQuantity.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				Valid.intInput(evt.getKeyChar(), evt);
			}
		});
		txtItemQuantity.setBounds(130, 50, 35, 20);
		this.add(txtItemQuantity);
		txtItemQuantity.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(60, 53, 53, 14);
		this.add(lblQuantity);
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{txtItemNumber, txtItemQuantity}));
	}
	
	/**
	 * Getter method for txtItemNumber
	 * @return User input
	 */
	public String getItemNumber() {
		String itemNumberInput = txtItemNumber.getText();
		if (Valid.validItemNumber(itemNumberInput))
			return itemNumberInput;
		else
			return "";
	}
	
	/**
	 * Getter method for txtItemQuantity
	 * @return User input
	 */
	public String getItemQuantity() {
		String itemQuantityInput = txtItemQuantity.getText();
		if (Valid.validInt(itemQuantityInput))
			return itemQuantityInput;
		else
			return "";
	}	
	
}//Class end