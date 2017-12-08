package com.Raspi;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client1 {

    private static Socket socket;
    public String ipAddress="localhost";
    public  int portAddress=25000;
    public Client1(String ip,int port){
        ipAddress=ip;
        portAddress=port;

    }
    public  void findTemp(){
        while (true) {
            try {
                String host = ipAddress;
                int port = portAddress;
                InetAddress address = InetAddress.getByName(host);
                socket = new Socket(address, port);
                //System.out.println("You're now connected to the Server"); /*this should only print once */
                //Send the message to the server
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                String number;
                number = "hello";
                String sendMessage = number + "\n";
                bw.write(sendMessage);
                bw.flush();
                System.out.println("Message sent to the server : " + sendMessage);

                //Get the return message from the server
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String message = br.readLine();
                System.out.println("Message received from the server : " + message);
                Thread.sleep(1000);
            } catch (IOException exception) {
                System.out.println("d");  //System.out.println("Server is still offline");/*This should only print once*/
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}