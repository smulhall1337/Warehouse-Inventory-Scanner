package gui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import java.awt.FlowLayout;
import java.awt.Component;

import javax.swing.Box;

public class SplashScreen {

	private JFrame frame;
	private static final int BUTTON_WIDTH = 300;
	private static final int DEFAULT_WINDOW_WIDTH = 1000;
	private static final int DEFAULT_WINDOW_HEIGHT = 750;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SplashScreen window = new SplashScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SplashScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int xPos = (dim.width/2-frame.getSize().width/2)-(DEFAULT_WINDOW_WIDTH/2);
		int yPos = (dim.height/2-frame.getSize().height/2)-(DEFAULT_WINDOW_HEIGHT/2);
		frame.setBounds(xPos, yPos, DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
		frame.setResizable(false);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 30));
		panel.add(rigidArea_3);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Badger Warehouse Industries\r\n");
		panel_1.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 59));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Warehouse Inventory Management System");
		panel_2.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 50));
		
		JPanel panel_3 = new JPanel();
		frame.getContentPane().add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		int sideDim = (frame.getSize().height-BUTTON_WIDTH)/2;
		Dimension sideAreaDim = new Dimension(sideDim, sideDim);
		Component rigidArea = Box.createRigidArea(sideAreaDim);
		panel_3.add(rigidArea, BorderLayout.WEST);
		
		JButton btnNewButton = new JButton("Continue To Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 47));
		panel_3.add(btnNewButton, BorderLayout.CENTER);
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				LoginScreen loginScreen = new LoginScreen();
				loginScreen.getFrame().setVisible(true);
				frame.dispose();
			}
		});
		
		Component rigidArea_1 = Box.createRigidArea(sideAreaDim);
		panel_3.add(rigidArea_1, BorderLayout.EAST);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 40));
		panel_3.add(rigidArea_2, BorderLayout.SOUTH);
		
		JPanel panel_4 = new JPanel();
		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		
	}

}
