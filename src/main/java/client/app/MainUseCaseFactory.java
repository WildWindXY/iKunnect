package client.app;

import client.data_access.options.OptionsState;
import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.ReceiveMessage.ReceiveMessagePresenter;
import client.interface_adapter.SendMessage.*;
import client.interface_adapter.options.OptionsPresenter;
import client.use_case.ReceiveMessage.ReceiveMessageDataAccessInterface;
import client.use_case.ReceiveMessage.ReceiveMessageInputBoundary;
import client.use_case.ReceiveMessage.ReceiveMessageInteractor;
import client.use_case.ReceiveMessage.ReceiveMessageOutputBoundary;
import client.use_case.SendMessage.SendMessageDataAccessInterface;
import client.use_case.SendMessage.SendMessageInputBoundary;
import client.use_case.SendMessage.SendMessageInteractor;
import client.use_case.SendMessage.SendMessageOutputBoundary;
import client.use_case.Translate.TranslateDataAccessInterface;
import client.use_case.Translate.TranslationInputBoundary;
import client.use_case.Translate.TranslationInteractor;
import client.use_case.options.OptionsDataAccessInterface;
import client.use_case.options.OptionsInputBoundary;
import client.use_case.options.OptionsInteractor;
import client.use_case.options.OptionsOutputBoundary;
import client.view.MainView;

public class MainUseCaseFactory {
    private MainUseCaseFactory() {}
    public static MainView create(String myUsername,
                                            SendMessageDataAccessInterface sendMessageDataAccessObject,
                                            ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                            TranslateDataAccessInterface translateDataAccessObject,
                                            OptionsDataAccessInterface optionsDataAccessObject,
                                            MainViewModel mainViewModel) {
        MainController mainController = createMainUseCase(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, optionsDataAccessObject, mainViewModel);
        return new MainView(mainController, mainViewModel, optionsDataAccessObject.get(OptionsDataAccessInterface.HIGH_CONTRAST));
    }

    private static MainController createMainUseCase(String myUsername,
                                                    SendMessageDataAccessInterface sendMessageDataAccessObject,
                                                    ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                                    TranslateDataAccessInterface translateDataAccessObject,
                                                    OptionsDataAccessInterface optionsDataAccessObject,
                                                    MainViewModel mainViewModel) {
        SendMessageOutputBoundary sendMessageOutputBoundary = new SendMessagePresenter(mainViewModel);
        ReceiveMessageOutputBoundary receiveMessageOutputBoundary = new ReceiveMessagePresenter(mainViewModel);
        OptionsOutputBoundary optionsOutputBoundary = new OptionsPresenter(mainViewModel);
        SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccessObject, sendMessageOutputBoundary);
        ReceiveMessageInputBoundary receiveMessageInteractor = new ReceiveMessageInteractor(receiveMessageDataAccessObject, receiveMessageOutputBoundary);
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translateDataAccessObject);
        OptionsInputBoundary optionsInteractor = new OptionsInteractor(optionsDataAccessObject, optionsOutputBoundary);
        return new MainController(myUsername, sendMessageInteractor, receiveMessageInteractor, translationInteractor, optionsInteractor);
    }
}