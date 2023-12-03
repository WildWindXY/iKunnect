package client.use_case.add_friend;

public interface AddFriendInputBoundary {
    void execute(String username);

    void executeFriendList();
}
