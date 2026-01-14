package demo.mowed.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import demo.mowed.core.MessageType;

/*
RequestMessage parallels an API request
- messageType corresponds to the route
- authRequest corresponds to the authorization header
 */
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

    public AuthRequest getAuthRequest() {
        return authRequest;
    }

    public void setAuthRequest(AuthRequest authRequest) {
        this.authRequest = authRequest;
    }
}
