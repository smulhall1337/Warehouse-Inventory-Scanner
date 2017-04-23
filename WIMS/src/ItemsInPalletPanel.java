import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
	private static JList itemJList;
	private static DefaultListModel listModel;

	
	/**
	 * Create the panel.
	 */
	public ItemsInPalletPanel() {
		//Super and This
		super();
		this.setPreferredSize(pref);
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		this.setBorder(new TitledBorder(null, "Items On Pallet", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	
		itemCont.setLayout(new BoxLayout(itemCont, BoxLayout.Y_AXIS));
		
		//Top Panel
		topPanel = new JPanel();
		topPanel.setSize(topPanelSize);
		topPanel.setMaximumSize(topPanelSize);
		topPanel.setMinimumSize(topPanelSize);
		this.add(topPanel);
		topPanel.add(currentItemPanel);
		
		//List Section
		listModel = new DefaultListModel();
		itemJList = new JList(listModel);
		
		/*
		 * selections is a list of the indexs selected (this  project only allows one though)
		 * selecctionValues is a list of the values selected (this project only allows one though)
		 */
		 ListSelectionListener listSelectionListener = new ListSelectionListener() {
		        public void valueChanged(ListSelectionEvent listSelectionEvent) {
		          boolean adjust = listSelectionEvent.getValueIsAdjusting();
		          if (!adjust) {
		            JList list = (JList) listSelectionEvent.getSource();
		            int selections[] = list.getSelectedIndices();
		            Object selectionValues[] = list.getSelectedValues();
		            for (int i = 0, n = selections.length; i < n; i++) {
		              if (i == 0) {
		                System.out.print("  Selections: ");  //probably dont need this if
		              }
		              System.out.print(selections[i] + "/" + selectionValues[i] + " ");
		              
		            }
		            System.out.println();
		          }
		        }
		      };
		      itemJList.addListSelectionListener(listSelectionListener);

		      MouseListener mouseListener = new MouseAdapter() {
		        public void mouseClicked(MouseEvent mouseEvent) {
		          JList theList = (JList) mouseEvent.getSource();
		          if (mouseEvent.getClickCount() == 2) {
		            int index = theList.locationToIndex(mouseEvent.getPoint());
		            if (index >= 0) {
		              Object o = theList.getModel().getElementAt(index);
		              System.out.println("Double-clicked on: " + o.toString());
		              ScanWindow scanWindow = new ScanWindow(OrderWindow.getManagement(), o.toString());
		              scanWindow.getFrame().setVisible(true);
		            }
		          }
		        }
		      };
		      itemJList.addMouseListener(mouseListener);
		
		itemJList.setSelectedIndex(0);		
		itemJList.setSize(iScrollSize);
		itemJList.setMaximumSize(iScrollSize);
		itemJList.setMinimumSize(iScrollSize);
		itemJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(itemJList);
				
		//Scroll Section
		itemScroll = new JScrollPane(itemJList);
		itemScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(itemScroll);	
		itemScroll.setSize(285, 360);
		itemScroll.setPreferredSize(iScrollSize);
		itemScroll.setMinimumSize(iScrollSize);
		itemScroll.setMaximumSize(iScrollSize);
		itemScroll.setLocation(410, 25);
			
	}//ItemsInPalletPanel


	/*
	 ********************************************************** 
	 ***************************************************Getters
	 **********************************************************
	 */
	public static ItemPanel getCurrentItemPanel() {
		return currentItemPanel;
	}

	public static JList getCurrentList() {
		return itemJList;
	}

	public static DefaultListModel getListModel() {
		return listModel;
	}
	
	
	

}//Class end
