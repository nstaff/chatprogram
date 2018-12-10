import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.*;
import java.io.*;
import java.lang.Thread;
import java.lang.Runnable;

public class clientConnection {

    GUI_Construction GUI;
    String serverAddress = "";
    Socket serverSocket;
    BufferedReader receivedText;
    volatile Boolean stayConnected = true;

    public static void main(String args[]) throws IOException
    {

        new clientConnection();

    }

    clientConnection(){

        GUI = new GUI_Construction();
        GUI.m11.addActionListener(this::m11Pressed);

    }

    public void m11Pressed(ActionEvent m11pres){

        serverAddress = JOptionPane.showInputDialog("Enter IP Address of desired server\n" + "That is operating on the port 4500:");
        try{
            serverSocket = new Socket(serverAddress, 4500);
        }catch(UnknownHostException e){
            throw new RuntimeException(e);
        }catch(IOException d){
            throw new RuntimeException(d);
        }
        GUI.messageHistory.setText(GUI.messageHistory.getText() + "\nConnected to the Server!.");
        GUI.m12.setEnabled(true);
        GUI.send.setEnabled(true);
        GUI.clear.setEnabled(true);
        GUI.m11.setEnabled(false);
        createClient();

    }

    public void createClient(){

        stayConnected=true;
        new sendingText();
        new checkingInput();

    }

class checkingInput implements Runnable{

        private String message;

        checkingInput(){
            Thread t = new Thread(this);
            t.start();
        }

        public void run(){
            try {
                receivedText = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                while(stayConnected==true && (this.message = receivedText.readLine()) != null ){

                    GUI.messageHistory.setText(GUI.messageHistory.getText() + "\n" + this.message);
                }
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }

    class sendingText implements Runnable{
        private String message;
        private DataOutputStream sentText = null;

        sendingText(){

            Thread d = new Thread(this);
            d.start();

        }

        public void run(){
            try {
                sentText = new DataOutputStream(serverSocket.getOutputStream());
                GUI.send.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        message = GUI.messageBox.getText() + "\n";
                        try {
                            sentText.writeBytes(message);
                        }catch(IOException d){
                            throw new RuntimeException(d);
                        }
                        GUI.messageBox.setText("");
                    }
                });

                GUI.m12.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent d) {
                        stayConnected = false;
                        GUI.m12.setEnabled(false);
                        GUI.send.setEnabled(false);
                        GUI.clear.setEnabled(false);
                        GUI.m11.setEnabled(true);
                        try {
                            sentText.writeBytes("cmd999disconnectclient_tocleanupthelist0fconn3cteDu2rs\n");
                            serverSocket.close();
                        }catch(IOException f){
                            throw new RuntimeException(f);
                        }
                    }
                });

            }catch(IOException e){
                throw new RuntimeException(e);
            }

        }

    }

}
