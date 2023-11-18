package server.entity;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public class ServerUsers implements IFile<ServerUsers> {

    public static final String PATH = "user.json";
    @Expose
    private List<User> users = new ArrayList<>();

    public void addUser(String username, String password) {
        users.add(new User(users.size(), username, password));
    }

    public ServerUser getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public ServerUser getUser(int userId) {
        return users.get(userId);
    }

    public String getPath() {
        return PATH;
    }

    public static ServerUsers getDefault() {
        return new ServerUsers();
    }

    public static class User implements ServerUser {
        @Expose
        private int userId;
        @Expose
        private String username;
        @Expose
        private String password;
        @Expose
        private List<Friend> friends = new ArrayList<>();

        User(int userId, String username, String password) {
            this.userId = userId;
            this.username = username;
            this.password = password;
        }

        public boolean wasFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    return true;
                }
            }
            return false;
        }

        public void makeupFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    friend.isFriend = true;
                    return;
                }
            }
        }

        public void addFriend(int friendId, int chatId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    friend.isFriend = true;
                    return;
                }
            }
            friends.add(new Friend(friendId, chatId));
        }

        public void removeFriend(int friendId) {
            for (Friend friend : friends) {
                if (friend.friendId == friendId) {
                    friend.isFriend = false;
                    return;
                }
            }
        }

        public int getUserId() {
            return userId;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }

    public static class Friend {
        @Expose
        private int friendId;
        @Expose
        private int chatId;
        @Expose
        private boolean isFriend = true;

        Friend(int friendId, int chatId) {
            this.friendId = friendId;
            this.chatId = chatId;
        }

        public int getFriendId() {
            return friendId;
        }

        public int getChatId() {
            return chatId;
        }

        public boolean isFriend() {
            return isFriend;
        }
    }
}
