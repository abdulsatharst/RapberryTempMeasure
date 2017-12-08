package com.Raspi;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    private static Socket socket;

    public static void main(String args[]) {

        Thread s = new Thread();
        click cl = new click();
      cl.start();


    }

    static class click extends Thread {
        public void run() {
            while (true) {
                try {
                    String host = "localhost";
                    int port = 25000;
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
                    Thread.sleep(5000);
                } catch (IOException exception) {
                    System.out.println("d");  //System.out.println("Server is still offline");/*This should only print once*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
