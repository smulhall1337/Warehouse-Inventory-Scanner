package controller;

import javax.swing.JComponent;

public class FocusGrabber implements Runnable {
	  private JComponent component;

	  public FocusGrabber(JComponent component) {
	    this.component = component;
	  }

	  public void run() {
	    component.grabFocus();
	  }
	}
