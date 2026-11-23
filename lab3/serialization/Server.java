package serialization;

import java.io.*;
import java.util.*;
import java.net.*;

public class Server {

    private static ArrayList<Person> db = new ArrayList<Person>();

    public static void main(String[] args) {
        fillInPhoneNumberDataBase(); // vul de "database" met personen en hun telefoonnummer
        int serverPort = 1025; // andere poort dan de greeter-servers (1024), zodat ze niet botsen
        ServerSocket listen = null; // buiten try + null zodat finally ermee kan werken
        try { // code die een IOException kan gooien
            listen = new ServerSocket(serverPort); // open de serversocket op poort 1025
            while (true) { // blijf clients aanvaarden, één na één (geen multithreading nodig)
                Socket client = listen.accept(); // wacht (blokkeert) tot een client verbindt
                ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream()); // 1. output-stroom voor objecten: object -> bytes
                out.flush(); // 2. stuur meteen de stream-header door, anders blijft de ObjectInputStream aan de andere kant wachten
                ObjectInputStream in = new ObjectInputStream(client.getInputStream()); // 3. input-stroom voor objecten: bytes -> object (wacht op de header van de client)
                Person p = (Person) in.readObject(); // lees het object van de client en cast het van Object naar Person
                Person antwoord = lookUpPhoneNumber(p); // zoek de persoon op in de database (vult het telefoonnummer aan)
                out.writeObject(antwoord); // stuur het aangevulde Person-object terug naar de client
                client.close(); // antwoord is verstuurd: verbinding mag meteen dicht
            } // einde while: terug naar accept() voor de volgende client
        } catch (IOException e) { // probleem met de socket of bij versturen/ontvangen (bv. poort bezet)
            System.err.println(e); // toon de fout
        } catch (ClassNotFoundException e) { // readObject ontving een object van een klasse die de server niet kent
            System.err.println(e); // toon de fout
        } finally { // wordt ALTIJD uitgevoerd, ook bij een fout
            try { // close() kan zelf ook een IOException gooien
                if (listen != null) listen.close(); // sluit de serversocket zodat de poort terug vrij komt
            } catch (IOException e) { // fout bij het sluiten
                System.err.println(e); // toon de fout
            } // einde try/catch rond close
        } // einde finally
    }

    private static void fillInPhoneNumberDataBase() {
        Person[] p = {new Person("Jan", "Janssens", new PhoneNumber("32", "9", "44 55 66")),
            new Person("Piet", "Pieters", new PhoneNumber("32", "50", "11 22 33")),
            new Person("Giovanni", "Totti", new PhoneNumber("49", "22", "00 99 88")),
            new Person("Jean", "Lefevre", new PhoneNumber("33", "4", "12 34 56"))};
        for (Person i : p) {
            db.add(i);
        }
    }

    private static Person lookUpPhoneNumber(Person p) {
        int index = db.indexOf(p);
        if (index >= 0) {
            return db.get(index);
        } else {
            p.setPhoneNumber(new PhoneNumber());
            return p;
        }
    }
}
