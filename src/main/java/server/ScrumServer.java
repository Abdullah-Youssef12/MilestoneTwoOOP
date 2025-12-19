package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ScrumServer {

    private static final int PORT = 5000;
    private final List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        new ScrumServer().start();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server running on port " + PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                ClientHandler handler = new ClientHandler(socket, this);
                synchronized (clients) {
                    clients.add(handler);
                }
                new Thread(handler).start();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void broadcast(String message) {
        synchronized (clients) {
            for (ClientHandler c : clients) {
                c.sendMessage(message);
            }
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket socket;
        private final ScrumServer server;
        private PrintWriter out;

        ClientHandler(Socket socket, ScrumServer server) {
            this.socket = socket;
            this.server = server;
        }

        public void run() {
            System.out.println(
                    "Handling client on thread: " + Thread.currentThread().getName()
            );

            try (BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()))) {
                out = new PrintWriter(socket.getOutputStream(), true);
                String line;
                while ((line = in.readLine()) != null) {
                    if (line.startsWith("CREATE_EPIC")) {
                        // parse command
                        String[] parts = line.split(" ", 3);
                        String title = parts[1];
                        String desc = parts[2];
                        // Here you would call IssueCreationService.createIssue(...)
                        server.broadcast("Epic created: " + title);
                    } else if (line.equals("GET_BACKLOG")) {
                        // Here you would call BacklogService.getBacklog()
                        server.broadcast("Backlog requested by client");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                server.clients.remove(this);
            }
        }

        void sendMessage(String msg) {
            if (out != null) {
                out.println(msg);
            }
        }
    }
}
