package server.use_case.signup;

public class ServerSignupInteractor implements ServerSignupInputBoundary {
    private final ServerSignupOutputBoundary serverSignupOutputBoundary;
    private final ServerSignupDataAccessInterface serverSignupDataAccessInterface;

    public ServerSignupInteractor(ServerSignupDataAccessInterface serverSignupDataAccessInterface, ServerSignupOutputBoundary serverSignupOutputBoundary) {
        this.serverSignupOutputBoundary = serverSignupOutputBoundary;
        this.serverSignupDataAccessInterface = serverSignupDataAccessInterface;
    }

    private void handlePacket() {

    }
}
