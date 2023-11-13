package client.interface_adapter.Login;

import client.use_case.Login.LoginOutputBoundary;
import client.use_case.Login.LoginOutputData;
import client.use_case.Signup.SignupOutputBoundary;
import client.use_case.Signup.SignupOutputData;


public class LoginPresenter implements LoginOutputBoundary {

    @Override
    public void prepareSuccessView(LoginOutputData user) {
        System.out.println("Login success");
    }

    @Override
    public void prepareFailView(String s) {
        System.out.println("Login fail");
    }

}
