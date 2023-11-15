package client.interface_adapter.SendMessage;

public class SendMessageState {
    private String message = "";
    private long timestamp = 0;
    private boolean success = false;
    public SendMessageState(SendMessageState copy) {
        message = copy.message;
        timestamp = copy.timestamp;
        success = copy.success;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public SendMessageState() {
    }


    public String getMessage(){

        return message;
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

    public void setTimestamp(long timestamp){
        this.timestamp = timestamp;
    }

    public void setSuccess(boolean success){
        this.success = success;
    }


}
