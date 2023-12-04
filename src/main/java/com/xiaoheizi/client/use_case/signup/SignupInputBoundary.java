package com.xiaoheizi.client.use_case.signup;

public interface SignupInputBoundary {
    void execute(SignupInputData signupInputData);

    void executeLogin();
}
