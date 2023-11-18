package server.data_access.local;

import server.data_access.DataAccess;
import server.entity.ServerUser;
import server.entity.ServerUsers;

public class FileManager {
    private final DataAccess dataAccess;
    private final ServerUsers serverUsers;

    public FileManager(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        serverUsers = new ServerUsers();
    }

    public ServerUser getUserById(int userId) {
        return serverUsers.getUser(userId);
    }

    public ServerUser getUserByUsername(int username) {
        return serverUsers.getUser(username);
    }

    public void shutdown() {
        
    }
}
