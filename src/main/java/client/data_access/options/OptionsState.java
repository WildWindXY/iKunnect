package client.data_access.options;

public class OptionsState {
    private boolean highContrast = false;
    public OptionsState(OptionsState copy) {
        this.highContrast = copy.highContrast;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public OptionsState() {
    }

    public boolean getHighContrast() {
        return highContrast;
    }

    public void setHighContrast(boolean highContrast) {
        this.highContrast = highContrast;
    }
}
