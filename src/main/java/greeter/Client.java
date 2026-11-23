package greeter;
import java.net.*;
import java.io.*;

public class Client {
	public static void main(String[] args) throws IOException {
		InetAddress host = null;
		int serverPort = 1024;
		//DatagramSocket socket = null;
		Socket socket = null;
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(
				System.in));
		String name = "";
		try {
			host = InetAddress.getLocalHost();
			//socket = new DatagramSocket(1300);
			socket = new Socket(host, serverPort);
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();
			BufferedReader inputt = new BufferedReader(new InputStreamReader(input));
			PrintWriter outputt = new PrintWriter(output, true);
			do {
				// read in a name
				System.out.println("Enter a name : ");
				consoleInput = new BufferedReader(new InputStreamReader(
						System.in));

				name = consoleInput.readLine();

				if (!(name.equals("stop"))) {
					// send the name
					//byte[] messageBytes = name.getBytes();
					outputt.println(name);
					//output.flush();		// Dit om te vermijden dat er niks wordt gestuurd omdat de buffer 'te leeg' zou zijn



					// receive reply
					//byte[] buffer = new byte[50];
					String received = inputt.readLine();

					// print the greeting
					System.out.println("Reply from server = "
							+ received);
				}
			} while (!(name.equals("stop")));
		} catch (UnknownHostException e) {
			System.err.println(e);
		} catch (SocketException e) {
			System.err.println(e);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}

