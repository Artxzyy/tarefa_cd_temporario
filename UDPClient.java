import java.net.*;
import java.io.*;

public class UDPClient{

    public static String multiplyString(String s, int m) {
        String res = s;
        for (int i = 0; i < m; i++) {
            res += res;
        }
        return res;
    }

    public static void main(String args[]){ 
		// args give message contents and destination hostname
		DatagramSocket aSocket = null;

        while (true) {
            try {

                aSocket = new DatagramSocket();    
                aSocket.setSoTimeout(1);

                for (int i = 0; i < 100; i++) {
                    byte[] m = multiplyString(args[0], i).getBytes();
                
                    InetAddress aHost = InetAddress.getByName(args[1]);
                    int serverPort = 6789;
        
                    DatagramPacket request = new DatagramPacket(m, multiplyString(args[0], i).length(), aHost, serverPort);
                    
                    aSocket.send(request);
                    
                    byte[] buffer = new byte[multiplyString(args[0], i).length()];
                    DatagramPacket reply = new DatagramPacket(buffer, buffer.length);	
                    
                    aSocket.receive(reply);
                    
                    System.out.println("Reply: " + new String(reply.getData()));
                }
            } catch (SocketTimeoutException e) {
                System.out.println("SocketTimeoutException was raised because SO_TIMEOUT expired.");
            } catch (SocketException e) {
                System.out.println("Socket: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO: " + e.getMessage());
            } finally {
                if(aSocket != null) {
                    aSocket.close();
                }
            }
        }
	}		      	
}