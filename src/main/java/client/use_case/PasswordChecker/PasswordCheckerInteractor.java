package client.use_case.PasswordChecker;

import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageOutputBoundary;

public class PasswordCheckerInteractor implements PasswordCheckerInputBoundary{
    private final PasswordCheckerDataAccessInterface dataAccess;


    /**
     * Constructs a SendMessageInteractor with the provided data access and output boundary.
     *
     * @param dataAccess     The data access interface for sending messages.
     * @param outputBoundary The output boundary for presenting message result.
     */
    public PasswordCheckerInteractor(PasswordCheckerDataAccessInterface dataAccess) {
        this.dataAccess = dataAccess;
    }


    @Override
    public boolean execute(PasswordCheckerInputData in) {
        PasswordCheckerOutputData out = new PasswordCheckerOutputData(dataAccess.isPasswordValid(in.getPassword()));
        return out.isValid();
    }
}
