package wims_v1;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.File;

/**
 * Created by Jon on 3/29/2017.
 */
public abstract class LabelFactory {
    private static List<String> barcodeTitles = populateBarcodeTitles();
    private static BufferedImage label ;

    private static final int START_Y = 20;
    private static final int START_X = 0;
	private static final int NEXT_BARC_TITLE_Y = 85;
	private static final int NEXT_BARC_Y = 90;
	private static final int QTY_BARC_START_VERT_OFFSET = 180;
	private static final int QTY_BARC_START_HORIZ_OFFSET = 225;	//NEEDS TUNE
	private static final int NO_TITLE_VERT_OFFSET = 20;
	
    private static List<String> populateBarcodeTitles() {
    	barcodeTitles = new ArrayList<String>();
    	barcodeTitles.add("Order Number:");
    	barcodeTitles.add("Pallet ID");
    	barcodeTitles.add("Item Number");
    	return barcodeTitles;
    }

    public static Barcode createBarcode(String text) {
        Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createCode128(text);
            barcode.setBarHeight(30);
            barcode.setBarWidth(2);
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
        return barcode;
    }
    
    public static void createLabel(String orderNum, String palletID, Map<String, String> items) {
    	int titleCount = 0;
    	int barcodeCount = 0;
    	int qtyBarcCount = 0;
    	
        label = new BufferedImage(390, 576, BufferedImage.TYPE_INT_RGB);
        
        Font font = new Font("Times New Roman", Font.BOLD, 16);
        Graphics2D g = label.createGraphics();
        FontMetrics fm;
        Barcode barcode;

        try {
            //Set graphics properties
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, label.getWidth(), label.getHeight());
            fm = g.getFontMetrics();
            
            
            //Draw Order Number barcode title
            g.setColor(Color.BLACK);
            //g.drawImage(barcodeLabels.get("OrderNumber"), ORDER_NUM_LBL_X, 0, null);
            g.drawString("Order Number:", START_X, fm.getAscent());
            //Create and draw barcode
            barcode = createBarcode(orderNum);
            barcode.draw(g, START_X, START_Y);

            //Draw Pallet ID barcode title
            g.drawString("Pallet ID:", START_X, (NEXT_BARC_TITLE_Y * ++titleCount));
            //Create and draw barcode
            barcode = createBarcode(palletID);
            barcode.draw(g, START_X, (NEXT_BARC_Y * ++barcodeCount));
            
            //Draw Item barcodes and title
            g.drawString("Item Numbers:", START_X, (NEXT_BARC_TITLE_Y * ++titleCount));
            g.drawString("Item Qty:", QTY_BARC_START_HORIZ_OFFSET, (NEXT_BARC_TITLE_Y * titleCount));
            boolean firstBarcode = true;
            Entry<String, String> entry;
            for (Iterator<Entry<String, String>> it = items.entrySet().iterator(); it.hasNext();) {
            	entry = it.next();
            	String itemNumber = entry.getKey();
            	String itemQty = entry.getValue();
            	barcode = createBarcode(itemNumber);
            	if (!firstBarcode) {
            		barcode.draw(g, START_X, (NEXT_BARC_Y * ++barcodeCount - NO_TITLE_VERT_OFFSET));
            		barcode = createBarcode(itemQty);
            		barcode.draw(g, QTY_BARC_START_HORIZ_OFFSET, (QTY_BARC_START_VERT_OFFSET + NEXT_BARC_Y * ++qtyBarcCount - NO_TITLE_VERT_OFFSET));
            	}
            	else {
            		barcode.draw(g, START_X, (NEXT_BARC_Y * ++barcodeCount));
            		barcode = createBarcode(itemQty);
            		barcode.draw(g, QTY_BARC_START_HORIZ_OFFSET, QTY_BARC_START_VERT_OFFSET);
            		firstBarcode = false;
            	}	
            }         
            //Write label to file
            ImageIO.write(label, "png", new File("result.png"));
        } catch (OutputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
