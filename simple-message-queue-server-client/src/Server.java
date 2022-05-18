import java.io.*;
import java.net.*;
import java.util.Vector;
import java.time.LocalDateTime;
import java.lang.Runnable;

public class Server implements Runnable
{
    private Socket socket;
    private ServerSocket server;
    private DataInputStream in;
    private Vector<Time> queueueue = new Vector<>();
    private int messagesExist = 0;
    private DataOutputStream out;
    private boolean threadStop = false;
    public Server(int port){
        try {
            server = new ServerSocket(port);
            System.out.println("Server started");
            System.out.println("Waiting for a new client ...");
            socket = server.accept();
            System.out.println("Client accepted");
        } catch (IOException e) {
            System.out.println("Błąd połączenia z klientem, program kończy działanie");
        }
    }

    @Override
    public void run() {
        try {
            in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Błąd w połączeniu z klientem! Program kończy działanie");
            return;
        }
        String line = "";
        int hour, mint;
        Thread thread = new Thread(new Sender());
            while (true) {
                if (line.equals("Stop")) {
                    break;
                }
                try {
                    hour = in.readInt();
                    mint = in.readInt();
                    line = in.readUTF();
                    addToQueue(hour, mint, line);
                    if (messagesExist == 0) {
                        messagesExist = -1;
                        thread.start();
                    }
                } catch (IOException e) {
                    System.out.println("Utracono połączenie z klientem");
                    threadStop=true;
                    break;
                }
            }
            System.out.println("Closing connection");
        try {
            socket.close();
            in.close();
        } catch (IOException e) {
//            return;
        }
    }
    public void addToQueue(int h, int m, String not){
        Time temp= new Time();
        temp.setTime(h,m);
        temp.setNotification(not);
        LocalDateTime now = LocalDateTime.now();

        if(h>now.getHour()||(h==now.getHour()&&m>=now.getMinute())) {
            for (int i = 0; i < queueueue.size(); i++) {
                if(queueueue.get(i).dzien>0){
                    queueueue.insertElementAt(temp, i);
                    return;
                }
                if (queueueue.get(i).getTime() > h * 3600 + m * 60) {
                    queueueue.insertElementAt(temp, i);
                    return;
                }
            }
        }else{
            temp.dzien=1;
        }
        queueueue.add(temp);
    }
    public int getClosestTime(){
        try {
            return queueueue.get(0).getTime();
        }catch(ArrayIndexOutOfBoundsException e){
            return -1;
        }
    }
    public void removeNot(){
        queueueue.removeElementAt(0);
    }

    public static void main(String[] args){
        int port=6666;
        new Thread(new Server(port)).start();
        new Thread(new Server(port+1)).start();

    }

    public class Sender implements Runnable {
        @Override
        public void run() {
            LocalDateTime now;
            String line = "";
            while (true) {
                if(threadStop){
                    return;
                }
                    try {
                        out = new DataOutputStream(socket.getOutputStream());
                    } catch (IOException e) {
                        System.out.println("Niepowodzenie przy pobieraniu danych przez socket");
                    }
                try {
                    now = LocalDateTime.now();
                    line = queueueue.get(0).getNotification();
                    if (now.getHour() * 3600 + now.getMinute() * 60 == getClosestTime()) {

                        try {
                            out.writeUTF(line);
                        } catch (IOException e) {
                            System.out.println("Niepowodzenie w przesłaniu wiadomosci: " + line);
                        }
                        removeNot();
                    }
                } catch (ArrayIndexOutOfBoundsException e) { ///if theres nothing on the vector, wait 45s
                    try {
                        Thread.sleep(60 * 45);
                    } catch (InterruptedException ex) {
                        //nothing to do really here, internal error only harming code optimalisation
                    }
                }
            }

        }
    }

}