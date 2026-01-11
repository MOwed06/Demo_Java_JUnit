package demo.mowed.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestMessage {
    private MessageType messageType;
    private AuthRequest authRequest;

    public RequestMessage() {
    }

    public RequestMessage(MessageType messageType, AuthRequest authRequest) {
        this.messageType = messageType;
        this.authRequest = authRequest;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public AuthRequest getAuthRequestDto() {
        return this.authRequest;
    }

    public void setAuthRequestDto(AuthRequest value) {
        this.authRequest = value;
    }
}
