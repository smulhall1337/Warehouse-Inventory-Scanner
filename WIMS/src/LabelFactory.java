package wims_v1;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class is used to construct barcode labels for the WIMS application. 
 * @author Jon Spratt
 * @version 4/22/2017
 */
public abstract class LabelFactory {
	
	/**
	 * Holds the image of the label being constructed
	 */
    private static BufferedImage label;
    
    /**
     * File path to write to
     */
    private static final String LABEL_WRITE_PATH = "";
    /**
     * Starting horizontal position for titles / barcodes
     */
    private static final int START_X = 0;
    /**
     * Vertical distance to the next barcode title
     */
	private static final int NEXT_BARC_TITLE_Y = 85;
	/**
	 * Vertical distance to the next barcode
	 */
	private static final int NEXT_BARC_Y = 75;
	/**
	 * Vertical distance offset for start of item barcodes
	 */
	private static final int ITEM_BARC_START_VERT_OFFSET = 173;
	/**
	 * Vertical distance offset for start of item quantity barcodes
	 */
	private static final int QTY_BARC_START_VERT_OFFSET = 173;
	/**
	 * Horizontal distance offset for item quantity barcodes
	 */
	private static final int QTY_BARC_START_HORIZ_OFFSET = 200;
	/**
	 * Vertical distance offset for the height of a title
	 */
	private static final int TITLE_VERT_OFFSET = 20;
	
	private static final int TOP_RIGHT_X = 200;
	private static final int TOP_RIGHT_ORDER_Y = 0;
	private static final int TOP_RIGHT_PAGE_Y = 20;
	/**
	 * Maximum number of item barcodes per label page
	 */
	private static final int MAX_LABEL_ITEMS = 5;
	
	/**
	 * Create a barcode based on text input
	 * @param text the in-bound text to convert into a barcode
	 * @return returns the barcode that was created
	 */
    private static Barcode createBarcode(String text) {
        Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createCode128(text);
            barcode.setBarHeight(30);
            barcode.setBarWidth(1);
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
        return barcode;
    }
    
    /**
     * Create a label page.	
     * @param orderNum the order number for the label 
     * @param palletID the pallet ID for the label
     * @param items a collection of items to write barcodes for
     * @param pageNum the page # for orders with > 1 page
     * @throws IOException when the image cannot be written to a file
     */
    private static void createPage(String orderNum, String palletID, SortedMap<String, String> items, int pageNum, int totalPages) throws IOException {
    	
    	int titleCount = 0;		// amount of titles; used for multiplying by offset
    	int barcodeCount = 0;	// amount of barcodes; used for multiplying by offset
    	int itemBarcCount = 0;	// amount of item barcodes; used for multiplying by offset
    	
    	String itemQty;		// Hold the quantity of a current item
    	String itemNumber;	// Hold the current item number
    	
    	boolean firstBarcode = true; // Whether or not this is the first item barcode
		Entry<String, String> entry; // Holds an entry of itemNumber => itemQty
    	
        label = new BufferedImage(385, 575, BufferedImage.TYPE_INT_RGB);
        
        Font font = new Font("Times New Roman", Font.BOLD, 16);
        Graphics2D g = label.createGraphics();
        FontMetrics fm;
        Barcode barcode;
        
		try {
			// Set graphics properties
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, label.getWidth(), label.getHeight());
			fm = g.getFontMetrics();
			g.setColor(Color.BLACK);
			
			// Draw top right page number
			g.drawString("Page: " + pageNum + " of " + totalPages, TOP_RIGHT_X, TOP_RIGHT_PAGE_Y);
			// Draw Order Number barcode title
			g.drawString("Order Number:", START_X, fm.getAscent());
			// Create and draw barcode
			barcode = createBarcode(orderNum);
			barcode.draw(g, START_X, (NEXT_BARC_Y * barcodeCount++) + TITLE_VERT_OFFSET);

			// Draw Pallet ID barcode title
			g.drawString("Pallet ID:", START_X, (NEXT_BARC_TITLE_Y * ++titleCount));
			// Create and draw barcode
			barcode = createBarcode(palletID);
			barcode.draw(g, START_X, (NEXT_BARC_Y * barcodeCount++) + TITLE_VERT_OFFSET);

			// Draw Item barcodes and titles
			g.drawString("Item Numbers:", START_X, (NEXT_BARC_TITLE_Y * ++titleCount));
			g.drawString("Item Qty:", QTY_BARC_START_HORIZ_OFFSET, (NEXT_BARC_TITLE_Y * titleCount));
		
			for (Iterator<Entry<String, String>> it = items.entrySet().iterator(); it.hasNext();) {
				entry = it.next();

				itemNumber = entry.getKey();
				itemQty = entry.getValue();
				barcode = createBarcode(itemNumber);

				if (!firstBarcode) {
					barcode.draw(g, START_X, ITEM_BARC_START_VERT_OFFSET + (NEXT_BARC_Y * ++itemBarcCount));
					barcode = createBarcode(itemQty);
					barcode.draw(g, QTY_BARC_START_HORIZ_OFFSET,
							QTY_BARC_START_VERT_OFFSET + (NEXT_BARC_Y * itemBarcCount));
				} else {
					barcode.draw(g, START_X, ITEM_BARC_START_VERT_OFFSET);
					barcode = createBarcode(itemQty);
					barcode.draw(g, QTY_BARC_START_HORIZ_OFFSET, QTY_BARC_START_VERT_OFFSET);
					firstBarcode = false;
				}
			}
		} catch (OutputException e) {
			e.printStackTrace();
		}
    	//Write label to file
        ImageIO.write(label, "png", new File("Order_"+ orderNum +"_Label_Page_"+ pageNum +".png"));
    }
    
    /**
     * Create a barcode label
     * @param orderNum the order number to be printed on the labels
     * @param palletID the pallet ID to be printed on the labels
     * @param items a collection of items with quantities to be printed on the labels
     */
    public static void createLabel(String orderNum, String palletID, SortedMap<String, String> items) {
    	
    	int totalPages = (items.size() > MAX_LABEL_ITEMS) ? (items.size() + MAX_LABEL_ITEMS - 1) / 5 : 1;
    	int pageNum = 1;	// The current page number
    	
    	Entry<String, String> entry = null;		// Holds the current itemNum/itemQty entry
    	// a slice of the collection of items to apply to a page
    	SortedMap<String, String> itemsSlice = new TreeMap<String, String>();
    	// A collection of pages to create for a pallet with > MAX_LABEL_ITEMS
    	List<SortedMap<String,String>> pages = new ArrayList<SortedMap<String,String>>();
    	
    	Iterator<Entry<String, String>> it = items.entrySet().iterator();
    	
    	while (it.hasNext()) {
    		entry = it.next();
    		
    		if (itemsSlice.size() < MAX_LABEL_ITEMS) {	// Less than maximum items per label
        		itemsSlice.put(entry.getKey(), entry.getValue());
    		}
    		else if (itemsSlice.size() == MAX_LABEL_ITEMS) { // Full page of items 
    			pages.add(itemsSlice);
    			itemsSlice = new TreeMap<String, String>();
    			itemsSlice.put(entry.getKey(), entry.getValue());
    		}
    	}
    	
    	// If < MAX_LABEL_ITEMS still need to be paged. Page them. 
    	if (!itemsSlice.isEmpty()) {
    		pages.add(itemsSlice);
    	}
    	
    	// For each page create a label page
    	for (SortedMap<String, String> sm : pages) {
    		try {
				createPage(orderNum, palletID, sm, pageNum++, totalPages);
			} catch (IOException e) {
				e.printStackTrace();
			}
    	}
    }
}
