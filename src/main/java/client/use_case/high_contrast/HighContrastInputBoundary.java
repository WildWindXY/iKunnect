package client.use_case.high_contrast;

public interface HighContrastInputBoundary {
    int INVALID_OPTION = -1;
    int TOGGLE_HIGH_CONTRAST = 0;

    int ADD_FRIEND = 1;
    int execute(int action);
}
