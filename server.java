package com.Raspi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends Thread {
    public int p;
    public Double inc;

    public  server(int p,Double inc){
        this.p=p;
        this.inc=inc;
    }
    private static Socket socket;

    public static void main(String[] args) {
//        Thread server1=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Temperature(0,0.0);
//            }
//        });
//
//        Thread server2=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Temperature(10,2.0);
//            }
//        });
//        Thread server3=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Temperature(20,4.0);
//            }
//        });
//        Thread server4=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Temperature(30,-2.0);
//            }
//        });
//        Thread server5=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Temperature(40,-4.0);
//            }
//        });
       server server1=new server(0,0.0);
        server server2=new server(10,1.0);
        server server3=new server(20,2.0);
        server server4=new server(30,3.0);
        server server5=new server(40,4.0);


        server1.start();

        server2.start();
        server3.start();
        server4.start();
        server5.start();
        try {
            server1.join();
            server2.join();
            server3.join();
            server4.join();
            server5.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public  synchronized void Temperature(){
        String fileName = "/sys/class/thermal/thermal_zone0/temp";
        String line = null;
        float tempC=0;
        try {

            int port = 25000+p;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started and listening to the port 25000");

            //Server is running always. This is done using this while(true) loop
            while (true) {
                try {
                    FileReader fileReader = new FileReader(fileName);

                    BufferedReader bufferedReader = new BufferedReader(fileReader);

                    while((line = bufferedReader.readLine()) != null) {
                        tempC = (Integer.parseInt(line) / 1000);
                        float tempF = ((tempC / 5) * 9) + 32;
                        System.out.println("Temp °C: " + tempC + " Temp °F: " + tempF);
                    }

                    bufferedReader.close();
                }
                catch(FileNotFoundException ex) {
                    System.out.println("Unable to open file '" + fileName + "'");
                }
                catch(IOException ex) {
                    System.out.println("Error reading file '" + fileName + "'");
                }
                //Reading the message from the client
                socket = serverSocket.accept();
//                InputStream is = socket.getInputStream();
//                InputStreamReader isr = new InputStreamReader(is);
//                BufferedReader br = new BufferedReader(isr);
//                String number = br.readLine();
//                System.out.println("Message received from client is " + number);

                //Multiplying the number by 2 and forming the return message
                String returnMessage=String.valueOf(tempC+inc)+"\n";

                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write(returnMessage);
                System.out.println("Message sent to the client is " + returnMessage);
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void run() {
        Temperature();

    }
}
