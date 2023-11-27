package client.use_case.Signup;

public interface SignupOutputBoundary {

    void prepareFailView(String s);

    void prepareSuccessView(SignupOutputData signupOutputData);

    void prepareLoginView();
}