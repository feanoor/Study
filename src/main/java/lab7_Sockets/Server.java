package lab7_Sockets;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Данный класс занимается обработкой подключений клиентов, чтением и пересылкой сообщений от
 * клиетов другим клиентам, отправкой сервесных сообщений.
 */

public class Server {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;
    public static LinkedList<ServerListener> serverListener = new LinkedList<>();

    /**
     * здесь создается сокет сервера и слушается порт, на наличие новых запросов на подкючения.
     * В случае установления соединения создается новый экземпляр класса serverListener.
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            server = new ServerSocket(3232);
            System.out.println("Сервер запущен!");
            try {

                while (true) {
                    clientSocket = server.accept();
                    System.out.println("Подключился новый клиент " + clientSocket);
                    serverListener.add(new ServerListener(clientSocket));
                }
            } finally {
                in.close();
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
                System.out.println("Сервер закрыт.");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
