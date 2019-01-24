package lab7_Sockets;

import java.io.*;
import java.net.Socket;

/**
 * Этот класс устанавливает соединение с сервером и передает socket потоку на выполнение чтения данных с сервера,
 * и потоку отправки данных на сервер
 */
public class Client {

    private static Socket clientSocket;

    /**
     * Здесь запускаются потоки чтения и отправки данных на сервер.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            clientSocket = new Socket("127.0.0.1", 3232);
            ClientReaderThread read = new ClientReaderThread(clientSocket);
            ClientWriterThread write = new ClientWriterThread(clientSocket);
            try {
                read.join();
                write.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }












        /*
        try{
            clientSocket = new Socket("127.0.0.1",3232);

            reader = new BufferedReader(new InputStreamReader(System.in));

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String word;

            while (!(word = reader.readLine()).equals("exit")) {

                out.write(word + "\n");
                out.flush();

                System.out.println(in.readLine());
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                clientSocket.close();
                reader.close();
                System.out.println("Клиент был закрыт.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
    }
}
