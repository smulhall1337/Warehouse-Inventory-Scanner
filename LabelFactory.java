package sandbox_v1;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeException;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.output.OutputException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

/**
 * Created by Jon on 3/29/2017.
 */
public abstract class LabelFactory {
    private static ArrayList<BufferedImage> barcodes = new ArrayList<>();
    private static HashMap<String, BufferedImage> barcodeLabels = populateBarcodeLabels();
    private static BufferedImage label ;


    private static HashMap<String, BufferedImage> populateBarcodeLabels() {
        HashMap<String, BufferedImage> labels = new HashMap<>();
        String labelKey;

        //Store the img of the order number label
        labelKey = "OrderNumber";
        labels.put(labelKey, createBarLabel("Order Number:"));

        return labels;
    }

    private static BufferedImage createBarLabel(String text) {
        //String text = "Order Number:";
        BufferedImage img = new BufferedImage(1,1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();
        Font font = new Font("Times New Roman", Font.PLAIN, 10);
        FontMetrics fm = g.getFontMetrics();
        g.setFont(font);
        //Update width and height of current text
        int width = fm.stringWidth(text);
        int height = fm.getHeight();
        //g.dispose();
        //Label creation
        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        g = img.createGraphics();
        g.setFont(font);
        fm = g.getFontMetrics();
        g.setBackground(Color.WHITE);
        g.setColor(Color.BLACK);
        g.drawString(text, 0, fm.getAscent());
        try {
            ImageIO.write(img, "png", new File("barcodeLabel.png"));
        } catch (IOException e) {

        }
        return img;
    }

    public static Barcode createBarcode(String text) {
        //BufferedImage img = new BufferedImage(60, 5, BufferedImage.TYPE_INT_RGB);
        Barcode barcode = null;
        try {
            barcode = BarcodeFactory.createCode128(text);
            barcode.setBarHeight(30);
            barcode.setBarWidth(2);
            //Graphics2D g = (Graphics2D) img.getGraphics();
            //barcode.draw(g, 0, 0);
        } catch (BarcodeException e) {
            e.printStackTrace();
        }
        return barcode;
    }

    public static void createLabel(String orderNum, String palletID) {
        final int ORDER_NUM_LBL_X = 0;
        final int ORDER_NUM_BARC_X = 0;
        final int ORDER_NUM_BARC_Y = 20;

        final int PALLET_ID_LBL_X = 0;
        final int PALLET_ID_LBL_Y = 85;
        final int PALLET_ID_BARC_X = 0;
        final int PALLET_ID_BARC_Y = 90;

        int x_position= 0;
        int y_position = 0;
        label = new BufferedImage(384, 576, BufferedImage.TYPE_INT_RGB);

        Font font = new Font("Times New Roman", Font.PLAIN, 16);
        Graphics2D g = label.createGraphics();
        FontMetrics fm;
        Barcode barcode;

        try {
            //Set graphics properties
            g.setFont(font);
            fm = g.getFontMetrics();
            System.out.print(fm.getHeight());

            //Draw Order Number barcode title
            g.drawString("Order Number:", ORDER_NUM_LBL_X, fm.getAscent());
            //Create and draw barcode
            barcode = createBarcode(orderNum);
            barcode.draw(g, ORDER_NUM_BARC_X, ORDER_NUM_BARC_Y);

            //Draw Pallet ID barcode title
            g.drawString("Pallet ID:", PALLET_ID_LBL_X, PALLET_ID_LBL_Y);
            //Create and draw barcode
            createBarcode("123456").draw(g, PALLET_ID_BARC_X, PALLET_ID_BARC_Y);


            //Write label to file
            ImageIO.write(label, "png", new File("result.png"));
        } catch (OutputException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
