package lab7_Sockets;

import java.io.*;
import java.net.Socket;

public class ServerListener extends Thread {
    private Socket socket;
    private BufferedWriter out;
    private BufferedReader in;
    private String nickName = "";

    /**
     * В конструкторе инициализируется ридер, который получает поток данных от клиента
     * и райтер, который отправляет данные клиенту.
     *
     * @param socket
     */
    public ServerListener(Socket socket) {
        this.socket = socket;
        try {
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * В этом методе происходит авторизация пользователя, отправка сервесных сообщений, пересылка
     * сообщения одного из пользователей всем остальным подключеным пользователям.
     */
    @Override
    public void run() {
        String word;
        send("Вы попали в чат.\nПредставьтесь пожалуйста:");
        try {
            while (!((word = in.readLine()).equals("quit"))) {
                if (nickName.equals("")) {
                    nickName = word;
                    send("Добро пожаловать, " + nickName + "! Теперь вы можете начать общение!");
                    sendAll(nickName + " теперь с нами!");
                } else {
                    sendAll(word);
                }

            }
        } catch (IOException ignore) {
        } catch (NullPointerException ignore) {
        } finally {
            try {
                Server.serverListener.remove(this);
                sendAll(nickName + " покинул нас!");
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Клиент " + nickName + " отключился.");
            Server.serverListener.remove(this);
        }
    }

    private void send(String word) {
        try {
            out.write(word + "\n");
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendAll(String word) {
        for (ServerListener cl : Server.serverListener) {
            cl.send("<" + nickName + ">: " + word);
        }
    }
}
