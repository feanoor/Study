package lab7_Sockets;

import java.io.*;
import java.net.Socket;

/**
 * Данный класс занимается отправкой данных на сервер.
 */
public class ClientWriterThread extends Thread {
    BufferedReader reader;
    BufferedWriter out;
    Socket socket;

    /**
     * В конструкторе инициализируется ридер, который считывает текст с клавиатуры и райтер
     * который отправляет считаный текст на сервер
     *
     * @param socket
     * @throws IOException
     */
    public ClientWriterThread(Socket socket) throws IOException {
        this.socket = socket;
        reader = new BufferedReader(new InputStreamReader(System.in));
        out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        start();
    }

    /**
     * Здесь запускается поток. Если в консоль вводится quit поток завершает свою работу.
     */
    @Override
    public void run() {
        try {

            String message;
            while (!((message = reader.readLine()).equals("quit"))) {
                out.write(message + "\n");
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                System.out.println("Соединение прервано.");
                reader.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
