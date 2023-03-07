import java.io.IOException;

public class TestMailServer {
    public static void main(String[] args) {


        try {
            SMTPConnection test = new SMTPConnection();
            String localhost = "HELO example.com";
            test.sendCommand(localhost, 250);
            test.sendCommand("quit", 221);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
