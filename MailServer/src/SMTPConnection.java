import java.net.*;
import java.io.*;
import java.util.*;

/**
 * Open an SMTP connection to a mailserver and send one mail.
 * 
 */
public class SMTPConnection {
	/* The socket to the server */
	private Socket connection;
	/* Streams for reading and writing the socket */
	private BufferedReader fromServer;
	private DataOutputStream toServer;
	private static final int SMTP_PORT = 25;
	private static final String CRLF = "\r\n";
	/* Are we connected? Used in close() to determine what to do. */
	private boolean isConnected = false;

	/*
	 * Create an SMTPConnection object. Create the socket and the associated
	 * streams. Initialize SMTP connection.
	 */
	public SMTPConnection(Envelope envelope) throws IOException {

		/* Creating connection object to mail server */

		connection = new Socket(envelope.DestAddr, SMTP_PORT);

		/* Streams for reading and writing the socket */

		fromServer = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		toServer = new DataOutputStream(connection.getOutputStream());

		/* Reading from server and exit the program if reply is not 220 */

		String reply = fromServer.readLine();

		if (parseReply(reply) != 220) {

			System.out.println(reply);
			return;
		}
		
		/* Reading localhost value to send to server */
		
		String localhost = (InetAddress.getLocalHost()).getHostName();
		
		
		//System.out.println("local host is " + localhost);
		
		/* Sending Helo command to server and exit the program if the server doesn't reply 250 */
		
		try {
			sendCommand("HELO " + localhost, 250);

			System.out.println("LOCALHOST: " + localhost);
		} catch (IOException e) {
			System.out.println("Error");
			return;
		}
		
		/*  Setting isConnected if the server replies  250 for HELO command */
		
		isConnected = true;

	}

	/*
	 * Send the message. Write the correct SMTP-commands in the correct order.
	 * No checking for errors, just throw them to the caller.
	 */
	public void send(Envelope envelope) throws IOException {
		
		/*  Setting isConnected if the server replies  250 for HELO command */
		
		if(isConnected){
			
			sendCommand("MAIL FROM:<" + envelope.Sender + ">" + CRLF, 250);
			sendCommand("RCPT TO:<" + envelope.Recipient + ">" + CRLF, 250);
			sendCommand("DATA", 354);
			sendCommand(envelope.Message.toString() + CRLF + ".", 250);
			
		}
		
	}

	/*
	 * Close the connection. First, terminate on SMTP level, then close the
	 * socket.
	 */
	public void close() {
		isConnected = false;
		try {
			sendCommand("QUIT", 221);
			connection.close();
		} catch (Exception e) {
			System.out.println("Unable to close connection: " + e);
			isConnected = true;
		}
	}

	/*
	 * Send an SMTP command to the server. Check that the reply code is what is
	 * is supposed to be according to RFC 821.
	 */
	private void sendCommand(String command, int rc) throws IOException {
		String reply = null;
		System.out.println("Command to server: " + command);
		toServer.writeBytes(command + CRLF);
		reply = fromServer.readLine();
		if (parseReply(reply) != rc) {
			System.out.println("Error" + command);
			throw new IOException();
		} else {
			System.out.println("Reply from server: " + reply);

		}
	}

	/* Parse the reply line from the server. Returns the reply code. */
	private int parseReply(String reply) {
		System.out.println("inside parse reply" + reply);
		StringTokenizer parser = new StringTokenizer(reply);
		String replycode = parser.nextToken();
		return (new Integer(replycode)).intValue();
	}

	/* Destructor. Closes the connection if something bad happens. */
	protected void finalize() throws Throwable {
		if (isConnected) {

			close();
		}
		super.finalize();
	}
}
