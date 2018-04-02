package com.comprosoft.telnet.io;

import java.awt.Color;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


/**
 * Display the contents of the log out to a textarea
 *
 */
public class TextAreaHandler extends Handler {


	 private JTextArea textArea = new JTextArea();

	 public JTextArea getTextArea() {
		 this.textArea.setBackground(Color.white);
		 this.textArea.setForeground(Color.black);
		 this.textArea.setEditable(false);
		 
		 return this.textArea;
	 }
	
	@Override
	public void close() throws SecurityException {
		//Does nothing
	}

	@Override
	public void flush() {
		textArea.setText("");
	}

	@Override
	public void publish(LogRecord record) {
		SwingUtilities.invokeLater(new Runnable() {
			
            @Override
            public void run() {
            	
            	//Format the date
            	Date date = new Date(record.getMillis());
            	DateFormat formatter = new SimpleDateFormat("MMM d, yyyy h:mm:ss a");
            	String dateFormatted = formatter.format(date);
            	
                StringWriter text = new StringWriter();
                PrintWriter out = new PrintWriter(text);
                out.print(textArea.getText());
                out.printf("[%s] %s: %s.%s [Thread %d]\n      -> %s\n", record.getLevel(),
                        dateFormatted, record.getSourceClassName(),
                        record.getSourceMethodName(), record.getThreadID(), 
                        record.getMessage());
                textArea.setText(text.toString());
            }

        });
	}

}
