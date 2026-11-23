package serialization;

import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket(InetAddress.getLocalHost(),1025);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Person vraag = new Person("Jan", "Janssens");
            out.writeObject(vraag);
            Person antwoord = (Person) in.readObject();
            System.out.println("Antwoord van server: " + antwoord);

        } catch (IOException e) {
            System.err.println(e);
        } catch (ClassNotFoundException e) {
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
