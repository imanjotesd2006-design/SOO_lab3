package parallelgreeter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class Server {
    public static void main(String[] args) { // startpunt: "portier"
        int serverPort = 1024; // poort waarop de server luistert
        ServerSocket listen = null; // buiten try + null zodat finally ermee kan werken
        try { // code die een IOException kan gooien
            listen = new ServerSocket(serverPort); // open de serversocket op poort 1024
            System.out.println("Server luistert op poort " + serverPort); // toon dat de server klaar is
            while (true) { // blijf altijd nieuwe clients aanvaarden
                Socket client = listen.accept(); // wacht (blokkeert) tot een client verbindt
                new Thread(() -> handleClient(client)).start(); // nieuwe draad die deze client afhandelt; main gaat meteen terug naar accept()
            } // einde while: komt hier terug voor de volgende client
        } catch (SocketException e) { // probleem bij het maken van de socket (bv. poort bezet)
            System.err.println(e);
        } catch (IOException e) { // ander probleem bij versturen/ontvangen
            System.err.println(e);
        } finally { // wordt ALTIJD uitgevoerd, ook bij een fout
            try { // close() kan zelf ook een IOException gooien
                if (listen != null) listen.close(); // sluit de serversocket zodat de poort terug vrij komt
            } catch (IOException e) { // fout bij het sluiten
                System.err.println(e);
            }
        }
    }

    private static void handleClient(Socket client) { // handelt één client af; loopt in een eigen draad
        String draad = Thread.currentThread().getName(); // naam van de huidige draad (om parallelisme te zien)
        System.out.println(draad + ": client verbonden op poort " + client.getPort()); // log welke draad welke client behandelt
        try { // code die een IOException kan gooien
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); // bytes -> tekst voor de input
            PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true); // tekst -> bytes voor de output (true = auto-flush)
            String name = in.readLine(); // lees de eerste naam (blokkeert enkel DEZE draad)
            while (name != null && !name.equals("stop")) { // ga door zolang de client niet weg is en geen "stop" stuurt
                out.println("Hello, " + name); // stuur de begroeting terug
                name = in.readLine(); // lees de volgende naam
            } // einde while: client is klaar
        } catch (IOException e) { // probleem bij lezen/schrijven met deze client
            System.err.println(draad + ": " + e);
        } finally { // wordt ALTIJD uitgevoerd, ook bij een fout
            try { // close() kan zelf ook een IOException gooien
                client.close(); // sluit de verbinding met deze client
                System.out.println(draad + ": client afgesloten"); // log dat deze client klaar is
            } catch (IOException e) { // fout bij het sluiten
                System.err.println(e);
            }
        }
    }
}
