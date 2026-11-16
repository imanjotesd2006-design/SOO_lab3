package greeter;
import java.net.*; //zelfde packages als bij server.java
import java.io.*; // nodig om de toetsenbordinvoer te lezen

public class Client {
	public static void main(String[] args) {
		InetAddress host = null; // ip van de server: "identificeer de bestemming"
		int serverPort = 1234; // poort waarop de server luistert
		DatagramSocket socket = null; // socket client, null en buiten try zoals bij server
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
				System.in)); // maakt lezer voor het toetsenbord: bytes worden ingelezen, vervolgens omgevormd naar tekens en tot slot wordt de hele regel gelezen door readLine()
		String name = ""; //voor naam, leeg zodat lusvoorwaarde iets kan controleren
		try {
			host = InetAddress.getLocalHost(); // zoekt adres van eigen computer op
			socket = new DatagramSocket(1300); // client maakt socket vast op poort 1300 -> maar 1 client tegelijk kan draaien
			do { // minstens 1 keer uitgevoerd daarna controle voorwaarde
				// read in a name
				System.out.println("Enter a name : ");
				consoleInput = new BufferedReader(new InputStreamReader(
						System.in));
				name = consoleInput.readLine(); //hier lezen we dus wat we typten

				if (!(name.equals("stop"))) { // er wordt iets verstuurd als je iets anders dan stop typt
					// send the name
					byte[] messageBytes = name.getBytes(); //terug omzetten naar bytes
					DatagramPacket request = new DatagramPacket(messageBytes,
							messageBytes.length, host, serverPort); // maakt pakket om te verzenden: boodschap, lengte, bestemming
					socket.send(request); // pakket versturen

					// receive reply
					byte[] buffer = new byte[50]; //maximale grootte pakket + buffer om pakket op te vangen
					DatagramPacket reply = new DatagramPacket(buffer, // ontvangstbak
							buffer.length);
					socket.receive(reply); // wacht op antwoord van de server

					// print the greeting
					System.out.println("Reply from server = "
							+ (new String(reply.getData()))); //bytes omzetten naar tekst en printen
				}
			} while (!(name.equals("stop")));
		} catch (UnknownHostException e) { // adres niet gevonden
			System.err.println(e);
		} catch (SocketException e) { // socket kon niet gemaakt worden
			System.err.println(e);
		} catch (IOException e) { // fouten bij versturen, ontvangen of lezen van toetsenbord
			System.err.println(e);
		} finally { // aflsuiter, maakt poort opnieuw vrij
			if (socket != null)
				socket.close();
		}
	}
}

