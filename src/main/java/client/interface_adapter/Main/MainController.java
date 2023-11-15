package client.interface_adapter.Main;

import client.use_case.ReceiveMessage.ReceiveMessageInputBoundary;
import client.use_case.ReceiveMessage.ReceiveMessageInteractor;
import client.use_case.SendMessage.SendMessageInputBoundary;
import client.use_case.SendMessage.SendMessageInputData;

public class MainController {

    private final SendMessageInputBoundary sendMessageInteractor;

    private final ReceiveMessageInputBoundary receiveMessageInteractor;
    private final String myUsername;

    public MainController(String myUsername, SendMessageInputBoundary sendMessageInteractor, ReceiveMessageInputBoundary receiveMessageInteractor){
        this.sendMessageInteractor = sendMessageInteractor;
        this.receiveMessageInteractor = receiveMessageInteractor;
        this.myUsername = myUsername;
    }

    public void sendMessage(String message, String receiver){
        SendMessageInputData in = new SendMessageInputData(message, myUsername, receiver);
        sendMessageInteractor.execute(in);
    }

    public void getMessage(){
        receiveMessageInteractor.execute();
    }

}
