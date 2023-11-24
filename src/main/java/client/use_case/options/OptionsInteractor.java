package client.use_case.options;

public class OptionsInteractor implements OptionsInputBoundary {

    public static final int INVALID_OPTION = -1;
    public static final int TOGGLE_HIGH_CONTRAST = 0;

    private final OptionsDataAccessInterface optionsDataAccessObject;
    private final OptionsOutputBoundary optionsOutputBoundary;


    public OptionsInteractor(OptionsDataAccessInterface optionsDataAccessObject, OptionsOutputBoundary optionsOutputBoundary){
        this.optionsDataAccessObject = optionsDataAccessObject;
        this.optionsOutputBoundary = optionsOutputBoundary;
    }

    @Override
    public int execute(int action) {
        switch(action){
            case TOGGLE_HIGH_CONTRAST:
                System.out.println("Interactor Toggle High Contrast");
                optionsDataAccessObject.toggle(optionsDataAccessObject.HIGH_CONTRAST);
                OptionsOutputData outputData = optionsDataAccessObject.get(optionsDataAccessObject.HIGH_CONTRAST);
                optionsOutputBoundary.setHighContrast(outputData);
                return outputData.getHighContrast()? 1 : 0;
                //TODO return void after feature complete
            case 2:
            case 3:
        }
        return -1;
    }
}
