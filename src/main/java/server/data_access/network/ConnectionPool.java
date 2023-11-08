package server.data_access.network;

import common.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

class ConnectionPool {
    private final ArrayList<Connection> connections = new ArrayList<>();
    private final ServerSocket serverSocket;
    private final NetworkManager networkManager;


    ConnectionPool(NetworkManager networkManager, int port) throws IOException {
        this.networkManager = networkManager;
        serverSocket = new ServerSocket(port);
        new Thread(this::handleConnections).start();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!serverSocket.isClosed()) {
                    connections.removeIf(connection -> connection.socket.isClosed());
                } else {
                    timer.cancel();
                }
            }
        }, 0, 1000);// execute every second
    }

    private void handleConnections() {
        while (!serverSocket.isClosed()) {
            try {
                Socket socket = serverSocket.accept();
                networkManager.addMessageToTerminal("Some client connected");
                Connection connection = new Connection(socket);
                connections.add(connection);
            } catch (Exception ignored) {//TODO: Log it later

            }
        }
    }

    /**
     * Closes the resources associated with the server.
     * <p>
     * This method is used to close the server's resources, including the server socket
     * and all active connections. It is typically called to gracefully terminate the
     * server. Any IOExceptions that occur during the closing process are caught and
     * ignored for later handling.
     */
    public void close() {
        try {
            serverSocket.close();
        } catch (IOException ignored) {//TODO: Handle it later

        }
        for (Connection connection : connections) {
            try {
                connection.destroy();
            } catch (IOException ignored) {//TODO: Handle it later

            }
        }
    }

    /**
     * Sends a packet to all connected clients.
     * <p>
     * This method sends the specified packet to all connected clients by adding it to
     * each client's send queue. Additionally, it logs the action in the network manager's
     * terminal output.
     *
     * @param packet The packet to be sent to all connected clients.
     */
    public void sendAll(Packet packet) {
        for (Connection connection : connections) {
            connection.toSend.add(packet);
            networkManager.addMessageToTerminal("Send packet to client: " + packet);
        }
    }

    //TODO: public void sendTo(Packet packet, User user){}

    private class Connection {
        private final Socket socket;
        private final ObjectInputStream in;
        private final ObjectOutputStream out;
        private final LinkedBlockingQueue<Packet> toSend = new LinkedBlockingQueue<>();

        Connection(Socket socket) throws IOException {
            this.socket = socket;
            this.in = new ObjectInputStream(socket.getInputStream());
            this.out = new ObjectOutputStream(socket.getOutputStream());

            new Thread(() -> {
                while (!socket.isClosed() && !serverSocket.isClosed()) {
                    try {
                        Object object = in.readObject();
                        if (object instanceof Packet) {
                            packetHandler((Packet) object);
                        }//TODO: else throws Unknown Packet Exception.
                    } catch (IOException | ClassNotFoundException ignored) {
                        //TODO: Do something at least
                    }
                }
            }).start();

            new Thread(() -> {
                while (!socket.isClosed() && !serverSocket.isClosed()) {
                    try {
                        Object obj = toSend.take();
                        out.writeObject(obj);
                        out.flush();
                    } catch (IOException | InterruptedException ignored) {
                        //TODO: Do something at least
                    }
                }
            }).start();
        }

        private void packetHandler(Packet packet) {
            networkManager.packetHandler(packet);
        }

        private void destroy() throws IOException {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
        }
    }
}

