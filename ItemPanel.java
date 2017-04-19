import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

@SuppressWarnings("serial")
public class ItemPanel extends JPanel{
	private JTextField textField_1;
	private JTextField textField_2;

	public ItemPanel(){
		super();
		this.setBounds(6, 51, 175, 92);
		this.setBorder(new TitledBorder(null, "Items on Pallet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(null);
		createContents();
	}
	
	private void createContents() {
		JLabel lblItemId = new JLabel("Item ID:");
		lblItemId.setBounds(6, 22, 51, 14);
		this.add(lblItemId);
		
		textField_1 = new JTextField();
		textField_1.setBounds(60, 19, 105, 20);
		this.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(130, 50, 35, 20);
		this.add(textField_2);
		textField_2.setColumns(10);
		
		JLabel lblQuantity = new JLabel("Quantity");
		lblQuantity.setBounds(60, 53, 53, 14);
		this.add(lblQuantity);
	}
}
