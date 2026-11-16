package Localhost;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHostIP {
    public static void main(String[] args) {
        try {
            InetAddress local = InetAddress.getLocalHost();
            System.out.println("Hostnaam: " + local.getHostName());
            System.out.println("IP: " + local.getHostAddress());
            InetAddress loopback = InetAddress.getLoopbackAddress();
            System.out.println("Loopback: " + loopback.getHostAddress());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
