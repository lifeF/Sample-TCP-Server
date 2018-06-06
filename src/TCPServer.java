
import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer  {

    public static void main(String argv[]) throws Exception {

        String requestMessageLine;
        String File_path;

        ServerSocket listenSocket = new ServerSocket(14287);

        while (true) {

            Socket connectionSocket = listenSocket.accept();
            System.out.println("client connection through"+ connectionSocket);
            BufferedReader inFromClient =
                    new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream Out_Put_Stream =
                    new DataOutputStream(connectionSocket.getOutputStream());

            requestMessageLine = inFromClient.readLine();

            StringTokenizer Get_parameters =
                    new StringTokenizer(requestMessageLine);

            if (Get_parameters.nextToken().equals("GET")) {

                File_path = Get_parameters.nextToken();

                if (File_path.startsWith("/") == true)
                    File_path = File_path.substring(1);

                File file;
                FileInputStream inFile;

                //handle file not found exception
                try{
                     file= new File(File_path);
                     inFile= new FileInputStream(File_path);
                }
                catch(Exception e) {
                    File_path="Error.html";
                    file = new File("Error.html");
                    inFile= new FileInputStream(File_path);
                    System.out.println("File Not Found display Error.HTML");
                }

                int numOfBytes = (int) file.length();

                byte[] fileInBytes = new byte[numOfBytes];
                inFile.read(fileInBytes);

                Out_Put_Stream.writeBytes("HTTP/1.0 200 Document Follows\r\n");

                if (File_path.endsWith(".jpg"))
                    Out_Put_Stream.writeBytes("Content-Type: image/jpeg\r\n");

                Out_Put_Stream.writeBytes("Content-Length: " + numOfBytes + "\r\n");
                Out_Put_Stream.writeBytes("\r\n");

                Out_Put_Stream.write(fileInBytes, 0, numOfBytes);

                //connectionSocket.close();
            } else System.out.println("Bad Request Message");

        }
    }


}





