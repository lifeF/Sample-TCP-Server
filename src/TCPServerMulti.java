/*
Kalana Dhananjaya Rathnayake
----------------------------------
Department of computer Engineering
Faculty of Engineerning
University of peradeniya
E14287
---------------------------------
Date : 2018 : 06 : 06
 */

import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServerMulti extends Thread  {

    private Socket connectionSocket;
    private  String requestMessageLine;
    private  String File_path;

    TCPServerMulti(Socket connectionSocket){
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {

            System.out.println("client connection through"+ this.connectionSocket);
            try {
                //buffer reader for input stream read
                BufferedReader inputStream = new BufferedReader (new InputStreamReader (this.connectionSocket.getInputStream ( )));
                //out put stream for sent response
                DataOutputStream Out_Put_Stream =  new DataOutputStream (this.connectionSocket.getOutputStream ( ));

                requestMessageLine = inputStream.readLine ( );

                StringTokenizer Get_parameters =  new StringTokenizer (requestMessageLine);

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
                    Out_Put_Stream.close ();
                    inputStream.close ();

                    //connectionSocket.close();
                //
                } else System.out.println("Bad Request Message");
            }
            catch (IOException ex){ //exception handle
                System.out.println("Server exception: " + ex.getMessage());
                ex.printStackTrace();
            }
    }

    public static void main(String argv[]) throws Exception {
        ServerSocket listenSocket = new ServerSocket(14287);

       while(!Thread.interrupted ()){

           Socket sock = listenSocket.accept();
           TCPServerMulti server=new TCPServerMulti (sock);
           server.start ();
       }



    }


}





