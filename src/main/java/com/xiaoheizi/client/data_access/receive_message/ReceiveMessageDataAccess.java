package com.xiaoheizi.client.data_access.receive_message;

import com.xiaoheizi.client.data_access.ServerDataAccessObject;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageDataAccessInterface;
import com.xiaoheizi.client.use_case.receive_message.ReceiveMessageOutputData;
import com.xiaoheizi.packet.PacketServerTextMessage;

public class ReceiveMessageDataAccess implements ReceiveMessageDataAccessInterface {
    private final ServerDataAccessObject serverDataAccessObject;

    public ReceiveMessageDataAccess(ServerDataAccessObject serverDataAccessObject) {
        this.serverDataAccessObject = serverDataAccessObject;
    }

    /**
     * Retrieves a message from the com.ikun.server and decrypts it.
     *
     * @return The output data containing the decrypted message and associated details.
     * @throws RuntimeException if there is an error during the decryption process.
     */
    @Override
    public ReceiveMessageOutputData receiveMessage() {
        PacketServerTextMessage response = serverDataAccessObject.getReceiveMessage();
        return new ReceiveMessageOutputData(response.getSender(), response.getEncryptedMessage(), response.getTimestamp());
//        try {
//            return new ReceiveMessageOutputData(response.getSender(), AES_decrypt(response.getEncryptedMessage()), response.getTimestamp());
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }
}
