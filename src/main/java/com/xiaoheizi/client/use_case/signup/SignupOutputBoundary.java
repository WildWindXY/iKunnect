package com.xiaoheizi.client.use_case.signup;

public interface SignupOutputBoundary {

    void prepareFailView(String s);

    void prepareSuccessView(SignupOutputData signupOutputData);

    void prepareLoginView();
}