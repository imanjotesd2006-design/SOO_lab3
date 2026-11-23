package greeter;
import java.net.*;
import java.io.*;

public class Server {
	 public static void main(String[] args) throws IOException {
	      //DatagramSocket socket=null;
		  ServerSocket listen = null;
	      int serverPort=1024;
	      int bufferSize=100;
	      
	      try {
	    	  //socket=new DatagramSocket(serverPort);
			  listen = new ServerSocket(serverPort);
			  Socket client = listen.accept();
			  InputStream input = client.getInputStream();
			  OutputStream output = client.getOutputStream();
			  BufferedReader inputt = new BufferedReader(new InputStreamReader(input));
			  PrintWriter outputt = new PrintWriter(output, true);
	    	  while(true) {
	    		  //byte[] buffer=new byte[bufferSize];
				  String received = inputt.readLine();

				  String rMess="Hello, "+ received;
				  outputt.println(rMess);

	    	  }
	      } catch(SocketException e) {
	    	  System.err.println(e);
	      } catch(IOException e) {
	    	  System.err.println(e);
	      } finally {
	    	  //if(socket!=null) socket.close();
			  if (listen!=null) {
				  listen.close();
			  }
	      }
	   }

}
