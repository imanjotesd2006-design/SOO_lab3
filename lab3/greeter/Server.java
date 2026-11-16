package greeter;
import java.net.*;  // bevat DatagramSocket, DatagramPacket en SocketException
import java.io.*; // bevat IOException, deze fout kan optreden bij versturen en ontvangen

public class Server {
	 public static void main(String[] args) {
	      DatagramSocket socket=null; // declareer de socket = stopcontact, we zetten hem eerst op null en buiten try zodat hij nog bruikbaar is bij finally om af te ronden
	      int serverPort=1234; // poort ligt vast, hier wordt geluisterd door de server
	      int bufferSize=100; // maximale grootte van een binnenkomend pakket
	      
	      try {
	    	  socket=new DatagramSocket(serverPort); // maakt socket en verbindt hem met poort 1234, dit is nu het adres van de server, als er als een server is op de poort SocketException
	    	  while(true) { // oneindige lus
	    		  byte[] buffer=new byte[bufferSize]; // we maken een lege byte "buffer" aan om de data in op te vangen
	    		  DatagramPacket request=new DatagramPacket(buffer,buffer.length); // leeg pakket = ontvangstbak, kent buffer en de lengte van de buffer
	    		  socket.receive(request); // programma blokkeert hier tot er een pakket binnenkomt, daarna: gegevens, ip afzender, poort afzender
	    		  String rMess="Hello, "+ (new String(request.getData())); // maakt string, zet ook lege bytes om!
	    		  DatagramPacket reply =new DatagramPacket(rMess.getBytes(),rMess.length(),request.getAddress(),request.getPort()); //maakt antwoordpakket: terug omzetten naar bytes, lengte van het aantal te versturen bytes, ip adres van client van wie het bericht komt, poort van client
	    		  socket.send(reply); // verstuurt antwoord
	    	  }
	      } catch(SocketException e) { //probleem bij maken van de socket
	    	  System.err.println(e);
	      } catch(IOException e) { //probleem bij het versturen/ontvangen
	    	  System.err.println(e);
	      } finally { // dit wordt ALTIJD uitgevoerd (ook bij fout), sluit socket zodat poort terug vrij komt
	    	  if(socket!=null) socket.close();
	      }
	   }

}
