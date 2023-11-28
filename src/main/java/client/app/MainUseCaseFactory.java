package client.app;

import client.interface_adapter.AddFriend.AddFriendPresenter;
import client.interface_adapter.Main.MainController;
import client.interface_adapter.Main.MainViewModel;
import client.interface_adapter.ReceiveMessage.ReceiveMessagePresenter;
import client.interface_adapter.SendMessage.*;
import client.interface_adapter.options.HighContrastPresenter;
import client.use_case.AddFriend.AddFriendDataAccessInterface;
import client.use_case.AddFriend.AddFriendInputBoundary;
import client.use_case.AddFriend.AddFriendInteractor;
import client.use_case.AddFriend.AddFriendOutputBoundary;
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
import client.use_case.HighContrast.HighContrastDataAccessInterface;
import client.use_case.HighContrast.HighContrastInputBoundary;
import client.use_case.HighContrast.HighContrastInteractor;
import client.use_case.HighContrast.HighContrastOutputBoundary;
import client.view.MainView;

public class MainUseCaseFactory {
    private MainUseCaseFactory() {}
    public static MainView create(String myUsername,
                                            SendMessageDataAccessInterface sendMessageDataAccessObject,
                                            ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                            TranslateDataAccessInterface translateDataAccessObject,
                                            HighContrastDataAccessInterface highContrastDataAccessObject,
                                            AddFriendDataAccessInterface addFriendDataAccessObject,
                                            MainViewModel mainViewModel) {
        MainController mainController = createMainUseCase(myUsername, sendMessageDataAccessObject, receiveMessageDataAccessObject, translateDataAccessObject, highContrastDataAccessObject, addFriendDataAccessObject, mainViewModel);
        return new MainView(mainController, mainViewModel, highContrastDataAccessObject.get(HighContrastDataAccessInterface.HIGH_CONTRAST));
    }

    private static MainController createMainUseCase(String myUsername,
                                                    SendMessageDataAccessInterface sendMessageDataAccessObject,
                                                    ReceiveMessageDataAccessInterface receiveMessageDataAccessObject,
                                                    TranslateDataAccessInterface translateDataAccessObject,
                                                    HighContrastDataAccessInterface highContrastDataAccessObject,
                                                    AddFriendDataAccessInterface addFriendDataAccessObject,
                                                    MainViewModel mainViewModel) {
        SendMessageOutputBoundary sendMessageOutputBoundary = new SendMessagePresenter(mainViewModel);
        ReceiveMessageOutputBoundary receiveMessageOutputBoundary = new ReceiveMessagePresenter(mainViewModel);
        HighContrastOutputBoundary highContrastOutputBoundary = new HighContrastPresenter(mainViewModel);
        AddFriendOutputBoundary addFriendOutputBoundary = new AddFriendPresenter(mainViewModel);
        SendMessageInputBoundary sendMessageInteractor = new SendMessageInteractor(sendMessageDataAccessObject, sendMessageOutputBoundary);
        ReceiveMessageInputBoundary receiveMessageInteractor = new ReceiveMessageInteractor(receiveMessageDataAccessObject, receiveMessageOutputBoundary);
        TranslationInputBoundary translationInteractor = new TranslationInteractor(translateDataAccessObject);
        HighContrastInputBoundary highContrastInteractor = new HighContrastInteractor(highContrastDataAccessObject, highContrastOutputBoundary);
        AddFriendInputBoundary addFriendInteractor = new AddFriendInteractor(addFriendDataAccessObject, addFriendOutputBoundary);
        return new MainController(myUsername, sendMessageInteractor, receiveMessageInteractor, translationInteractor, highContrastInteractor, addFriendInteractor);
    }
}