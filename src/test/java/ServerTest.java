import common.packet.*;
import org.junit.jupiter.api.Test;
import utils.FileUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Tests in this class requires server running with empty data.
 */
public class ServerTest {

    /**
     * Since testing the server requires a fixed order, it cannot be seperated into different tests
     */
    @Test
    public void testServer() throws IOException, ClassNotFoundException {
        System.out.println(FileUtils.getJarPath());
        IKunnectServer.start();

        FakeClient user1 = new FakeClient();
        testServerSignup(user1);

        FakeClient user2 = new FakeClient();
        testServerLogin(user2);

        testServerNotLoggedIn();
        testServerFriendRelated(user1, user2);

    }

    private void testServerSignup(FakeClient user) throws IOException, ClassNotFoundException {
        user.send(new PacketClientSignup(null, null));
        Packet packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.NULL_ATTRIBUTE);

        user.send(new PacketClientSignup("----", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.INVALID_CHARACTERS);

        user.send(new PacketClientSignup("a", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.TOO_SHORT);

        user.send(new PacketClientSignup("12345678901234567890", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.TOO_LONG);

        user.send(new PacketClientSignup("user1", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.SUCCESS);
        assert (((PacketServerSignupResponse) packet).getUserID() == 0);

        user.send(new PacketClientSignup("user1", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.ALREADY_LOGGED_IN);
    }

    private void testServerLogin(FakeClient user) throws IOException, ClassNotFoundException {
        user.send(new PacketClientSignup("user1", "user"));
        Packet packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.USERNAME_EXISTS);

        user.send(new PacketClientSignup("user2", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerSignupResponse);
        assert (((PacketServerSignupResponse) packet).getStatus() == PacketServerSignupResponse.Status.SUCCESS);

        user.close();
        user.rejoin();

        user.send(new PacketClientLogin(null, "user"));
        packet = user.read();
        assert (packet instanceof PacketServerLoginResponse);
        assert (((PacketServerLoginResponse) packet).getStatus() == PacketServerLoginResponse.Status.NULL_ATTRIBUTE);

        user.send(new PacketClientLogin("user", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerLoginResponse);
        assert (((PacketServerLoginResponse) packet).getStatus() == PacketServerLoginResponse.Status.NO_SUCH_USERNAME);

        user.send(new PacketClientLogin("user2", "null"));
        packet = user.read();
        assert (packet instanceof PacketServerLoginResponse);
        assert (((PacketServerLoginResponse) packet).getStatus() == PacketServerLoginResponse.Status.WRONG_PASSWORD);

        user.send(new PacketClientLogin("user2", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerLoginResponse);
        assert (((PacketServerLoginResponse) packet).getStatus() == PacketServerLoginResponse.Status.SUCCESS);
        assert (((PacketServerLoginResponse) packet).getUserID() == 1);

        user.send(new PacketClientLogin("user2", "user"));
        packet = user.read();
        assert (packet instanceof PacketServerLoginResponse);
        assert (((PacketServerLoginResponse) packet).getStatus() == PacketServerLoginResponse.Status.ALREADY_LOGGED_IN);
    }

    private void testServerNotLoggedIn() throws IOException, ClassNotFoundException {
        FakeClient user = new FakeClient();
        user.send(new PacketClientGetFriendList());
        Packet packet = user.read();
        assert (packet instanceof PacketServerGetFriendListResponse);
        assert (((PacketServerGetFriendListResponse) packet).getStatus() == PacketServerGetFriendListResponse.Status.NOT_LOGGED_IN);

        user.send(new PacketClientTextMessage(0, 0, null));
        packet = user.read();
        assert (packet instanceof PacketServerTextMessageResponse);
        assert (((PacketServerTextMessageResponse) packet).getStatus() == PacketServerTextMessageResponse.Status.NOT_LOGGED_IN);

        user.send(new PacketClientFriendRequest(null));
        packet = user.read();
        assert (packet instanceof PacketServerFriendRequestResponse);
        assert (((PacketServerFriendRequestResponse) packet).getStatus() == PacketServerFriendRequestResponse.Status.NOT_LOGGED_IN);
    }

    private void testServerFriendRelated(FakeClient user1, FakeClient user2) throws IOException, ClassNotFoundException {
        user1.send(new PacketClientTextMessage(0, 1, "message"));
        Packet packet = user1.read();
        assert (packet instanceof PacketServerTextMessageResponse);
        assert (((PacketServerTextMessageResponse) packet).getStatus() == PacketServerTextMessageResponse.Status.NOT_FRIEND);

        user1.send(new PacketClientFriendRequest("user"));
        packet = user1.read();
        assert (packet instanceof PacketServerFriendRequestResponse);
        assert (((PacketServerFriendRequestResponse) packet).getStatus() == PacketServerFriendRequestResponse.Status.INFO_INVALID);

        user1.send(new PacketClientFriendRequest("user2"));
        packet = user1.read();
        assert (packet instanceof PacketServerFriendRequestResponse);
        assert (((PacketServerFriendRequestResponse) packet).getStatus() == PacketServerFriendRequestResponse.Status.SENT);

        user2.send(new PacketClientFriendRequest("user1"));
        packet = user2.read();
        assert (packet instanceof PacketServerFriendRequestResponse);
        assert (((PacketServerFriendRequestResponse) packet).getStatus() == PacketServerFriendRequestResponse.Status.ACCEPTED);

        user2.send(new PacketClientFriendRequest("user1"));
        packet = user2.read();
        assert (packet instanceof PacketServerFriendRequestResponse);
        assert (((PacketServerFriendRequestResponse) packet).getStatus() == PacketServerFriendRequestResponse.Status.ALREADY_FRIEND);

        user1.send(new PacketClientTextMessage(0, 1, ""));
        packet = user1.read();
        assert (packet instanceof PacketServerTextMessageResponse);
        assert (((PacketServerTextMessageResponse) packet).getStatus() == PacketServerTextMessageResponse.Status.INVALID_MESSAGE);

        user1.send(new PacketClientTextMessage(0, 1, "message"));
        packet = user1.read();
        assert (packet instanceof PacketServerTextMessageResponse);
        assert (((PacketServerTextMessageResponse) packet).getStatus() == PacketServerTextMessageResponse.Status.RECEIVED);


        user1.send(new PacketClientGetFriendList());
        packet = user1.read();
        assert (packet instanceof PacketServerGetFriendListResponse);
    }

    private static class FakeClient {
        private ObjectOutputStream out;
        private ObjectInputStream in;

        private Socket socket;

        public FakeClient() throws IOException {
            rejoin();
        }

        private void send(Packet packet) throws IOException {
            out.writeObject(packet);
            out.flush();
        }

        private Packet read() throws IOException, ClassNotFoundException {
            return (Packet) in.readObject();
        }

        private void close() throws IOException {
            in.close();
            out.close();
            socket.close();
        }

        private void rejoin() throws IOException {
            socket = new Socket("localhost", 0x2304);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
    }
}
