package client;

import client.entity.User;
import client.interface_adapter.Signup.SignupController;
import client.interface_adapter.Signup.SignupState;
import client.interface_adapter.Signup.SignupViewModel;
import client.interface_adapter.Signup.UserSignupDataAccessInterface;
import client.use_case.Signup.SignupInputBoundary;
import client.use_case.Signup.SignupInteractor;
import client.view.SignupView;
import client.view.components.frames.SmallJFrame;

import javax.swing.*;

public class TestClientUI {

    public static void main(String[] args) {
        SignupState state;
        SignupInputBoundary userSignupUseCaseInteractor = new SignupInteractor();
        SignupController controller = new SignupController(userSignupUseCaseInteractor);
        SignupViewModel signupviewModel = new SignupViewModel();
        new SmallJFrame("iKunnect - Sign up (SignupView)", new SignupView(controller, signupviewModel));
        while (true) {
            state = signupviewModel.getState();
            System.out.println("-------------------------------------------");
            System.out.println("username = " + state.getUsername());
            System.out.println("password = " + state.getPassword());
            System.out.println("passwordR = " + state.getRepeatPassword());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

}