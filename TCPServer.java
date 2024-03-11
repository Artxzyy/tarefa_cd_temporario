import java.net.*;
import java.io.*;

public class TCPServer {
	public static void main (String args[]) {
		try{
			int serverPort = 7896; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			// listenSocket.setSoTimeout(1);
            while(true) {
				Socket clientSocket = listenSocket.accept();
                clientSocket.setSoTimeout(1);
                {
                    Connection c = new Connection(clientSocket);
                    c.run();
                }
			}
		} catch(IOException e) {
            System.out.println("Listen socket: " + e.getMessage());
        }
	}
}

class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	
    public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream(clientSocket.getInputStream());
			out = new DataOutputStream(clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {
            System.out.println("Connection: " + e.getMessage());
        }
	}
	
    public void run(){
		try {
			String data = in.readUTF();
			out.writeUTF(data);
		} catch (EOFException e) {
            System.out.println("EOF: " + e.getMessage());
		} catch(IOException e) {
            System.out.println("readline: " + e.getMessage());
		} finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Close error.");
            }
        }
	}
}