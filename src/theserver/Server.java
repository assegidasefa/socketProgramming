package theserver;

import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

class serverChatForm extends JFrame implements ActionListener {

    static ServerSocket server;
    static Socket sok;
    JPanel panel;
    JTextField messageFrom;
    JTextArea ChatArea;
    JButton button;
    DataInputStream dis;
    DataOutputStream dos;

    public serverChatForm() {
        super("Server");
        try {
            
            panel = new JPanel();
            messageFrom = new JTextField();
            ChatArea = new JTextArea();
            button = new JButton("Send");
            setSize(500, 500);
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            panel.setLayout(null);
            add(panel);
            ChatArea.setBounds(20, 20, 450, 360);
            panel.add(ChatArea);
            messageFrom.setBounds(20, 400, 340, 30);
            panel.add(messageFrom);
            button.setBounds(375, 400, 95, 30);
            panel.add(button);
            
            button.addActionListener(this);
            server = new ServerSocket(2000, 1, InetAddress.getLocalHost());
            ChatArea.setText("Waiting for Client");
            sok = server.accept();
            ChatArea.setText(ChatArea.getText() + '\n' + "Client Found");
            while (true) {
                try {
                    DataInputStream dis = new DataInputStream(sok.getInputStream());
                    String string = dis.readUTF();
                    ChatArea.setText(ChatArea.getText() + '\n' + "Client: " + string);
                } catch (IOException e1) {
                    ChatArea.setText(ChatArea.getText() + '\n' + " Message sending fail:Network Error");
                    try {
                        Thread.sleep(2000);
                        
                    } catch (InterruptedException e) {

                        System.out.println(e);
                    }
                }
            }
        } catch (IOException er) {
            System.out.println(er);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == button) {
            ChatArea.setText(ChatArea.getText() + '\n' + "ME :" + messageFrom.getText());
            try {
                DataOutputStream dos = new DataOutputStream(sok.getOutputStream());
                dos.writeUTF(messageFrom.getText());
            } catch (IOException ew) {
                try {
                    Thread.sleep(2000);
                    
                } catch (InterruptedException et) {
                    System.out.println(et);
                }
            }
            messageFrom.setText("");
        }
    }

    public static void main(String[] args)  {
        
        serverChatForm t = new serverChatForm();
        
    }
}
