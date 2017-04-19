package controller;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JButton;

public class ResizingButton extends JButton {
    int mCurrentSize = 0;
    Font mInitialFont = null;
    int mInitialHeight;

    private static final long serialVersionUID = 1L;

    public ResizingButton(String pString) {
        super(pString);
        init();
    }

    public ResizingButton() {
        super();
        init();
    }

    private void init() {
        mInitialFont = getFont();
    }
    
    @Override
    public void setFont(Font font)
    {
    	mInitialFont = font;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (mInitialHeight == 0) {
            mInitialHeight = getHeight();
        }
        int resizal = this.getHeight() * mInitialFont.getSize() / mInitialHeight;
        if(resizal != mCurrentSize){
            setFont(mInitialFont.deriveFont((float) resizal));
            mCurrentSize = resizal;
        }
        super.paintComponent(g);
    }
}