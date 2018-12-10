import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.lang.Runnable;
import java.lang.Thread;
import java.util.Scanner;

public class ServerSide {

    String message;
    volatile int i = 0;
    List<Socket> clientList = new ArrayList<>();
    volatile boolean running;
    ServerSocket listener;

    public static void main(String[] args) throws IOException {

        System.out.println("Server is running! Type 'close' to shut down the server.");
        new ServerSide();

    }

    ServerSide()
    {
        try{
            listener = new ServerSocket(4500);
            new serverCommands();
            while(running) {
                Socket socket = listener.accept();
                i++;
                clientList.add(socket);
                new clientConnectionCreated(socket);
            }
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    class clientConnectionCreated implements Runnable{
        private Socket socket;
        BufferedReader receivedText;
        private Boolean connected = true;
        private Socket sendingSocket;

        clientConnectionCreated(Socket socket){
            this.socket = socket;

            Thread t = new Thread(this);
            t.start();

        }

        public void run() {
            DataOutputStream relayedText;
            try {
                    receivedText = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        while(this.connected = true && (message = receivedText.readLine()) != null) {
                            if(!this.connected){
                                break;
                            }
                            if(message.equals("cmd999disconnectclient_tocleanupthelist0fconn3cteDu2rs")){
                                for(int x=0; x<i;x++){
                                    if(clientList.get(x)==this.socket){
                                        clientList.remove(x);
                                        i--;
                                        break;
                                    }
                                }
                                this.socket.close();
                                this.connected=false;
                            }else if (!message.equals("cmd999disconnectclient_tocleanupthelist0fconn3cteDu2rs")){
                                message = message + "\n";
                                System.out.println(message);
                                for (int x = 0; x < i; x++) {
                                    sendingSocket = clientList.get(x);
                                    relayedText = new DataOutputStream(sendingSocket.getOutputStream());
                                    relayedText.writeBytes(message);
                                }
                            }
                    }
                } catch (IOException e) {

                    throw new RuntimeException(e);

                }
        }
    }

    class serverCommands implements Runnable{
        Scanner scanner = new Scanner(System.in);
        String command;

        serverCommands(){
            Thread t = new Thread(this);
            t.start();

        }

        public void run(){
            while(running){
            command = scanner.nextLine();
            if(command == "exit"){
                try {
                    listener.close();
                }catch(IOException e){
                    throw new RuntimeException(e);
                }
                System.exit(0);
            }
            }

        }

    }

}




