package lab7_Sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Данный класс занимается чтением данных приходящих с сервера.
 */
public class ClientReaderThread extends Thread {
    BufferedReader in;
    Socket socket;

    /**
     * В конструкторе инициализируется ридер, который получает поток данных с сервера.
     * * @param socket
     *
     * @throws IOException
     */
    public ClientReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }

    /**
     * Здесь запускается поток. Если в от сервера поступит quit поток завершит свою работу.
     */
    @Override
    public void run() {
        try {
            String message;
            while (true) {
                message = in.readLine();
                System.out.println(message);
            }
        } catch (IOException e) {
        } finally {
            try {
                in.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
