package logic;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;

public class Message implements Serializable {
    // attributes are final, so they cannot be manipulated during the process
    private final String messageType;
    private final Object information;

    public Message(String messageType, Object information){
        this.messageType = messageType;
        this.information = information;
    }

    public static class MessageBuilder{
        String messageType;
        Object information;

        public MessageBuilder withMessageType(String messageType){
            this.messageType = messageType;
            return this;
        }

        public MessageBuilder withInformation(Object information){
            if (information instanceof BufferedImage){
                this.information = new MyImage((BufferedImage) information);
            } else {
                this.information = information;
            }
            return this;
        }

        public Message build(){
            if (messageType.equals("updateFrame") && information instanceof BufferedImage){
                return new Message(messageType, information);
            } else
            if (messageType.equals("initType") && information instanceof String){
                return new Message(messageType, information);
            } else
            {
                return new Message("faultyMessage", null);
            }

        }
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getInformation(){
        if (information instanceof MyImage){
            try {
                return ((MyImage) information).getImage();
            } catch (IOException e) {
                return new Message("invalidMessage", null);
            }
        } else {
            return information;
        }
    }
}
