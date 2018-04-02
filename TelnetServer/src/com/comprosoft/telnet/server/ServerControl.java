package com.comprosoft.telnet.server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.comprosoft.telnet.io.TextAreaHandler;


/**
 * Simple GUI interface that displays the server log
 *
 */
public class ServerControl {

	private JFrame frmTelnetServerControl;
	private JLabel lblRunning;
		
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JTextArea ta = setUpLoggers();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerControl window = new ServerControl(ta);
					window.frmTelnetServerControl.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @param ta The text area for all loggers
	 */
	public ServerControl(JTextArea ta) {
		initialize(ta);
		
		//Start the Telnet Server
		Runnable r = new Runnable() {
			public void run() {
				TelnetServer server = new TelnetServer(null);
				server.run();
				serverNotRunning();
			}
		};
		new Thread(r).start();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @param ta The text area for all loggers
	 */
	private void initialize(JTextArea ta) {
		frmTelnetServerControl = new JFrame();
		frmTelnetServerControl.setResizable(false);
		frmTelnetServerControl.setBackground(Color.BLACK);
		frmTelnetServerControl.setTitle("Telnet Server Control");
		frmTelnetServerControl.setBounds(100, 100, 570, 439);
		frmTelnetServerControl.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTelnetServerControl.getContentPane().setLayout(null);
		
		JLabel lblServerControl = new JLabel("Telnet Server Control");
		lblServerControl.setForeground(new Color(255, 127, 80));
		lblServerControl.setFont(new Font("Times New Roman", Font.BOLD, 26));
		lblServerControl.setHorizontalAlignment(SwingConstants.CENTER);
		lblServerControl.setBounds(124, 11, 314, 35);
		frmTelnetServerControl.getContentPane().add(lblServerControl);
		
		JLabel lblStatus = new JLabel("Status:");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblStatus.setBounds(200, 57, 89, 35);
		frmTelnetServerControl.getContentPane().add(lblStatus);
		
		lblRunning = new JLabel("Running");
		lblRunning.setForeground(new Color(0, 128, 0));
		lblRunning.setHorizontalAlignment(SwingConstants.LEFT);
		lblRunning.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblRunning.setBounds(274, 57, 124, 35);
		frmTelnetServerControl.getContentPane().add(lblRunning);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 138, 542, 226);
		frmTelnetServerControl.getContentPane().add(scrollPane);
		
		JLabel lblLog = new JLabel("Server Log:");
		lblLog.setFont(new Font("Tahoma", Font.ITALIC, 16));
		lblLog.setHorizontalAlignment(SwingConstants.CENTER);
		lblLog.setBounds(221, 113, 119, 27);
		frmTelnetServerControl.getContentPane().add(lblLog);

		scrollPane.setViewportView(ta);
		
		JButton btnHide = new JButton("Hide Window*");
		btnHide.setFont(new Font("Dialog", Font.BOLD, 10));
		btnHide.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmTelnetServerControl.setVisible(false);
			}
		});
		btnHide.setBounds(10, 377, 119, 23);
		frmTelnetServerControl.getContentPane().add(btnHide);
		
		JLabel lblHide = new JLabel("*Hiding window means server will need to be closed from Task Manager");
		lblHide.setFont(new Font("Tahoma", Font.BOLD, 10));
		lblHide.setBounds(136, 381, 428, 14);
		frmTelnetServerControl.getContentPane().add(lblHide);
		
	}
	
	
	/**
	 * Set up all loggers to have an additional handler
	 * 
	 * @return The new text area object to add to the form
	 */
	private static JTextArea setUpLoggers() {
		Logger rootLogger = Logger.getLogger("");
		TextAreaHandler handle = new TextAreaHandler();
		rootLogger.addHandler(handle);
		return handle.getTextArea();
	}
	
	
	/**
	 * Indiate that the server is running
	 */
	public void serverRunning() {
		lblRunning.setForeground(new Color(0, 128, 0));
		lblRunning.setText("Running");
	}
	
	
	/**
	 * Indiate that the server is not running
	 */
	public void serverNotRunning() {
		lblRunning.setForeground(new Color(255,0,0));
		lblRunning.setText("Not Running");
	}
	
}
