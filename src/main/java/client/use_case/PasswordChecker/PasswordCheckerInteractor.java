package client.use_case.PasswordChecker;

import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageOutputBoundary;

public class PasswordCheckerInteractor implements PasswordCheckerInputBoundary{
    private final PasswordCheckerDataAccessInterface dataAccess;
    private final PasswordCheckerOutputBoundary passwordCheckerPresenter;

    /**
     * Constructs a SendMessageInteractor with the provided data access and output boundary.
     *
     * @param dataAccess     The data access interface for sending messages.
     * @param outputBoundary The output boundary for presenting message result.
     */
    public PasswordCheckerInteractor(PasswordCheckerDataAccessInterface dataAccess, PasswordCheckerOutputBoundary passwordCheckerPresenter) {
        this.dataAccess = dataAccess;
        this.passwordCheckerPresenter = passwordCheckerPresenter;
    }


    @Override
    public void execute(PasswordCheckerInputData in) {
        PasswordCheckerOutputData out = new PasswordCheckerOutputData(dataAccess.isPasswordValid(in.getPassword()));
        passwordCheckerPresenter.presentCheckPasswordResult(out);

    }
}
