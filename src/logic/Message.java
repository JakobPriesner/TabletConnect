package logic;

import java.awt.image.BufferedImage;

public class Message {
    // attributes are final, so they cannot be manipulated during the process
    private final int convID;
    private final String messageType;
    private final Object information;

    private Message(int convID, String messageType, Object information){
        this.convID = convID;
        this.messageType = messageType;
        this.information = information;
    }

    public static class MessageBuilder{
        int convID;
        String messageType;
        Object information;

        public MessageBuilder withConvID(int convID){
            this.convID = convID;
            return this;
        }

        public MessageBuilder withMessageType(String messageType){
            this.messageType = messageType;
            return this;
        }

        public MessageBuilder withInformation(Object information){
            this.information = information;
            return this;
        }

        public Message build(){
            if (messageType.equals("updateFrame") && information instanceof BufferedImage){
                return new Message(convID, messageType, information);
            } else if (messageType.equals("initType") && information instanceof String){
                return new Message(convID, messageType, information);
            } else
            {
                return new Message(convID, "faultyMessage", null);
            }

        }
    }

    public int getConvID(){
        return convID;
    }

    public String getMessageType() {
        return messageType;
    }

    public Object getInformation(){
        return information;
    }
}
