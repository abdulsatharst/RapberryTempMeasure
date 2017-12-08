package com.Raspi;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class RaspiClient {

    private JButton valueButton;
    private JPanel panelMain;
    private JButton IPAddressButton;
    private JTextField ip;
    private JButton PORTButton;
    private JTextField port;
    private JButton getTemperatureButton;
    private JButton graphButton;
    private JTextField rateTextBox;
    private JButton rateButton;
    private JTextField valueTextField;
    private JLabel noOfValueLabel;
    private JLabel rateLabel;
    private JTextField ip2;
    private JButton button1;
    private JTextField iTextField;
    private JTextField ip4;
    private JButton submitIPButton1;
    private JButton submitIPButton2;
    private JRadioButton enableServer2RadioButton;
    private JRadioButton enableServer3RadioButton;
    private JRadioButton radioButton1;
    private JTextField ip5;
    private JButton submitIPButton3;
    private JRadioButton enableServer5RadioButton;
    private JTextField portTextField2;
    private JTextField portTextField3;
    private JTextField portTextField4;
    private JTextField portTextField5;
    private JButton submitPORTButton;
    private JButton submitPORTButton1;
    private JButton submitPORTButton2;
    private JButton submitPORTButton3;
    private JRadioButton enableServer4RadioButton;
    private JButton submitButton2;
    private JTextField ip3;
    public String ipAddress = "192.168.1.2";
    public String ipAddress2 = "192.168.1.2";
    public String ipAddress3 = "192.168.1.2";
    public String ipAddress4 = "192.168.1.2";
    public String ipAddress5 = "192.168.1.2";

    public int portAddress = 25000;
    public int portAddress2 = 25010;
    public int portAddress3 = 25020;
    public int portAddress4 = 25030;
    public int portAddress5 = 25040;

    public static Socket socket;
    List<List<Double>> temperature = new ArrayList<>(10);
    List<Double> tempListFill = new ArrayList<>();
    int samplingRate = 1;
    int noOfValues = 20;
    private boolean serverFlag[] = new boolean[5];
    boolean clickFlag1 = false;
    boolean clickFlag2 = false;
    boolean clickFlag3 = false;
    boolean clickFlag4 = false;
    boolean clickFlag5 = false;


    public RaspiClient() {
        graphButton.setEnabled(false);
        rateLabel.setText("Sampling Rate: 1");
        noOfValueLabel.setText("No of Values given: 20");
        rateTextBox.setText("in milli sec");
        temperature.add(tempListFill);
        temperature.add(tempListFill);
        temperature.add(tempListFill);
        temperature.add(tempListFill);
        temperature.add(tempListFill);
        for (int i = 0; i < 5; i++) {
            serverFlag[i] = false;
        }


//        button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
        IPAddressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipAddress = ip.getText();
                JOptionPane.showMessageDialog(null, "ip address set");

            }
        });
        PORTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    portAddress = Integer.parseInt(port.getText());
                    JOptionPane.showMessageDialog(null, "port address set");

                } catch (Exception e1) {

                }
            }
        });
        getTemperatureButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int i;
                Thread server1 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        returnTemp(0, ipAddress, portAddress);

                        System.out.println("Thread 1 complete");
                    }
                });

                Thread server2 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        returnTemp(10, ipAddress2, portAddress2);

                        System.out.println("Thread 2 complete");


                    }
                });
                Thread server3 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        returnTemp(20, ipAddress3, portAddress3);

                        System.out.println("Thread 3 complete");


                    }
                });
                Thread server4 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        returnTemp(30, ipAddress4, portAddress4);

                        System.out.println("Thread 4 complete");


                    }
                });
                Thread server5 = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        returnTemp(40, ipAddress5, portAddress5);

                        System.out.println("Thread 5 complete");


                    }
                });
                server1.start();
                server2.start();
                server3.start();
                server4.start();
                server5.start();

//                while (i<noOfValues) {
//                    try {
//                        String host = ipAddress;
//                        int port = portAddress;
//                        InetAddress address = InetAddress.getByName(host);
//                        socket = new Socket(address, port);
//                        //System.out.println("You're now connected to the Server"); /*this should only print once */
//                        //Send the message to the server
//                        OutputStream os = socket.getOutputStream();
//                        OutputStreamWriter osw = new OutputStreamWriter(os);
//                        BufferedWriter bw = new BufferedWriter(osw);
//
//                        String number;
//                        number = "hello";
//                        String sendMessage = number + "\n";
//                        bw.write(sendMessage);
//                        bw.flush();
//                        System.out.println("Message sent to the server : " + sendMessage);
//
//                        //Get the return message from the server
//                        InputStream is = socket.getInputStream();
//                        InputStreamReader isr = new InputStreamReader(is);
//                        BufferedReader br = new BufferedReader(isr);
//                        String message = br.readLine();
//                        System.out.println("Message received from the server : " + message);
//
//                        Thread.sleep(samplingRate);
//                      //  JOptionPane.showMessageDialog(null,message);
//                        temperature.add( Double.parseDouble(message));
//                        i++;
//
//                    } catch (IOException exception) {
//                        System.out.println("d");  //System.out.println("Server is still offline");/*This should only print once*/
//                    } catch (InterruptedException e1) {
//                        e1.printStackTrace();
//                    }
//                }
//                returnTemp();
                if (temperature.size() > 0) {
                    graphButton.setEnabled(true);
                }

            }
        });


        graphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GraphPanel mainPanel = new GraphPanel(temperature, serverFlag);
                mainPanel.setPreferredSize(new Dimension(800, 600));
                JFrame frame = new JFrame("DrawGraph");
                frame.setLayout(new BorderLayout());

                frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                frame.add(new JScrollPane(mainPanel));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);


            }
        });
        rateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                samplingRate = Integer.parseInt(rateTextBox.getText());
                rateLabel.setText("Sampling Rate: " + samplingRate);
            }
        });
        valueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noOfValues = Integer.parseInt(valueTextField.getText());
                noOfValueLabel.setText("No of Values given: " + noOfValues);

            }
        });

        radioButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickFlag1 == false) {
                    serverFlag[0] = true;
                    clickFlag1 = true;
                } else if (clickFlag1 == true) {
                    serverFlag[0] = false;
                    clickFlag1 = false;
                }

            }
        });
        enableServer2RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickFlag2 == false) {
                    serverFlag[1] = true;
                    clickFlag2 = true;
                } else if (clickFlag2 == true) {
                    serverFlag[1] = false;
                    clickFlag2 = false;
                }


            }
        });
        enableServer3RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickFlag3 == false) {
                    serverFlag[2] = true;
                    clickFlag3 = true;
                } else if (clickFlag3 == true) {
                    serverFlag[2] = false;
                    clickFlag3 = false;
                }
            }
        });
        enableServer4RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickFlag4 == false) {
                    serverFlag[3] = true;
                    clickFlag4 = true;
                } else if (clickFlag4 == true) {
                    serverFlag[3] = false;
                    clickFlag4 = false;
                }
            }
        });
        enableServer5RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (clickFlag5 == false) {
                    serverFlag[4] = true;
                    clickFlag5 = true;
                } else if (clickFlag5 == true) {
                    serverFlag[4] = false;
                    clickFlag5 = false;
                }
            }
        });
        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipAddress2 = ip2.getText();
            }
        });
        submitIPButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipAddress3 = ip3.getText();
            }
        });
        submitIPButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipAddress4 = ip4.getText();
            }
        });
        submitIPButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ipAddress5 = ip5.getText();
            }
        });
        submitPORTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portAddress2 = Integer.parseInt(portTextField2.getText());
            }
        });
        submitPORTButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portAddress3 = Integer.parseInt(portTextField3.getText());

            }
        });
        submitPORTButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portAddress4 = Integer.parseInt(portTextField4.getText());

            }
        });
        submitPORTButton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portAddress5 = Integer.parseInt(portTextField5.getText());

            }
        });
        radioButton1.addFocusListener(new FocusAdapter() {
        });
    }

    public synchronized void returnTemp(int p, String ipAddress, int portAddress) {
        int i = 0;
        List<Double> tempList = new ArrayList(20);
//        temperature.add(tempList);
//        temperature.add(tempList);
//        temperature.add(tempList);
//        temperature.add(tempList);
//        temperature.add(tempList);

        while (i < noOfValues) {
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

                //Get the return message from the server
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String message = br.readLine();
                // System.out.println("Message received from the server : " +portAddress+" "+ message);

                Thread.sleep(samplingRate);
                //  JOptionPane.showMessageDialog(null,message);
                tempList.add(Double.parseDouble(message));
                i++;

            } catch (IOException exception) {
                System.out.println("d");  //System.out.println("Server is still offline");/*This should only print once*/
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        try {
            temperature.remove(p / 10);
            temperature.add(p / 10, tempList);
            i = 0;
            String tempString = "from server " + p + "\n";


            // JOptionPane.showMessageDialog(null, tempString);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Some error occured " + p);

        }


    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("RaspiClienti");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new RaspiClient().panelMain);
        frame.pack();
        frame.setVisible(true);
    }


}
