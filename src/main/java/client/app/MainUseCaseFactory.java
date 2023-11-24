package client.app;

import client.data_access.options.OptionsState;
import client.interface_adapter.main.MainController;
import client.interface_adapter.main.MainViewModel;
import client.interface_adapter.options.OptionsPresenter;
import client.interface_adapter.receiveMessage.ReceiveMessagePresenter;
import client.interface_adapter.sendMessage.SendMessagePresenter;
import client.use_case.options.OptionsDataAccessInterface;
import client.use_case.options.OptionsInputBoundary;
import client.use_case.options.OptionsInteractor;
import client.use_case.options.OptionsOutputBoundary;
import client.use_case.receiveMessage.ReceiveMessageDataAccessInterface;
import client.use_case.receiveMessage.ReceiveMessageInputBoundary;
import client.use_case.receiveMessage.ReceiveMessageInteractor;
import client.use_case.receiveMessage.ReceiveMessageOutputBoundary;
import client.use_case.sendMessage.SendMessageDataAccessInterface;
import client.use_case.sendMessage.SendMessageInputBoundary;
import client.use_case.sendMessage.SendMessageInteractor;
import client.use_case.sendMessage.SendMessageOutputBoundary;
import client.use_case.translate.TranslateDataAccessInterface;
import client.use_case.translate.TranslationInputBoundary;
import client.use_case.translate.TranslationInteractor;
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