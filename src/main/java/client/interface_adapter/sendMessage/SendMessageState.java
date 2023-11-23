package client.interface_adapter.sendMessage;

public class SendMessageState {
    private String message = "";
    private long timestamp = 0;

    private String sender = "";
    private boolean success = false;
    public SendMessageState(SendMessageState copy) {
        message = copy.message;
        timestamp = copy.timestamp;
        success = copy.success;
        sender = copy.sender;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SendMessageState() {
    }


    public String getMessage(){

        return message;
    }

    public String getSender(){
        return sender;
    }

    public long getTimestamp(){
        return timestamp;
    }

    public boolean getSuccess(){
        return success;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setSender(String sender){
        this.sender = sender;
    }

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }


}
