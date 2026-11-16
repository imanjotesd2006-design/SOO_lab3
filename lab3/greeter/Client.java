package greeter;

import java.net.*; //zelfde packages als bij server.java
import java.io.*; // nodig om de toetsenbordinvoer te lezen

public class Client {
    public static void main(String[] args) {
        InetAddress host = null; // ip van de server: "identificeer de bestemming"
        int serverPort = 1024; // poort waarop de server luistert
        Socket socket = null; // socket client, null en buiten try zoals bij server
        BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
                System.in)); // maakt lezer voor het toetsenbord: bytes worden ingelezen, vervolgens omgevormd naar tekens en tot slot wordt de hele regel gelezen door readLine()
        String name = ""; //voor naam, leeg zodat lusvoorwaarde iets kan controleren
        try {
            host = InetAddress.getLocalHost(); // zoekt adres van eigen computer op
            socket = new Socket(host, serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //bytes -> tekst voor de input
            PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true); //tekst -> bytes voor de output
            do { // minstens 1 keer uitgevoerd daarna controle voorwaarde
                // read in a name
                System.out.println("Enter a name : ");
                name = consoleInput.readLine(); //hier lezen we dus wat we typten

                if (!(name.equals("stop"))) { // er wordt iets verstuurd als je iets anders dan stop typt
                    out.println(name);
                    String reply = in.readLine();

                    // print the greeting
                    System.out.println("Reply from server = "
                            + reply);
                }
            } while (!(name.equals("stop")));
        } catch (UnknownHostException e) { // adres niet gevonden
            System.err.println(e);
        } catch (SocketException e) { // socket kon niet gemaakt worden
            System.err.println(e);
        } catch (IOException e) { // fouten bij versturen, ontvangen of lezen van toetsenbord
            System.err.println(e);
        } finally {
            try {
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}

