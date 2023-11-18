package server.entity;

public interface ServerUser {
    boolean wasFriend(int friendId);

    void addFriend(int friendId, int chatId);

    void makeupFriend(int friendId);

    void removeFriend(int friendId);

    int getUserId();

    String getUsername();

    String getPassword();
}
