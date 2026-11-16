package localhost;

import java.net.InetAddress;    //java.net is de package waar de netwerkklassen worden bewaard
import java.net.UnknownHostException;  //dit is om errors te kunnen ontcijferen voor onvertaalbaar ip-adres

public class LocalHostIP {
    public static void main(String[] args) {
        try { // we moeten mogelijke fout van getLocalhost kunnen afhandelen -> try + catch
            InetAddress local = InetAddress.getLocalHost(); // geeft InetAddress-object, opzoeken met statische methode
            System.out.println("Hostnaam: " + local.getHostName());
            System.out.println("IP: " + local.getHostAddress());
            InetAddress loopback = InetAddress.getLoopbackAddress(); // geeft loopback-adres, kan geen fout geven
            System.out.println("Loopback: " + loopback.getHostAddress());
        } catch (UnknownHostException e) { //afhandelen van de mogelijke opgetreden error
            e.printStackTrace(); // door printStackTrace() kunnen we zien waar het fout ging
        }
    }
}
