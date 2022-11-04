package com.example.ses.socket;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SesClient {

    public static void main(String[] args) throws Exception{
        System.out.println("aaa");
        Socket socket = new Socket("localhost", 1994);
        System.out.println("bbb");
        //write to socket using ObjectOutputStream
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        System.out.println("Sending request to Socket Server");

        oos.writeObject("login");

        while (true){
            //read the server response message
            String message = (String) ois.readObject();
            System.out.println("Message: " + message);
            if ("OFF".equals(message)) {
                break;
            }
        }
        //close resources
        ois.close();
        oos.close();
    }
}
