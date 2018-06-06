
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.logging.Logger;

public class TCPServer extends Thread  {

    static final int PORT = 8888;
    Socket socket;

    public static void main(String[] args) throws IOException {
        while (!Thread.interrupted())
            try(ServerSocket ss = new ServerSocket(PORT);) {
                new TCPServer(ss.accept()).start();
            }
    }

    TCPServer(Socket s) {
        socket = s;
    }

    @Override
    public void run() {

        try( OutputStream out = socket.getOutputStream(); InputStream in = socket.getInputStream() ) {

            PrintWriter out1 = new PrintWriter(out,true);
                    // Send a welcome message to the client.
            out1.println("Hello, you are client #.");
            out1.println("Enter a line with only a period to quit\n"); // Get messages from the client, line by line; return them // capitalized
             System.out.println("try user login");
            //TODO
        } catch (IOException e) {
//            out.write("400 ERROR".getBytes());
            Logger.getGlobal().severe(e.getMessage());
        }
    }



}





