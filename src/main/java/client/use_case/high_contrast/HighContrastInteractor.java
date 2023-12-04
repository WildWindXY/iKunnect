package client.use_case.high_contrast;

public class HighContrastInteractor implements HighContrastInputBoundary {

    public static final int INVALID_OPTION = -1;
    public static final int TOGGLE_HIGH_CONTRAST = 0;

    private final HighContrastDataAccessInterface optionsDataAccessObject;
    private final HighContrastOutputBoundary optionsOutputBoundary;


    public HighContrastInteractor(HighContrastDataAccessInterface optionsDataAccessObject, HighContrastOutputBoundary optionsOutputBoundary) {
        this.optionsDataAccessObject = optionsDataAccessObject;
        this.optionsOutputBoundary = optionsOutputBoundary;
    }

    @Override
    public int execute(int action) {
        switch (action) {
            case TOGGLE_HIGH_CONTRAST -> {
                System.out.println("Interactor Toggle High Contrast");
                optionsDataAccessObject.toggle(optionsDataAccessObject.HIGH_CONTRAST);
                HighContrastOutputData outputData = optionsDataAccessObject.get(optionsDataAccessObject.HIGH_CONTRAST);
                optionsOutputBoundary.setHighContrast(outputData);
                return outputData.getHighContrast() ? 1 : 0;
            }
            //TODO return void after feature complete
            //case ADD_FRIEND:
            //    System.out.println("Interactor Add Friend");

            //case 3:
        }
        return -1;
    }

}
