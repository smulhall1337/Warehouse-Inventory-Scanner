import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;

public class ItemsInPalletPanel extends JPanel {

	private Dimension pref = new Dimension(320, 400);
	private Dimension iScrollSize = new Dimension(285, 240);
	private Dimension topPanelSize = new Dimension(285, 160);
	private static JScrollPane itemScroll = new JScrollPane();
	private static Container itemCont = new Container();
	private static ItemPanel currentItemPanel = new ItemPanel();
	private JPanel topPanel;
	private JList itemJList;
	private String[][] itemList;
	private ArrayList<String[][]> allItems = new ArrayList<String[][]>();
	
	/**
	 * Create the panel.
	 */
	public ItemsInPalletPanel() {
		this.setPreferredSize(pref);
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		this.setBorder(new TitledBorder(null, "Items On Pallet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	
		
		topPanel = new JPanel();
		topPanel.setSize(topPanelSize);
		topPanel.setMaximumSize(topPanelSize);
		topPanel.setMinimumSize(topPanelSize);
		this.add(topPanel);
		topPanel.add(currentItemPanel);
		
		itemJList = new JList();
		
		itemJList.setSelectedIndex(0);		
		itemJList.setSize(iScrollSize);
		itemJList.setMaximumSize(iScrollSize);
		itemJList.setMinimumSize(iScrollSize);
		itemJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(itemJList);
		
		itemCont.setLayout(new BoxLayout(itemCont, BoxLayout.Y_AXIS));		
		
		itemScroll = new JScrollPane(itemJList);
		itemScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(itemScroll);	
		itemScroll.setSize(285, 360);
		itemScroll.setPreferredSize(iScrollSize);
		itemScroll.setMinimumSize(iScrollSize);
		itemScroll.setMaximumSize(iScrollSize);
		itemScroll.setLocation(410, 25);
			
	}
	
	public static void addItem() {
		//itemCont.add(new ItemPanel());
		//itemScroll.setViewportView(itemCont);		
		
		
	}//addItem() end
	
	public static void addItem(String itemID) {
		
	}//addItem(itemID) end

	public static ItemPanel getItemPanel() {
		return currentItemPanel;
	}
	
	
	

}//Class end
