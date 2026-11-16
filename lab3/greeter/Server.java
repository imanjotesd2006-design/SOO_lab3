package greeter;

import java.net.*;
import java.io.*; // bevat IOException

public class Server {
	public static void main(String[] args) {
		int serverPort = 1024;
		int bufferSize = 100; // maximale grootte van een binnenkomend pakket
		ServerSocket listen = null; //buiten try + null zodat finally ermee kan werken
		try {
			listen = new ServerSocket(serverPort);
			while (true) {
				Socket client = listen.accept(); // wacht tot client verbindt
				BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream())); //bytes -> tekst voor de input
				PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true); //tekst -> bytes voor de output
				String name = in.readLine();
				while (name != null && !name.equals("stop")) {
					out.println("Hello, " + name);
					name = in.readLine();
				}
				client.close();
			}
		} catch (SocketException e) { //probleem bij maken van de socket
			System.err.println(e);
		} catch (IOException e) { //probleem bij het versturen/ontvangen
			System.err.println(e);
		} finally { // dit wordt ALTIJD uitgevoerd (ook bij fout), sluit socket zodat poort terug vrij komt
			try {
				if (listen != null) listen.close();
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}
}