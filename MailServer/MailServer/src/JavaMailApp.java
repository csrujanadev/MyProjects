/*	Lab:2
*	Program:JavaMAil APP
*/

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFileChooser;

public class JavaMailApp extends Frame {
	String username = "...give ur ucmmail id..";
	String password = "....give ur password..";
	
	JFileChooser fc;
	/* The stuff for the GUI. */
	private Button btSend = new Button("Send");
	private Button btClear = new Button("Clear");
	private Button btQuit = new Button("Quit");
	private Label serverLabel = new Label("Local mailserver:");
	private TextField serverField = new TextField("", 40);
	private Label fromLabel = new Label("From:");
	private TextField fromField = new TextField("", 40);
	private Label toLabel = new Label("To:");
	private TextField toField = new TextField("", 40);
	private Label subjectLabel = new Label("Subject:");
	private TextField subjectField = new TextField("", 40);
	private Label ccLabel = new Label("cc:");
	private TextField ccField = new TextField("", 40);
	private Label bccLabel = new Label("bcc:");
	private TextField bccField = new TextField("", 40);
	private Label attachmentLabel = new Label("attatchment:");

	private Label messageLabel = new Label("Message:");
	private TextArea messageText = new TextArea(10, 40);

	/**
	 * Create a new MailClient window with fields for entering all the relevant
	 * information (From, To, Subject, and message).
	 */
	public JavaMailApp() {
		super("Java Mailclient");
		fc = new JFileChooser();
		/*
		 * Create panels for holding the fields. To make it look nice, create an
		 * extra panel for holding all the child panels.
		 */
		Panel serverPanel = new Panel(new BorderLayout());
		Panel fromPanel = new Panel(new BorderLayout());
		Panel toPanel = new Panel(new BorderLayout());
		Panel subjectPanel = new Panel(new BorderLayout());
		Panel messagePanel = new Panel(new BorderLayout());
		Panel ccPanel = new Panel(new BorderLayout());
		Panel bccPanel = new Panel(new BorderLayout());
		serverPanel.add(serverLabel, BorderLayout.WEST);
		serverPanel.add(serverField, BorderLayout.CENTER);
		fromPanel.add(fromLabel, BorderLayout.WEST);
		fromPanel.add(fromField, BorderLayout.CENTER);
		toPanel.add(toLabel, BorderLayout.WEST);
		toPanel.add(toField, BorderLayout.CENTER);
		subjectPanel.add(subjectLabel, BorderLayout.WEST);
		subjectPanel.add(subjectField, BorderLayout.CENTER);
		ccPanel.add(ccLabel, BorderLayout.WEST);
		ccPanel.add(ccField, BorderLayout.CENTER);
		bccPanel.add(bccLabel, BorderLayout.WEST);
		bccPanel.add(bccField, BorderLayout.CENTER);
		messagePanel.add(messageLabel, BorderLayout.NORTH);
		messagePanel.add(messageText, BorderLayout.CENTER);
		
		Panel fieldPanel = new Panel(new GridLayout(0, 1));
		fieldPanel.add(serverPanel);
		fieldPanel.add(fromPanel);
		fieldPanel.add(toPanel);
		fieldPanel.add(subjectPanel);
		fieldPanel.add(ccPanel);
		fieldPanel.add(bccPanel);
		
		/*
		 * Create a panel for the buttons and add listeners to the buttons.
		 */
		Panel buttonPanel = new Panel(new GridLayout(1, 0));
		btSend.addActionListener(new SendListener());
		btClear.addActionListener(new ClearListener());
		btQuit.addActionListener(new QuitListener());
		buttonPanel.add(btSend);
		buttonPanel.add(btClear);
		buttonPanel.add(btQuit);

		/* Add, pack, and show. */
		add(fieldPanel, BorderLayout.NORTH);
		add(messagePanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		pack();
		show();
	}

	static public void main(String argv[]) {
		new JavaMailApp();
	}

	/* Handler for the Send-button. */
	class SendListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.out.println("Sending mail");

			/* Check that we have the local mailserver */
			/*
			 * if ((serverField.getText()).equals("")) {
			 * System.out.println("Need name of local mailserver!"); return; }
			 */

			/* Check that we have the sender and recipient. */
			if ((fromField.getText()).equals("")) {
				System.out.println("Need sender!");
				return;
			}
			if ((toField.getText()).equals("")) {
				System.out.println("Need recipient!");
				return;
			}

			/*
			 * Check that the message is valid, i.e., sender and recipient
			 * addresses look ok.
			 */
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			Session session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			try {
				/* Create the message */

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress(fromField.getText()
						.toString().trim()));
				message.setRecipients(
						Message.RecipientType.TO,
						InternetAddress.parse(toField.getText().toString()
								.trim()));
				message.setRecipients(
						Message.RecipientType.CC,
						InternetAddress.parse(ccField.getText().toString()
								.trim()));
				message.setRecipients(
						Message.RecipientType.BCC,
						InternetAddress.parse(bccField.getText().toString()
								.trim()));
				message.setSubject(subjectField.getText().toString().trim());
				message.setText(messageText.getText().toString().trim());

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

			System.out.println("Mail sent succesfully!");
		}
	}

	/* Clear the fields on the GUI. */
	class ClearListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("Clearing fields");
			fromField.setText("");
			toField.setText("");
			subjectField.setText("");
			messageText.setText("");
		}
	}

	

	/* Quit. */
	class QuitListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
}

