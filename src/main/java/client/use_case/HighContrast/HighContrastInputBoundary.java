package client.use_case.HighContrast;

public interface HighContrastInputBoundary {
    int INVALID_OPTION = -1;
    int TOGGLE_HIGH_CONTRAST = 0;

    int ADD_FRIEND = 1;
    int execute(int action);
}
