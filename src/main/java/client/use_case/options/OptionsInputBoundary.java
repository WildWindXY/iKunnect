package client.use_case.options;

public interface OptionsInputBoundary {
    int INVALID_OPTION = -1;
    int TOGGLE_HIGH_CONTRAST = 0;
    int execute(int action);
}
