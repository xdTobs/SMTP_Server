import java.io.*;
import java.net.Socket;

public class SMTPConnection {
    private static final int SMTP_PORT = 5252;
    private static final String CRLF = "\r\n";
    /* The socket to the server */
    private Socket connection;
    /* Streams for reading and writing the socket */
    private BufferedReader fromServer;
    private DataOutputStream toServer;
    /* Are we connected? Used in close() to determine what to do. */
    private boolean isConnected = false;

    /* Create an SMTPConnection object. Create the socket and the
       associated streams. Initialize SMTP connection. */

    public SMTPConnection() throws IOException {
        connection = new Socket("130.225.170.65", 5252);
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toServer = new DataOutputStream(connection.getOutputStream());
        System.out.println(fromServer.readLine());
    }

    public SMTPConnection(Envelope envelope) throws IOException {
        connection = new Socket("130.225.170.65", Integer.parseInt(envelope.DestHost));
        fromServer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        toServer = new DataOutputStream(connection.getOutputStream());
        System.out.println(fromServer.readLine());
        String line = fromServer.readLine();
        if (!line.startsWith("220")) throw new IOException("first message must start with 220");


	/* SMTP handshake. We need the name of the local machine.
	   Send the appropriate SMTP handshake command. */

        String localhost = "HELO example.com";
        sendCommand(localhost, 250);

        isConnected = true;
    }


    /* Send the message. Write the correct SMTP-commands in the
       correct order. No checking for errors, just throw them to the
       caller. */
    public void send(Envelope envelope) throws IOException {

        sendCommand("mail from: " + envelope.Sender, 250);
        sendCommand("rcpt to: " + envelope.Recipient, 250);
        sendCommand("data", 354);
        sendCommand(envelope.Message.Body, 250);
        /* Fill in */
	/* Send all the necessary commands to send a message. Call
	   sendCommand() to do the dirty work. Do _not_ catch the
	   exception thrown from sendCommand(). */
        /* Fill in */
    }

    /* Close the connection. First, terminate on SMTP level, then
       close the socket. */
    public void close() {
        isConnected = false;
        try {
            sendCommand("quit", 221);
            connection.close();
        } catch (IOException e) {
            System.out.println("Unable to close connection: " + e);
            isConnected = true;
        }
    }

    /* Send an SMTP command to the server. Check that the reply code is
       what it is supposed to be according to RFC 821. */
    public void sendCommand(String command, int expectedRC) throws IOException {

        System.out.println("start Function");
        toServer.write(command.getBytes());
        toServer.flush();
        System.out.println("past flush");
        char[] buff = new char[1024];

        //TODO the read blocks execution
        fromServer.read(buff);
        System.out.println(new String(buff));

        //char[] rcBuff = new char[3];
        //fromServer.read(rcBuff,0,3);
        //int rc = Integer.parseInt(new String(rcBuff));

        int rc = Integer.parseInt(new String(buff).substring(0,3));
        System.out.println(rc + " " + expectedRC);
        if (rc != expectedRC) throw new IOException("excpected different RC");
    }

    /* Parse the reply line from the server. Returns the reply code. */
    private int parseReply(String reply) {
        /* Fill in */
        return 0;
    }

    /* Destructor. Closes the connection if something bad happens. */

}
