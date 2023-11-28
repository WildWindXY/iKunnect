package client.data_access.high_contrast;

public class HighContrastState {
    private boolean highContrast = false;
    public HighContrastState(HighContrastState copy) {
        this.highContrast = copy.highContrast;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public HighContrastState() {
    }

    public boolean getHighContrast() {
        return highContrast;
    }

    public void setHighContrast(boolean highContrast) {
        this.highContrast = highContrast;
    }
}
