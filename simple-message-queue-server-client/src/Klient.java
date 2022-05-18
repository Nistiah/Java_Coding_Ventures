import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Klient {
    private Socket socket;
    private BufferedReader input;
    private DataOutputStream out;
    private DataInputStream in;
    private int messagesExist = 0;
    private boolean threadStop = false;

    public Klient(String address, int port)
    {
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");
            input = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException e) {
            System.out.println("Nieznany host! Program kończy działanie");
            return;
        }
        catch(ConnectException e){
            System.out.println("Błąd w połączeniu z serwerem! Program kończy działanie");
            return;
        }
        catch(IOException i)
        {
            System.out.println(i);
            return;
        }
        System.out.println("Napisz \"stop\" aby zakończyć");
        String line = "";
        Scanner scanner = new Scanner(System.in);
        int temphour, tempmin;
        while (!line.equals("Stop")) {
            try {
                System.out.println("Podaj treść notyfikacji:");
                line = input.readLine();
                if(line.equals("stop")){
                    out.writeUTF(line);
                    threadStop=true;
                    break;
                }
                System.out.println("Podaj czas godzine i minute notyfikacji:");
                while (true) {
                    temphour = scanner.nextInt();
                    tempmin = scanner.nextInt();
                    scanner.nextLine();
                    if (temphour < 0 || temphour > 24 || tempmin < 0 || tempmin > 60) {
                        System.out.println("Podano niepoprawny czas, sprobuj ponownie");
                    }else break;
                }
                out.writeInt(temphour);
                out.writeInt(tempmin);

                out.writeUTF(line);
                if(line.equals("stop")){
                    break;
                }
                if (messagesExist == 0) {
                    messagesExist = -1;
                    Thread thread = new Thread(new Receiver());
                    thread.start();
                }
            }
            catch(InputMismatchException e)
            {
                System.out.println("Niepoprawny format danych, sprobuj ponownie!");
            }
            catch (IOException e){
                System.out.println("Błąd w połączeniu z serwerem! Program kończy działanie");
                break;
            }
        }
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        Klient client = new Klient("127.0.0.1", 6666);
    }
    public class Receiver implements Runnable{
        @Override
        public void run() {
            try {
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            while(true){
                if(threadStop){
                    return;
                }
                try {
                    System.out.println("Otrzymano notyfikacje!\n"+in.readUTF());
                } catch (IOException e) {
                    System.out.println("Utracono połączenie z serwerem! Program kończy działanie");
                    return;
                }
            }
        }
    }
}