import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.ScrollPaneConstants;
import javax.swing.AbstractListModel;

public class PalletsInOrderPanel extends JPanel {
	
	private Dimension pref = new Dimension (370, 280);
	private Dimension pScrollSize = new Dimension(300, 150);
	private Dimension topPanelSize = new Dimension(370, 100);
	private static JScrollPane palletScroll;
	private static Container palletCont = new Container();
	private static PalletPanel currentPalletPanel = new PalletPanel();
	private JPanel topPanel;
	private static JList palletJList;
	private static DefaultListModel listModel;
	
	/**
	 * Create the panel.
	 *
	 */
	public PalletsInOrderPanel() {
		//Super and This
		super();
		this.setPreferredSize(pref);
		this.setMinimumSize(pref);
		this.setMaximumSize(pref);
		this.setBorder(new TitledBorder(null, "Pallets In Order", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));		
		palletCont.setLayout(new BoxLayout(palletCont, BoxLayout.Y_AXIS));	
		
		//Top Panel
		topPanel = new JPanel();
		topPanel.setSize(topPanelSize);
		topPanel.setMaximumSize(topPanelSize);
		topPanel.setMinimumSize(topPanelSize);
		this.add(topPanel);
		topPanel.add(currentPalletPanel);
		
		//List Section
		listModel = new DefaultListModel();
		palletJList = new JList(listModel);
		
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
	              enableItemPanel();
	            }
	            System.out.println();
	          }
	        }
	      };
	      palletJList.addListSelectionListener(listSelectionListener);

	      MouseListener mouseListener = new MouseAdapter() {
	        public void mouseClicked(MouseEvent mouseEvent) {
	          JList theList = (JList) mouseEvent.getSource();
	          if (mouseEvent.getClickCount() == 2) {
	            int index = theList.locationToIndex(mouseEvent.getPoint());
	            if (index >= 0) {
	              Object o = theList.getModel().getElementAt(index);
	              System.out.println("Double-clicked on: " + o.toString());
	              
	              
	              
	              
	              if (OrderWindow.getFoundOrder()) {//if the Order is already in db
	            	PalletWindow palletWindow = new PalletWindow(o.toString()); //call pallet window when double clicked
	            	palletWindow.getFrame().setVisible(true);
	              }
	            }
	          }
	        }
	      };
	      palletJList.addMouseListener(mouseListener);
		
		palletJList.setSelectedIndex(0);		
		palletJList.setSize(pScrollSize);
		palletJList.setMaximumSize(pScrollSize);
		palletJList.setMinimumSize(pScrollSize);
		palletJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.add(palletJList);
		
		
		
		palletJList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (renderer instanceof JLabel && value instanceof Pallet) {
                    // Here value will be of the Type 'CD'
                    ((JLabel) renderer).setText("Pallet ID: " + ((Pallet) value).getID() + " stored in location " + ((Pallet) value).getSubLocation());
                }
                return renderer;
            }
        });
		
		
		
		
		
		
		//Scroll Section
		palletScroll = new JScrollPane(palletJList);
		palletScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(palletScroll);				
		palletScroll.setSize(342, 235);
		palletScroll.setPreferredSize(pScrollSize);
		palletScroll.setMinimumSize(pScrollSize);
		palletScroll.setMaximumSize(pScrollSize);
		palletScroll.setLocation(5, 140);
	}//PalletInOrderPanel end

	
	
	/*
	 ********************************************************** 
	 ***************************************************Getters
	 **********************************************************
	 */
	public static PalletPanel getPalletPanel() {
		return currentPalletPanel;
	}
	
	public static DefaultListModel getListModel() {
		return listModel;
	}
	
	public static JList getCurrentList() {
		return palletJList;
	}
	
	public void enableItemPanel() {
		ItemPanel.enableTxtItemNumber();
		
	}
	

}//PalletInOrderPanel end
