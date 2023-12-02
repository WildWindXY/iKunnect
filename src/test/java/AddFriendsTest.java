import static org.mockito.Mockito.*;

import client.data_access.ServerDataAccessObject;
import client.data_access.add_friend.AddFriendDataAccess;
import client.use_case.add_friend.AddFriendDataAccessInterface;
import client.use_case.add_friend.AddFriendInteractor;
import client.use_case.add_friend.AddFriendOutputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AddFriendInteractorTest {
    private AddFriendDataAccessInterface dataAccess;
    private AddFriendOutputBoundary addFriendPresenter;
    private AddFriendInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = mock(AddFriendDataAccessInterface.class);
        addFriendPresenter = mock(AddFriendOutputBoundary.class);
        interactor = new AddFriendInteractor(dataAccess, addFriendPresenter);
    }

    @Test
    void testExecuteAddsFriendAndPreparesView() {
        String username = "testUser";

        interactor.execute(username);

        verify(dataAccess).addFriend(username);
        verify(addFriendPresenter).prepareSuccessView();
    }

}

class AddFriendDataAccessTest {

    private ServerDataAccessObject serverDataAccessObject;
    private AddFriendDataAccess addFriendDataAccess;

    @BeforeEach
    void setUp() {
        serverDataAccessObject = mock(ServerDataAccessObject.class);
        addFriendDataAccess = new AddFriendDataAccess(serverDataAccessObject);
    }

    @Test
    void testAddFriend() {
        String username = "testUser";

        addFriendDataAccess.addFriend(username);

        verify(serverDataAccessObject).addFriend(username);
    }
}
